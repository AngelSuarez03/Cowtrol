package uv.tc.cowtrol

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.cowtrol.databinding.ActivityMenuBecerroBinding

class MenuBecerroActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBecerroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBecerroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val correo = intent.getStringExtra("correo")

        val sexoSeleccionado = intent.getStringExtra("sexoSeleccionado")

        binding.lnRegistrarBecerro.setOnClickListener {
            val intent = Intent(this, RegistrarBecerroActivity::class.java).apply {
                putExtra("correo", correo)
            }
            startActivity(intent)
        }

        binding.lnVerBecerro.setOnClickListener {
            val intent = Intent(this, VisualizarBecerrosActivity::class.java).apply {
                putExtra("correo", correo)
                putExtra("sexoSeleccionado", sexoSeleccionado)
            }
            startActivity(intent)
        }

        binding.btnRegresar.setOnClickListener{
            val intent = Intent(this@MenuBecerroActivity, PrincipalActivity::class.java)
            startActivity(intent)
        }
    }
}