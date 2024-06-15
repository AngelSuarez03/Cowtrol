package uv.tc.cowtrol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.cowtrol.databinding.ActivityModificarRanchoBinding
import uv.tc.cowtrol.databinding.ActivityVisualizarPotreroBinding

class VisualizarPotreroActivity : AppCompatActivity() {
    lateinit var binding: ActivityVisualizarPotreroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityVisualizarPotreroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}