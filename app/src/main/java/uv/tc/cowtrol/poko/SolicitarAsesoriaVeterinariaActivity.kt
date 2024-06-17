package uv.tc.cowtrol.poko

import android.media.session.PlaybackState.CustomAction
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uv.tc.cowtrol.R
import uv.tc.cowtrol.databinding.ActivityAnadirPotreroBinding
import uv.tc.cowtrol.databinding.ActivitySolicitarAsesoriaVeterinariaBinding

class SolicitarAsesoriaVeterinariaActivity() : AppCompatActivity() {
    private lateinit var binding: ActivitySolicitarAsesoriaVeterinariaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySolicitarAsesoriaVeterinariaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerView =findViewById<RecyclerView>(R.id.rv_asesoria_veterinaria)
        val adapter = AdaptadorVeterinario(){
            Toast.makeText(this@SolicitarAsesoriaVeterinariaActivity,"telefono $it",Toast.LENGTH_LONG).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this@SolicitarAsesoriaVeterinariaActivity)
        recyclerView.adapter=adapter
    }
}