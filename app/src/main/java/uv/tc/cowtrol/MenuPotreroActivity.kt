package uv.tc.cowtrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uv.tc.cowtrol.databinding.ActivityMenuPotreroBinding

class MenuPotreroActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuPotreroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPotreroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.lnRegistrarPotrero.setOnClickListener {
            val intent = Intent(this@MenuPotreroActivity, AnadirPotreroActivity::class.java)
            startActivity(intent)
        }
        binding.lnVerPotrero.setOnClickListener {
            val intent = Intent(this@MenuPotreroActivity, VisualizarPotreroActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegresar.setOnClickListener {
            finish()
        }
    }
}