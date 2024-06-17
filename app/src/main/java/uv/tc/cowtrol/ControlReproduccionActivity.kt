package uv.tc.cowtrol

import android.R
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import uv.tc.cowtrol.databinding.ActivityControlReproduccionBinding
import uv.tc.cowtrol.modelo.BecerroBD
import uv.tc.cowtrol.modelo.ControlReproduccionBD
import uv.tc.cowtrol.modelo.PotreroBD
import uv.tc.cowtrol.modelo.UsuariosBD
import uv.tc.cowtrol.poko.ControlReproduccion
import uv.tc.cowtrol.poko.Potrero
import java.util.Calendar

class ControlReproduccionActivity : AppCompatActivity() {
    lateinit var binding: ActivityControlReproduccionBinding
    lateinit var usuarioBD: UsuariosBD
    lateinit var potreroBD: PotreroBD
    lateinit var becerrosBD: BecerroBD
    lateinit var controlBD: ControlReproduccionBD
    var correo: String? = ""
    var rancho: String? = ""
    var cargada: Int = 0
    var potreroSeleccionado: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlReproduccionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        controlBD = ControlReproduccionBD(this@ControlReproduccionActivity)
        usuarioBD = UsuariosBD(this@ControlReproduccionActivity)
        correo = intent.getStringExtra("correo")
        rancho = usuarioBD.obtenerRanchoDelUsuario(correo.toString())
        potreroBD = PotreroBD(this@ControlReproduccionActivity)
        becerrosBD = BecerroBD(this@ControlReproduccionActivity)
        llenarSpinners()
        binding.switchCargada.setOnCheckedChangeListener{ button, isChecked ->
            if(isChecked)
                cargada = 1
            else
                cargada = 0
        }
        binding.spPotreros.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                potreroSeleccionado = parent!!.getItemAtPosition(position).toString().toInt()
                val becerros = cargarBecerros(potreroSeleccionado)
                val spinner_adapter_becerros = ArrayAdapter(this@ControlReproduccionActivity, R.layout.simple_spinner_item, becerros)
                spinner_adapter_becerros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spAnimales.adapter = spinner_adapter_becerros
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        binding.btnGuardarControl.setOnClickListener {
            val potrero = binding.spPotreros.selectedItem.toString().toInt()
            val siniiga = binding.spAnimales.selectedItem.toString().toInt()
            val fechaRevision = binding.etFechaRevision.text.toString()
            val temporada = binding.etTemporadaReproduccion.text.toString()
            val diaParto = binding.etDiaParto.text.toString()
            val tipo = binding.spTipo.selectedItem.toString()
            val descripcion = binding.etDescripcion.text.toString()
            if (validarDatos()) {
                val controlReproduccion = ControlReproduccion(
                    potrero,
                    siniiga,
                    fechaRevision,
                    temporada,
                    diaParto,
                    tipo,
                    descripcion,
                    cargada.toString().toBoolean(),
                    rancho.toString()
                )
                agregarControl(controlReproduccion)
            }
        }
        binding.btnRegresar.setOnClickListener {
            finish()
        }
    }

    private fun llenarSpinners(): Unit {
        val potreros = cargarPotreros()
        val tipo = arrayOf("Monta", "Inseminación","Transferencia de Embriones")
        val spinner_adapter_potreros = ArrayAdapter(this@ControlReproduccionActivity, R.layout.simple_spinner_item, potreros)
        spinner_adapter_potreros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPotreros.adapter = spinner_adapter_potreros
        val spinner_adapter_tipo = ArrayAdapter(this@ControlReproduccionActivity, R.layout.simple_spinner_item, tipo)
        spinner_adapter_tipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spTipo.adapter = spinner_adapter_tipo
        binding.etFechaRevision.setOnClickListener{
            mostrarDatePicker(binding.etFechaRevision)
        }
        binding.etDiaParto.setOnClickListener {
            mostrarDatePicker(binding.etDiaParto)
        }
        binding.etTemporadaReproduccion.setOnClickListener {
            mostrarDatePicker(binding.etTemporadaReproduccion)
        }
    }

    private fun cargarPotreros(): List<Int> {
        val potrero = potreroBD.retornarPotrerosRancho(rancho.toString())
        return potrero.map { it.numeroPotrero }
    }

    private fun cargarBecerros(potrero: Int): List<Int> {
        val becerro = becerrosBD.seleccionarBecerrosPorPotrero(potrero, rancho.toString())
        return becerro.map { it.siiniga }
    }

    private fun mostrarDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this, uv.tc.cowtrol.R.style.CustomDatePickerDialog,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val fechaSeleccionada = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                editText.setText(fechaSeleccionada)
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
        binding.etFechaRevision.error = null
    }



    private fun agregarControl (control: ControlReproduccion) {
        val resultadoIncercion = controlBD.añadirControl(control)
        var msj = ""
        if(resultadoIncercion > 0) {
            msj = "Datos insertados correctamente"
            limpiarCampos()
            finish()
        }else
            msj = "Error al insertar los datos"

        Toast.makeText(this@ControlReproduccionActivity, msj, Toast.LENGTH_LONG).show()
    }

    private fun limpiarCampos() {
        binding.etFechaRevision.setText("")
        binding.etTemporadaReproduccion.setText("")
        binding.etDiaParto.setText("")
        binding.etDescripcion.setText("")
        binding.switchCargada.isActivated = false
        binding.etFechaRevision.error = null
        binding.etTemporadaReproduccion.error = null
        binding.etDiaParto.error = null
        binding.etDescripcion.error = null
    }

    private fun validarDatos(): Boolean {
        var valido = true
        if (binding.etFechaRevision.text.isEmpty()) {
            valido = false
            binding.etFechaRevision.error = "Campo Obligatorio"
        }
        if (binding.etTemporadaReproduccion.text.isEmpty()) {
            valido = false
            binding.etTemporadaReproduccion.error = "Campo Obligatorio"
        }
        if (binding.etDiaParto.text.isEmpty()) {
            valido = false
            binding.etDiaParto.error = "Campo Obligatorio"
        }
        if (binding.etDescripcion.text.isEmpty()) {
            valido = false
            binding.etDescripcion.error = "Campo Obligatorio"
        }
        return valido
    }

}