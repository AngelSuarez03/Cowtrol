package uv.tc.cowtrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uv.tc.cowtrol.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {
    lateinit var binding: ActivityPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}