package uv.tc.cowtrol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.cowtrol.databinding.ActivityModificarBecerroBinding
class ModificarBecerroActivity : AppCompatActivity() {

    lateinit var binding: ActivityModificarBecerroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarBecerroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}