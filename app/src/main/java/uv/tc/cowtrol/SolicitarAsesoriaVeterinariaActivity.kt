package uv.tc.cowtrol

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uv.tc.cowtrol.databinding.ActivitySolicitarAsesoriaVeterinariaBinding
import adaptadores.AdaptadorVeterinario

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