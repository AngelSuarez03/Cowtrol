package uv.tc.cowtrol

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import uv.tc.cowtrol.databinding.ActivityRegistrarTrabajadorBinding

class RegistrarTrabajadorActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrarTrabajadorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarTrabajadorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        opcionesSpinnerPuesto()
        binding.btnRegistrarTrabajador.setOnClickListener {

        }
    }

    private fun opcionesSpinnerPuesto(){
        val puestos = arrayOf("Capataz", "Ganadero", "")
        val spinner_adapter= ArrayAdapter(this@RegistrarTrabajadorActivity, R.layout.simple_spinner_item, puestos)
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPuestos.adapter = spinner_adapter
    }
}