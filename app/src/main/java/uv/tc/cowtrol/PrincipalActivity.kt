package uv.tc.cowtrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import uv.tc.cowtrol.databinding.ActivityPrincipalBinding
import uv.tc.cowtrol.modelo.UsuariosBD

class PrincipalActivity : AppCompatActivity() {
    lateinit var binding: ActivityPrincipalBinding
    lateinit var usuario: UsuariosBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        usuario = UsuariosBD(this@PrincipalActivity)
        val correo = intent.getStringExtra("correo")
        val rancho = usuario.obtenerRanchoDelUsuario(correo.toString())
        Log.i("info Usuario", correo + " | " + rancho)

        binding.llPotrero.setOnClickListener{
            val intent= Intent(this@PrincipalActivity, MenuPotreroActivity::class.java).apply {
                putExtra("correo", correo)
            }
            startActivity(intent)
        }
        binding.llTrabajador.setOnClickListener {
            val intent = Intent(this@PrincipalActivity, RegistrarTrabajadorActivity::class.java).apply {
                putExtra("correo", correo)
            }
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