package uv.tc.cowtrol

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.cowtrol.databinding.ActivityModificarBecerroBinding
import uv.tc.cowtrol.modelo.BecerroBD
import uv.tc.cowtrol.poko.Becerro

class ModificarBecerroActivity : AppCompatActivity() {

    lateinit var binding: ActivityModificarBecerroBinding
    private var potreroSeleccionado: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarBecerroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val siiniga = intent.getIntExtra("siiniga", 0)

        val correo = intent.getStringExtra("correo")

        val sexo = intent.getStringExtra("sexo")

        binding.etSinniga.setText(siiniga.toString())

        val spinner = binding.spinnerPotrero

        val items = listOf("1", "2", "3")

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, items)

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
                Toast.makeText(this@ModificarBecerroActivity, "Selecciona el potrero", Toast.LENGTH_LONG).show()
            }
        }

        binding.tvMacho.setOnClickListener {
            mostrarMensajeNoEditable()
        }

        binding.tvHembra.setOnClickListener {
            mostrarMensajeNoEditable()
        }

        binding.btnActualizarAnimal.setOnClickListener {
            val nombre = binding.etNombreBecerro.text.toString()
            val edad = binding.etEdadBecerro.text.toString().toInt()
            val pesoNacer = binding.etPesoNacer.text.toString().toFloat()
            val pesoDestete = binding.etPesoDestete.text.toString().toFloat()
            val pesoDoce = binding.etPesoDoce.text.toString().toFloat()
            val fecha = binding.etFechaNacimientoBecerro.text.toString()

            // Crear objeto Becerro con los datos actualizados
            val becerroActualizar = Becerro(
                sexo ?: "", nombre, siiniga, edad, pesoNacer, pesoDestete, pesoDoce, potreroSeleccionado, fecha, correo ?: ""
            )

            // Actualizar el becerro en la base de datos
            val becerroBD = BecerroBD(this)
            val filasAfectadas = becerroBD.actualizarBecerro(becerroActualizar)

            if (filasAfectadas > 0) {
                Toast.makeText(this, "Becerro actualizado correctamente", Toast.LENGTH_SHORT).show()
                // Aquí puedes regresar a la actividad anterior o realizar otra acción
            } else {
                Toast.makeText(this, "Error al actualizar el becerro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarMensajeNoEditable() {
        Toast.makeText(this, "El sexo del becerro no se puede modificar", Toast.LENGTH_SHORT).show()
    }
}