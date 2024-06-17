package uv.tc.cowtrol

import adaptadores.BecerroAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import interfaces.ListenerRecycleBecerros
import uv.tc.cowtrol.databinding.ActivityVisualizarBecerrosBinding
import uv.tc.cowtrol.modelo.BecerroBD
import uv.tc.cowtrol.poko.Becerro

class VisualizarBecerrosActivity : AppCompatActivity(), ListenerRecycleBecerros {
    private lateinit var binding: ActivityVisualizarBecerrosBinding
    private lateinit var modelo: BecerroBD
    private lateinit var correo: String
    private lateinit var sexo: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVisualizarBecerrosBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        modelo = BecerroBD(this@VisualizarBecerrosActivity)

        correo = intent.getStringExtra("correo") ?: ""

        sexo = intent.getStringExtra("sexoSeleccionado") ?:""

        confiugurarRecyclerBecerros()
        cargarMisBecerros()
    }

    override fun onResume() {
        super.onResume()
        cargarMisBecerros()
    }

     fun cargarMisBecerros(){
        val becerros = modelo.seleccionarBecerrosPorCorreo(correo.toString())
        if (becerros.size > 0){
            binding.tvSinBecerros.visibility = View.INVISIBLE
            binding.recyclerBecerros.visibility = View.VISIBLE
            val adaptadorBecerros = BecerroAdapter(becerros, this)
            binding.recyclerBecerros.adapter = adaptadorBecerros
        }else{
            binding.tvSinBecerros.visibility = View.VISIBLE
            binding.recyclerBecerros.visibility = View.INVISIBLE
        }
    }

    private fun confiugurarRecyclerBecerros(){
        binding.recyclerBecerros.layoutManager = LinearLayoutManager(this@VisualizarBecerrosActivity)
        binding.recyclerBecerros.setHasFixedSize(true)
    }

    override fun clicEditarBecerro(becerro: Becerro, posicion: Int) {
        val intent = Intent(this@VisualizarBecerrosActivity, ModificarBecerroActivity::class.java)
        intent.putExtra("siiniga", becerro.siiniga)
        intent.putExtra("correo", becerro.correoUsuario)
        intent.putExtra("sexo", becerro.sexo)
        intent.putExtra("edad", becerro.edad)
        intent.putExtra("pesoNacer", becerro.pesoNacer)
        intent.putExtra("pesoDestete", becerro.pesoDestete)
        intent.putExtra("pesoDoce", becerro.pesoDoce)
        intent.putExtra("fechaNa", becerro.fechaNacimiento)
        intent.putExtra("nombre", becerro.nombre)
        intent.putExtra("potrero", becerro.potrero)
        startActivity(intent)
    }
}