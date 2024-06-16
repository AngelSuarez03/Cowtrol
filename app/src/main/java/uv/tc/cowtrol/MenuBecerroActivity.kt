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

        binding.lnRegistrarBecerro.setOnClickListener {
            val intent = Intent(this@MenuBecerroActivity, RegistrarBecerroActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegresar.setOnClickListener{
            val intent = Intent(this@MenuBecerroActivity, PrincipalActivity::class.java)
            startActivity(intent)
        }
    }
}