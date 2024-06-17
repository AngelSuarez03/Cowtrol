package uv.tc.cowtrol

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uv.tc.cowtrol.databinding.ActivitySolicitarAsesoriaVeterinariaBinding
import uv.tc.cowtrol.adaptadores.VeterinarioAdapter

class SolicitarAsesoriaVeterinariaActivity() : AppCompatActivity() {
    private lateinit var binding: ActivitySolicitarAsesoriaVeterinariaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySolicitarAsesoriaVeterinariaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerView =findViewById<RecyclerView>(R.id.rv_asesoria_veterinaria)
        val adapter = VeterinarioAdapter(){
            val url = "tel:" + it
            val uriContenido = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uriContenido)
            startActivity(intent)
            }

        binding.btRegresarAnadirPotrero.setOnClickListener {
            finish()
        }

        recyclerView.layoutManager = LinearLayoutManager(this@SolicitarAsesoriaVeterinariaActivity)
        recyclerView.adapter=adapter
    }
}