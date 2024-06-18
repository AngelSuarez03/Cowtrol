package uv.tc.cowtrol

import android.R.drawable
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.cowtrol.databinding.ActivityModificarBecerroBinding
import uv.tc.cowtrol.modelo.BecerroBD
import uv.tc.cowtrol.modelo.PotreroBD
import uv.tc.cowtrol.modelo.UsuariosBD
import uv.tc.cowtrol.poko.Becerro
import uv.tc.cowtrol.poko.Usuario
import java.util.Calendar

class ModificarBecerroActivity : AppCompatActivity() {

    lateinit var binding: ActivityModificarBecerroBinding
    private var potreroSeleccionado: String = ""
    private lateinit var correo: String
    var rancho: String? = ""
    private lateinit var usuarioBD: UsuariosBD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarBecerroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        usuarioBD = UsuariosBD(this@ModificarBecerroActivity)

        correo = intent.getStringExtra("correo") ?: ""
        rancho = usuarioBD.obtenerRanchoDelUsuario(correo)

        val siiniga = intent.getIntExtra("siiniga", 0)
        val sexo = intent.getStringExtra("sexo")
        val edad = intent.getIntExtra("edad", 0)
        val nombre = intent.getStringExtra("nombre")
        val pesoNacer = intent.getFloatExtra("pesoNacer", 0F)
        val pesoDestete = intent.getFloatExtra("pesoDestete", 0F)
        val pesoDoce = intent.getFloatExtra("pesoDoce", 0F)
        val fechaNa = intent.getStringExtra("fechaNa")
        val rancho = intent.getStringExtra("rancho")
        val potrero = intent.getStringExtra("potrero")

        binding.etSinnigaModificar.setText(siiniga.toString())
        binding.etEdadBecerroModificar.setText(edad.toString())
        binding.etNombreBecerroModificar.setText(nombre)
        binding.etPesoNacerModificar.setText(pesoNacer.toString())
        binding.etPesoDesteteModificar.setText(pesoDestete.toString())
        binding.etPesoDoceModificar.setText(pesoDoce.toString())
        binding.etFechaNacimientoBecerroModificar.setText(fechaNa)


        potreroSeleccionado = potrero ?: ""

        val spinner = binding.spinnerPotreroModificar
        val nombresPotreros = cargarPotreros()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresPotreros)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.setSelection(nombresPotreros.indexOf(potreroSeleccionado))

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                potreroSeleccionado = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@ModificarBecerroActivity, "Selecciona el potrero", Toast.LENGTH_LONG).show()
            }
        }

        binding.tvMachoModificar.setOnClickListener {
            mostrarMensajeNoEditable()
        }

        binding.tvHembraModificar.setOnClickListener {
            mostrarMensajeNoEditable()
        }

        cambiarFondoSexo(sexo)

        val fechaNacimientoBecerro = binding.etFechaNacimientoBecerroModificar
        fechaNacimientoBecerro.setOnClickListener {
            mostrarDatePicker(fechaNacimientoBecerro)
        }

        binding.btnActualizarAnimal.setOnClickListener {
            if(validarCamposBecerro()){
                val nombre = binding.etNombreBecerroModificar.text.toString()
                val edad = binding.etEdadBecerroModificar.text.toString().toInt()
                val pesoNacer = binding.etPesoNacerModificar.text.toString().toFloat()
                val pesoDestete = binding.etPesoDesteteModificar.text.toString().toFloat()
                val pesoDoce = binding.etPesoDoceModificar.text.toString().toFloat()
                val fecha = binding.etFechaNacimientoBecerroModificar.text.toString()

                val becerroActualizar = Becerro(
                    sexo ?: "", nombre, siiniga, edad, pesoNacer, pesoDestete, pesoDoce, potreroSeleccionado, fecha, rancho ?: "")

                val becerroBD = BecerroBD(this)
                val filasAfectadas = becerroBD.actualizarBecerro(becerroActualizar)

                if (filasAfectadas > 0) {
                    Toast.makeText(this, "Becerro actualizado correctamente", Toast.LENGTH_SHORT).show()
                    binding.etNombreBecerroModificar.setText("")
                    binding.etNombreBecerroModificar.error = null
                    binding.etEdadBecerroModificar.setText("")
                    binding.etEdadBecerroModificar.error = null
                    binding.etPesoNacerModificar.setText("")
                    binding.etPesoDesteteModificar.setText("")
                    binding.etPesoDoceModificar.setText("")
                    binding.etFechaNacimientoBecerroModificar.setText("")
                    binding.etFechaNacimientoBecerroModificar.error = null
                    val intent = Intent(this@ModificarBecerroActivity, VisualizarBecerrosActivity::class.java)
                    intent.putExtra("correo", correo)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error al actualizar el becerro", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnRegresarMenuBecerro.setOnClickListener {
            val intent = Intent(this@ModificarBecerroActivity, VisualizarBecerrosActivity::class.java)
            intent.putExtra("correo", correo)
            startActivity(intent)
            finish()
        }
    }
    private fun cambiarFondoSexo(sexo: String?) {
        val textViewMacho = binding.tvMachoModificar
        val textViewHembra = binding.tvHembraModificar

        if (sexo == "Macho") {
            textViewMacho.setBackgroundResource(R.drawable.border_text_view_macho_seleccionado)
            textViewHembra.setBackgroundResource(R.drawable.border_text_view_hembra)
        } else if (sexo == "Hembra") {
            textViewMacho.setBackgroundResource(R.drawable.border_text_view_macho)
            textViewHembra.setBackgroundResource(R.drawable.border_text_view_hembra_seleccionado)
        }
    }

    private fun mostrarMensajeNoEditable() {
        Toast.makeText(this, "El sexo del becerro no se puede modificar", Toast.LENGTH_SHORT).show()
    }

    private fun cargarPotreros(): List<String> {
        val potreroBD = PotreroBD(this@ModificarBecerroActivity)
        val potreros = potreroBD.retornarPotrerosRancho(rancho.toString())
        return potreros.map { it.numeroPotrero.toString() }
    }


    private fun mostrarDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this, R.style.CustomDatePickerDialog,
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
        binding.etFechaNacimientoBecerroModificar.error = null
    }
    private fun validarCamposBecerro(): Boolean {
        var valido = true

        if (binding.etNombreBecerroModificar.text.toString().isEmpty()) {
            binding.etNombreBecerroModificar.error = "Nombre obligatorio"
            valido = false
        }

        if (binding.etEdadBecerroModificar.text.toString().isEmpty()) {
            binding.etEdadBecerroModificar.error = "Edad obligatoria"
            valido = false
        } else if (!binding.etEdadBecerroModificar.text.toString().matches(Regex("^\\d+\$"))) {
            binding.etEdadBecerroModificar.error = "Ingrese solo números enteros"
            valido = false
        }

        if (binding.etFechaNacimientoBecerroModificar.text.toString().isEmpty()) {
            binding.etFechaNacimientoBecerroModificar.error = "Fecha obligatoria"
            valido = false
        }
        return valido
    }
}
