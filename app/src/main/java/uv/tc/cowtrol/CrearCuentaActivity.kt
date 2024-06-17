package uv.tc.cowtrol

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.cowtrol.databinding.ActivityCrearCuentaBinding
import uv.tc.cowtrol.modelo.UsuariosBD
import uv.tc.cowtrol.poko.Usuario

class CrearCuentaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearCuentaBinding
    private lateinit var modelo: UsuariosBD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        modelo = UsuariosBD(this@CrearCuentaActivity)
        //modelo.crearTabla()

        binding.btnCrearCuenta.setOnClickListener {
            if (validarCamposUsuario()) {
                val nuevoUsuario = Usuario(
                    binding.etCorreo.text.toString(), binding.etPassword.text.toString(), "Dueño", "", "", "", null, null, null)
                agregarUsuario(nuevoUsuario)
            }
        }

        binding.etCuentaExistente.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun agregarUsuario(usuario: Usuario) {
        val resultadoInsercion = modelo.insertarUsuario(usuario)
        var mensaje = ""
        if (resultadoInsercion > 0) {
            mensaje = "Usuario registrado exitosamente"
            binding.etCorreo.setText("")
            binding.etPassword.setText("")
            binding.etCorreo.error = null
            binding.etPassword.error = null

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            mensaje = "Hubo un error al registrar el usuario, intentelo nuevamente"
        }
        Toast.makeText(this@CrearCuentaActivity, mensaje, Toast.LENGTH_LONG).show()
    }

    fun validarCamposUsuario(): Boolean {
        var valido = true
        val correoValido = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        val correo = binding.etCorreo.text.toString()

        if (correo.isEmpty()) {
            binding.etCorreo.error = "Correo electrónico obligatorio"
            valido = false
        }else if(!correo.matches(correoValido.toRegex())){
            binding.etCorreo.error = "Correo electronico invalido"
            valido = false
        } else {
            binding.etCorreo.error = null
        }

        if (binding.etPassword.text.isNullOrEmpty()) {
            binding.etPassword.error = "Contraseña obligatoria"
            valido = false
        } else {
            binding.etPassword.error = null
        }
        return valido
    }
}