package uv.tc.cowtrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import uv.tc.cowtrol.databinding.ActivityControlReproduccionMainBinding
import uv.tc.cowtrol.modelo.ControlReproduccionBD

class ControlReproduccionMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityControlReproduccionMainBinding
    lateinit var controlBD: ControlReproduccionBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlReproduccionMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        controlBD = ControlReproduccionBD(this@ControlReproduccionMainActivity)
        llenarListView()
        binding.btnAgregarControl.setOnClickListener {
            val intent = Intent(this@ControlReproduccionMainActivity, ControlReproduccionActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegresar.setOnClickListener {
            finish()
        }
    }

    private fun llenarListView (){
        val control = controlBD.obtenerControles()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, control)
        binding.lvControlesAgregados.adapter = adapter
    }
}