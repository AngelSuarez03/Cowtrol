package uv.tc.cowtrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import uv.tc.cowtrol.databinding.ActivityLoginBinding
import uv.tc.cowtrol.modelo.BecerroBD
import uv.tc.cowtrol.modelo.ControlReproduccionBD
import uv.tc.cowtrol.modelo.PotreroBD
import uv.tc.cowtrol.modelo.UsuariosBD

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var potreroBd: PotreroBD
    lateinit var becerroBD: BecerroBD
    lateinit var modelo: UsuariosBD
    lateinit var controlBD: ControlReproduccionBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        potreroBd = PotreroBD(this@LoginActivity)

        //potreroBd.crearTablaPotrero()
        modelo = UsuariosBD(this@LoginActivity)

        becerroBD = BecerroBD(this@LoginActivity)
        //becerroBD.crearTabla()

        controlBD = ControlReproduccionBD(this@LoginActivity)
        //controlBD.crearTabla()
        //controlBD.dropTabla()

        var mensaje = ""
        binding.btnIngresarLogin.setOnClickListener{
            val correo = binding.etCorreoLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()
            if(modelo.validarUsuario(correo, password)){
                mensaje = "Bienvenido a Cowtrol"
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, PrincipalActivity::class.java).apply {
                    putExtra("correo", correo)
                }
                startActivity(intent)
            }else{
                mensaje = "Correo o cantraseña incorrecos"
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvCrearCuenta.setOnClickListener{
            val int = Intent (this@LoginActivity, CrearCuentaActivity::class.java)
            startActivity(int)
        }

    }
}