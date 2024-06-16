package uv.tc.cowtrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.cowtrol.databinding.ActivityCrearCuentaBinding
import uv.tc.cowtrol.databinding.ActivityMainBinding
import uv.tc.cowtrol.poko.AnadirPotreroActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnPrueba.setOnClickListener {
            val intent = Intent(this,   CrearCuentaActivity::class.java)
            startActivity(intent)
        }
    }
}