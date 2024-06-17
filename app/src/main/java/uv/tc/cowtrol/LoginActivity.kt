package uv.tc.cowtrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import uv.tc.cowtrol.databinding.ActivityLoginBinding
import uv.tc.cowtrol.modelo.BecerroBD
import uv.tc.cowtrol.modelo.ControlReproduccionBD
import uv.tc.cowtrol.modelo.PotreroBD
import uv.tc.cowtrol.modelo.RanchoBD
import uv.tc.cowtrol.modelo.UsuariosBD
import uv.tc.cowtrol.poko.Rancho

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var potreroBd: PotreroBD
    lateinit var becerroBD: BecerroBD
    lateinit var modelo: UsuariosBD
    lateinit var ranchoBD: RanchoBD
    lateinit var controlBD: ControlReproduccionBD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        potreroBd = PotreroBD(this@LoginActivity)
        //potreroBd.eliminarTabla()
        //potreroBd.crearTablaPotrero()
        modelo = UsuariosBD(this@LoginActivity)
        //modelo.eliminarTabla()
        //modelo.crearTabla()

        becerroBD = BecerroBD(this@LoginActivity)
        //becerroBD.crearTabla()
        //becerroBD.eliminarTabla()

        controlBD = ControlReproduccionBD(this@LoginActivity)
        //controlBD.crearTabla()
        //controlBD.dropTabla()

        ranchoBD = RanchoBD(this@LoginActivity)
        //ranchoBD.crearTabla()

        var mensaje = ""

        binding.btnIngresarLogin.setOnClickListener {
            if (validarCamposUsuario()) {
                val correo = binding.etCorreoLogin.text.toString()
                val password = binding.etPasswordLogin.text.toString()
                val rancho = modelo.obtenerRanchoDelUsuario(binding.etCorreoLogin.text.toString())
                if (modelo.validarUsuario(correo, password)) {
                    mensaje = "Bienvenido a Cowtrol"
                    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                    if (rancho == null) {
                        val intent = Intent(this@LoginActivity, RegistroRanchoActivity::class.java).apply {
                            putExtra("correo", correo)
                        }
                        startActivity(intent)
                    }else if(rancho != null){
                        val intent = Intent(this, PrincipalActivity::class.java).apply {
                        putExtra("correo", correo)
                        putExtra("rancho", rancho)
                    }
                        startActivity(intent)
                }
                } else {
                    mensaje = "Correo o contraseña incorrectos"
                    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                    binding.etPasswordLogin.setText("")
                }
            }
        }

        binding.tvCrearCuenta.setOnClickListener {
            val int = Intent(this@LoginActivity, CrearCuentaActivity::class.java)
            startActivity(int)
        }
    }

    private fun validarCamposUsuario(): Boolean {
        var valido = true

        val correo = binding.etCorreoLogin.text.toString().trim()
        if (correo.isEmpty()) {
            binding.etCorreoLogin.error = "Correo electrónico obligatorio"
            valido = false
        } else {
            binding.etCorreoLogin.error = null
        }

        val password = binding.etPasswordLogin.text.toString().trim()
        if (password.isEmpty()) {
            binding.etPasswordLogin.error = "Password obligatorio"
            valido = false
        } else {
            binding.etPasswordLogin.error = null
        }

        return valido
    }
}