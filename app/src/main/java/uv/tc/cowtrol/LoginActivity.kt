package uv.tc.cowtrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import uv.tc.cowtrol.databinding.ActivityLoginBinding
import uv.tc.cowtrol.modelo.PotreroBD
import uv.tc.cowtrol.modelo.UsuariosBD

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var potreroBd: PotreroBD
    lateinit var modelo: UsuariosBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        potreroBd = PotreroBD(this@LoginActivity)

        //potreroBd.crearTablaPotrero()
        modelo = UsuariosBD(this@LoginActivity)

        var mensaje = ""
        binding.btnIngresarLogin.setOnClickListener{
            val correo = binding.etCorreoLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()
            if(modelo.validarUsuario(correo, password)){
                mensaje = "Bienvenido a Cowtrol"
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, PrincipalActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                mensaje = "Correo o cantraseña incorrecos"
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            }
        }
    }
}