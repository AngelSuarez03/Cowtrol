package uv.tc.cowtrol


import android.R.color
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.cowtrol.databinding.ActivityRegistrarBecerroBinding
import uv.tc.cowtrol.modelo.BecerroBD
import uv.tc.cowtrol.modelo.PotreroBD
import uv.tc.cowtrol.modelo.UsuariosBD
import uv.tc.cowtrol.poko.Becerro
import java.util.Calendar

class RegistrarBecerroActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegistrarBecerroBinding
    private var sexoSeleccionado: String? = null
    private lateinit var modelo: BecerroBD
    private var potreroSeleccionado: String = ""
    private lateinit var cargarBecerros : VisualizarBecerrosActivity
    private lateinit var usuarioModelo: UsuariosBD
    private lateinit var correo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBecerroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        modelo = BecerroBD(this@RegistrarBecerroActivity)
        cargarBecerros = VisualizarBecerrosActivity()
        usuarioModelo = UsuariosBD(this@RegistrarBecerroActivity)

        correo = intent.getStringExtra("correo") ?: ""

        if (correo.isEmpty()) {
            Toast.makeText(this, "Correo no recibido", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        modelo.crearTabla()
        Log.i("RegistrarBecerroActivity", "Tabla becerro se está creando...")

        val spinner = binding.spinnerPotrero

        binding.tvMacho.setOnClickListener {
            seleccionSexo("Macho")
        }

        binding.tvHembra.setOnClickListener {
            seleccionSexo("Hembra")
        }

        val nombresPotreros = cargarPotreros()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresPotreros)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                potreroSeleccionado = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@RegistrarBecerroActivity, "Selecciona el potrero", Toast.LENGTH_LONG).show()
            }
        }

        val fechaNacimientoBecerro = binding.etFechaNacimientoBecerro
        fechaNacimientoBecerro.setOnClickListener {
            mostrarDatePicker(fechaNacimientoBecerro)
        }

        binding.btnRegistrarAnimal.setOnClickListener {
            if (validarCamposBecerro()) {
                val rancho = usuarioModelo.obtenerRanchoDelUsuario(correo) ?: ""
                val nuevoBecerro = Becerro(
                    sexoSeleccionado.toString(),
                    binding.etNombreBecerro.text.toString(),
                    binding.etSinniga.text.toString().toInt(),
                    binding.etEdadBecerro.text.toString().toInt(),
                    binding.etPesoNacer.text.toString().toFloat(),
                    binding.etPesoDestete.text.toString().toFloat(),
                    binding.etPesoDoce.text.toString().toFloat(),
                    potreroSeleccionado,
                    binding.etFechaNacimientoBecerro.text.toString(),
                    rancho
                )
                agregarBecerro(nuevoBecerro)

                val intent = Intent(this@RegistrarBecerroActivity, MenuBecerroActivity::class.java )
                intent.putExtra("sexoSeleccionado", sexoSeleccionado)
                intent.putExtra("correo", correo)
                startActivity(intent)
                finish()
            }
        }

        binding.btnRegresarMenuBecerro.setOnClickListener {
            val intent = Intent(this@RegistrarBecerroActivity, MenuBecerroActivity::class.java )
            intent.putExtra("sexoSeleccionado", sexoSeleccionado)
            intent.putExtra("correo", correo)
            startActivity(intent)
            finish()
        }
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
        binding.etFechaNacimientoBecerro.error = null
    }

    private fun seleccionSexo(sexo: String) {
        val textViewMacho = binding.tvMacho
        val textViewHembra = binding.tvHembra

        if (sexo == "Macho") {
            textViewMacho.setBackgroundResource(R.drawable.border_text_view_macho_seleccionado)
            textViewHembra.setBackgroundResource(R.drawable.border_text_view_hembra)
        } else {
            textViewMacho.setBackgroundResource(R.drawable.border_text_view_macho)
            textViewHembra.setBackgroundResource(R.drawable.border_text_view_hembra_seleccionado)
        }
        sexoSeleccionado = sexo
    }

    private fun agregarBecerro(becerro: Becerro) {
        Log.d("RegistrarBecerroActivity", "Insertando becerro en la base de datos...")
        val resultadoInsercion = modelo.insertarBecerro(becerro)
        var mensaje = ""
        val textViewMacho = binding.tvMacho
        val textViewHembra = binding.tvHembra
        if (resultadoInsercion > 0) {
            mensaje = "Becerro registrado exitosamente"
            textViewHembra.setBackgroundResource(R.drawable.border_text_view_hembra)
            textViewMacho.setBackgroundResource(R.drawable.border_text_view_macho)
            sexoSeleccionado = null
            binding.etNombreBecerro.setText("")
            binding.etSinniga.setText("")
            binding.etEdadBecerro.setText("")
            binding.etPesoNacer.setText("")
            binding.etPesoDestete.setText("")
            binding.etPesoDoce.setText("")
            binding.etFechaNacimientoBecerro.setText("")

            binding.etNombreBecerro.error = null
            binding.etSinniga.error = null
            binding.etEdadBecerro.error = null
            binding.etPesoNacer.error = null
            binding.etPesoDestete.error = null
            binding.etPesoDoce.error = null
            binding.etFechaNacimientoBecerro.error = null
        } else {
            mensaje = "Hubo un error al registrar el usuario, intentelo nuevamente"
        }
        Toast.makeText(this@RegistrarBecerroActivity, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun validarCamposBecerro(): Boolean {
        var valido = true

        if (sexoSeleccionado == null) {
            Toast.makeText(this, "Selecciona el sexo", Toast.LENGTH_LONG).show()
            valido = false
        }

        if (binding.etNombreBecerro.text.toString().isEmpty()) {
            binding.etNombreBecerro.error = "Nombre obligatorio"
            valido = false
        }

        if (binding.etSinniga.text.toString().isEmpty()) {
            binding.etSinniga.error = "SINIIGA obligatorio"
            valido = false
        } else if (!binding.etSinniga.text.toString().matches(Regex("^\\d+\$"))) {
            binding.etSinniga.error = "Ingrese solo números enteros"
            valido = false
        }

        if (binding.etEdadBecerro.text.toString().isEmpty()) {
            binding.etEdadBecerro.error = "Edad obligatorio"
            valido = false
        } else if (!binding.etEdadBecerro.text.toString().matches(Regex("^\\d+\$"))) {
            binding.etEdadBecerro.error = "Ingrese solo números enteros"
            valido = false
        }

        if (binding.etFechaNacimientoBecerro.text.toString().isEmpty()) {
            binding.etFechaNacimientoBecerro.error = "Fecha obligatoria"
            valido = false
        }

        return valido
    }

    private fun cargarPotreros(): List<Int> {
        val potreroBD = PotreroBD(this@RegistrarBecerroActivity)
        val potreros = potreroBD.retornarPotrerosRegistrados()
        return potreros.map { it.numeroPotrero }
    }
}
