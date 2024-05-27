package uv.tc.cowtrol

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.cowtrol.databinding.ActivityMainBinding
import uv.tc.cowtrol.databinding.ActivityRegistroRanchoBinding

class RegistroRanchoActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistroRanchoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroRanchoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}