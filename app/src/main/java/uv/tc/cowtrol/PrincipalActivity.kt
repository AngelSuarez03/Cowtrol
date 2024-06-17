package uv.tc.cowtrol

import android.content.Intent
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

        val correo = intent.getStringExtra("correo")

        binding.pruebaPotrero.setOnClickListener {
            val intent = Intent(this@PrincipalActivity, RegistroRanchoActivity::class.java)
            startActivity(intent)
        }
        binding.llPotrero.setOnClickListener{
            val intent= Intent(this@PrincipalActivity, AnadirPotreroActivity::class.java)
            startActivity(intent)
        }
        binding.llTrabajador.setOnClickListener {
            val intent = Intent(this@PrincipalActivity, RegistrarTrabajadorActivity::class.java)
            startActivity(intent)
        }
        binding.opcionBecerro.setOnClickListener {
            val intent = Intent(this, MenuBecerroActivity::class.java).apply {
                putExtra("correo", correo)
            }
            startActivity(intent)
        }
        binding.controlReproduccion.setOnClickListener {
            val intent = Intent(this@PrincipalActivity, ControlReproduccionMainActivity::class.java)
            startActivity(intent)
        }
        binding.lnAsesoriaVeterinaria.setOnClickListener {
            val intent = Intent(this@PrincipalActivity, SolicitarAsesoriaVeterinariaActivity::class.java)
            startActivity(intent)
        }
        binding.btnCerrarSesion.setOnClickListener {
            finish()
        }
    }
}