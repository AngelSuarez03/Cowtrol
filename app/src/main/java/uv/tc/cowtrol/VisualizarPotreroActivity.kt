package uv.tc.cowtrol

import android.app.DatePickerDialog
import uv.tc.cowtrol.adaptadores.VisualizarPotreroAdapter
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import uv.tc.cowtrol.interfaces.ListenerRecycleBecerros
import uv.tc.cowtrol.databinding.ActivityModificarRanchoBinding
import uv.tc.cowtrol.databinding.ActivityVisualizarPotreroBinding
import uv.tc.cowtrol.modelo.BecerroBD
import uv.tc.cowtrol.modelo.EventoBD
import uv.tc.cowtrol.modelo.PotreroBD
import uv.tc.cowtrol.poko.Becerro
import uv.tc.cowtrol.poko.Evento
import java.util.Calendar

class VisualizarPotreroActivity : AppCompatActivity(), ListenerRecycleBecerros {
    lateinit var binding: ActivityVisualizarPotreroBinding
    lateinit var becerroBD: BecerroBD
    lateinit var potreroBD: PotreroBD
    lateinit var eventoBD: EventoBD
    var numeroPotrero: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisualizarPotreroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        eventoBD = EventoBD(this@VisualizarPotreroActivity)
        becerroBD = BecerroBD(this@VisualizarPotreroActivity)
        potreroBD = PotreroBD(this@VisualizarPotreroActivity)
        configurarRecyclerPotreros()
        llenarSpinner()
        binding.spPotrero.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedPotrero = parent.getItemAtPosition(position)
                numeroPotrero = selectedPotrero.toString().toInt()
                val becerros = becerroBD.seleccionarBecerrosPorPotrero(numeroPotrero)
                binding.rvPotreros.adapter = VisualizarPotreroAdapter(becerros, this@VisualizarPotreroActivity)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun configurarRecyclerPotreros() {
        binding.rvPotreros.layoutManager = LinearLayoutManager(this@VisualizarPotreroActivity)
        binding.rvPotreros.setHasFixedSize(true)
    }

    override fun clicEditarBecerro(becerro: Becerro, posicion: Int) {
        val builder = AlertDialog.Builder(this@VisualizarPotreroActivity)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_custom, null)
        val fecha: EditText = dialogLayout.findViewById(R.id.et_fecha_dialog)
        fecha.setOnClickListener {
            mostrarDatePicker(fecha)
        }
        val tipo: Spinner = dialogLayout.findViewById(R.id.sp_tipo_dialog)
        val spinnerArray = arrayOf("Revision Veterinario", "Vacunacion", "Sacrificio", "Marca", "Venta")
        val spinner_adapter = ArrayAdapter(this@VisualizarPotreroActivity, android.R.layout.simple_spinner_item, spinnerArray)
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipo.adapter = spinner_adapter
        val descripcion: EditText = dialogLayout.findViewById(R.id.et_descripcion_dialog)

        builder.setView(dialogLayout)
        val dialog = builder.create()

        val guardarEvento: Button = dialogLayout.findViewById(R.id.btn_guardar_evento)
        guardarEvento.setOnClickListener {
            var valido = true
            if(fecha.text.isEmpty()){
                valido = false
                fecha.error = "Campo Obligatorio"
            }
            if(descripcion.text.isEmpty()) {
                valido = false
                descripcion.error = "Campo Obligatorio"
            }
            if(valido) {
                val fecha_dialog = fecha.text.toString()
                val tipo_dialog = tipo.selectedItem.toString()
                val descripcion_dialog = descripcion.text.toString()
                val evento = Evento(becerro.siiniga, fecha_dialog, tipo_dialog, descripcion_dialog)
                añadirEvento(evento)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun llenarSpinner(){
        val potreros = potreroBD.retornarPotrerosRegistrados()
        val spinner_adapter = ArrayAdapter(this@VisualizarPotreroActivity, android.R.layout.simple_spinner_item, potreros.map { it.numeroPotrero })
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPotrero.adapter = spinner_adapter

    }

    private fun añadirEvento(evento: Evento) {
        val datosInsertados = eventoBD.añadirEvento(evento)
        var msj = ""
        if(datosInsertados > 0){
            msj = "Evento creado correctamente"
        }else{
            msj = "No se ha podido agregar el evento"
        }
        Toast.makeText(this@VisualizarPotreroActivity, msj, Toast.LENGTH_LONG).show()
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

        datePickerDialog.datePicker.maxDate = calendar.timeInMillis

        datePickerDialog.show()
    }

}