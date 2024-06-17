package uv.tc.cowtrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import uv.tc.cowtrol.databinding.ActivityControlReproduccionMainBinding
import uv.tc.cowtrol.modelo.ControlReproduccionBD
import uv.tc.cowtrol.modelo.UsuariosBD
import uv.tc.cowtrol.poko.ControlReproduccion

class ControlReproduccionMainActivity : AppCompatActivity() {
    lateinit var binding: ActivityControlReproduccionMainBinding
    lateinit var usuarioBD: UsuariosBD
    lateinit var controlBD: ControlReproduccionBD
    var rancho: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlReproduccionMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        controlBD = ControlReproduccionBD(this@ControlReproduccionMainActivity)
        usuarioBD = UsuariosBD(this@ControlReproduccionMainActivity)
        llenarListView()
        val correo = intent.getStringExtra("correo")
        rancho = usuarioBD.obtenerRanchoDelUsuario(correo.toString())
        rancho
        binding.btnAgregarControl.setOnClickListener {
            val intent = Intent(this@ControlReproduccionMainActivity, ControlReproduccionActivity::class.java).apply {
                putExtra("correo", correo)
            }
            startActivity(intent)
        }
        binding.btnRegresar.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        llenarListView()
    }

    private fun llenarListView (){
        val control = controlBD.obtenerControles(rancho.toString())
        val controlesReales = mutableListOf<String>()
        for(controles in control){
            val reproduccion = "Potrero: ${controles.potrero}\n" +
                    "Siniiga: ${controles.siniigaAnimal}\n" +
                    "Fecha de revision: ${controles.fechaRevision}\n" +
                    "Temporada de reproduccion: ${controles.temporada}\n" +
                    "Dia de Parto: ${controles.diaParto}\n" +
                    "Tipo: ${controles.tipo}\n" +
                    "Descripcion: ${controles.descripcion}\n" +
                    "Cargada: ${controles.cargada}\n" +
                    "----------------------------------"
            controlesReales.add(reproduccion)
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, controlesReales)
        binding.lvControlesAgregados.adapter = adapter
    }
}