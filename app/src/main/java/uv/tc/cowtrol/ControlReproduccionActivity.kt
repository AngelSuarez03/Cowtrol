package uv.tc.cowtrol

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import uv.tc.cowtrol.databinding.ActivityControlReproduccionBinding
import uv.tc.cowtrol.modelo.BecerroBD
import uv.tc.cowtrol.modelo.PotreroBD
import uv.tc.cowtrol.poko.Potrero

class ControlReproduccionActivity : AppCompatActivity() {
    lateinit var binding: ActivityControlReproduccionBinding
    lateinit var potreroBD: PotreroBD
    lateinit var becerrosBD: BecerroBD
    var correo: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlReproduccionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        correo = intent.getStringExtra("correo")
        potreroBD = PotreroBD(this@ControlReproduccionActivity)
        becerrosBD = BecerroBD(this@ControlReproduccionActivity)
        llenarSpinners()
        binding.btnGuardarControl.setOnClickListener {

        }
    }

    private fun llenarSpinners(): Unit {
        val potreros = cargarPotreros()
        val becerros = cargarBecerros()
        val tipo = arrayOf("Monta", "Inseminación","Transferencia de Embriones")
        val spinner_adapter_potreros = ArrayAdapter(this@ControlReproduccionActivity, R.layout.simple_spinner_item, potreros)
        spinner_adapter_potreros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPotreros.adapter = spinner_adapter_potreros
        val spinner_adapter_becerros = ArrayAdapter(this@ControlReproduccionActivity, R.layout.simple_spinner_item, becerros)
        spinner_adapter_becerros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spAnimales.adapter = spinner_adapter_becerros
        val spinner_adapter_tipo = ArrayAdapter(this@ControlReproduccionActivity, R.layout.simple_spinner_item, tipo)
        spinner_adapter_becerros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spTipo.adapter = spinner_adapter_tipo
    }

    private fun cargarPotreros(): List<Int> {
        val potrero = potreroBD.retornarPotrerosRegistrados()
        return potrero.map { it.numeroPotrero }
    }

    private fun cargarBecerros(): List<Int> {
        val becerro = becerrosBD.seleccionarBecerrosPorCorreo(correo.toString())
        return becerro.map { it.siiniga }
    }
}