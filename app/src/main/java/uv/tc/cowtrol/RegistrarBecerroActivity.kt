package uv.tc.cowtrol


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.cowtrol.databinding.ActivityRegistrarBecerroBinding

class RegistrarBecerroActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegistrarBecerroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBecerroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}