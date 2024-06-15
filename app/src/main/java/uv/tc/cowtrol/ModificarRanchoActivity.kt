package uv.tc.cowtrol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.cowtrol.databinding.ActivityModificarRanchoBinding
import uv.tc.cowtrol.databinding.ActivityRegistroRanchoBinding

class ModificarRanchoActivity : AppCompatActivity() {
    lateinit var binding: ActivityModificarRanchoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarRanchoBinding.inflate(layoutInflater)
        val view =binding.root
        setContentView(view)
    }
}