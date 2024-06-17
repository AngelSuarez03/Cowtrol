package uv.tc.cowtrol

import android.R.drawable
import android.content.Intent
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
import uv.tc.cowtrol.modelo.PotreroBD
import uv.tc.cowtrol.poko.Becerro

class ModificarBecerroActivity : AppCompatActivity() {

    lateinit var binding: ActivityModificarBecerroBinding
    private var potreroSeleccionado: String = ""
    private lateinit var correo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarBecerroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        correo = intent.getStringExtra("correo") ?: ""

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

        binding.etSinniga.setText(siiniga.toString())
        binding.etEdadBecerro.setText(edad.toString())
        binding.etNombreBecerro.setText(nombre)
        binding.etPesoNacer.setText(pesoNacer.toString())
        binding.etPesoDestete.setText(pesoDestete.toString())
        binding.etPesoDoce.setText(pesoDoce.toString())
        binding.etFechaNacimientoBecerro.setText(fechaNa)

        // Setear el potrero seleccionado
        potreroSeleccionado = potrero ?: ""

        val spinner = binding.spinnerPotrero
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

        binding.tvMacho.setOnClickListener {
            mostrarMensajeNoEditable()
        }

        binding.tvHembra.setOnClickListener {
            mostrarMensajeNoEditable()
        }

        cambiarFondoSexo(sexo)

        binding.btnActualizarAnimal.setOnClickListener {
            val nombre = binding.etNombreBecerro.text.toString()
            val edad = binding.etEdadBecerro.text.toString().toInt()
            val pesoNacer = binding.etPesoNacer.text.toString().toFloat()
            val pesoDestete = binding.etPesoDestete.text.toString().toFloat()
            val pesoDoce = binding.etPesoDoce.text.toString().toFloat()
            val fecha = binding.etFechaNacimientoBecerro.text.toString()

            val becerroActualizar = Becerro(
                sexo ?: "", nombre, siiniga, edad, pesoNacer, pesoDestete, pesoDoce, potreroSeleccionado, fecha, rancho ?: "")

            val becerroBD = BecerroBD(this)
            val filasAfectadas = becerroBD.actualizarBecerro(becerroActualizar)

            if (filasAfectadas > 0) {
                Toast.makeText(this, "Becerro actualizado correctamente", Toast.LENGTH_SHORT).show()
                binding.etNombreBecerro.setText("")
                binding.etEdadBecerro.setText("")
                binding.etPesoNacer.setText("")
                binding.etPesoDestete.setText("")
                binding.etPesoDoce.setText("")
                binding.etFechaNacimientoBecerro.setText("")
                val intent = Intent(this@ModificarBecerroActivity, VisualizarBecerrosActivity::class.java)
                intent.putExtra("correo", correo)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error al actualizar el becerro", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cambiarFondoSexo(sexo: String?) {
        val textViewMacho = binding.tvMacho
        val textViewHembra = binding.tvHembra

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
        val potreros = potreroBD.retornarPotrerosRegistrados()
        return potreros.map { it.numeroPotrero.toString() }
    }
}
