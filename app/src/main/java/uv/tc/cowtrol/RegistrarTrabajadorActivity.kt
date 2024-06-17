package uv.tc.cowtrol

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import uv.tc.cowtrol.databinding.ActivityRegistrarTrabajadorBinding
import uv.tc.cowtrol.modelo.PotreroBD
import uv.tc.cowtrol.modelo.UsuariosBD
import uv.tc.cowtrol.poko.Potrero
import uv.tc.cowtrol.poko.Usuario
import java.security.SecureRandom

class RegistrarTrabajadorActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrarTrabajadorBinding
    lateinit var potreroBD: PotreroBD
    lateinit var usuarioBD: UsuariosBD
    var correo: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarTrabajadorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        potreroBD = PotreroBD(this@RegistrarTrabajadorActivity)
        usuarioBD = UsuariosBD(this@RegistrarTrabajadorActivity)
        correo = intent.getStringExtra("correo")
        val rancho = usuarioBD.obtenerRanchoDelUsuario(correo.toString())
        opcionesSpinnerPuesto()
        cargarPotrerosRegistrados()
        binding.btnRegistrarTrabajador.setOnClickListener {
            if(validarCampos()) {
                val correo = binding.etCorreoTrabajador.text.toString()
                val password = generarContraseña()
                val nombre = binding.etNombreTrabajador.text.toString()
                val potrero = binding.spPotreroAsignado.selectedItem.toString()
                val edad = binding.etEdad.text.toString().toInt()
                val puesto = binding.spPuestos.selectedItem.toString()
                var sexo = ""
                if (binding.rbHombre.isActivated)
                    sexo = "Hombre"
                else if (binding.rbMujer.isActivated)
                    sexo = "Mujer"
                val usuario = Usuario(
                    correo,
                    password,
                    "Trabajador",
                    nombre,
                    puesto,
                    sexo,
                    rancho,
                    potrero,
                    edad
                )
                registrarTrabajador(usuario)
                dialogoPassword(password)
            }
        }
        binding.btnRegresar.setOnClickListener{
            finish()
        }
    }

    private fun opcionesSpinnerPuesto(){
        val puestos = arrayOf("Capataz", "Pastor", "Mantenimiento", "Encargado de Bebederos", "Encargado de Alimentación")
        val spinner_adapter = ArrayAdapter(this@RegistrarTrabajadorActivity, R.layout.simple_spinner_item, puestos)
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPuestos.adapter = spinner_adapter
    }

    private fun cargarPotrerosRegistrados() {
        val potreros = potreroBD.retornarPotrerosRegistrados()
            val spinner_adapter = ArrayAdapter(this@RegistrarTrabajadorActivity, R.layout.simple_spinner_item, potreros(potreros))
            spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spPotreroAsignado.adapter = spinner_adapter
    }

    private fun potreros(potrero: List<Potrero>): List<Int> {
        return potrero.map { it.numeroPotrero }
    }

    private fun registrarTrabajador(trabajador: Usuario) {
        var msj = ""
        val resultadoIncersion = usuarioBD.insertarUsuario(trabajador)
        if(resultadoIncersion > 0){
            msj = "Trabajador registrado correctamente"
            limpiarCampos()
        } else {
            msj = "Error al registrar el trabajador\nVerifique los campos"
        }
        Toast.makeText(this@RegistrarTrabajadorActivity, msj, Toast.LENGTH_LONG).show()
    }

    private fun generarContraseña(): String{
        val minusculas = "abcdefghijklmnopqrstuvwxyz"
        val mayusculas = minusculas.uppercase()
        val digitos = "0123456789"
        val caracteresEspeciales = "!@#$%^&*()-_"
        val permitidas = minusculas + mayusculas + digitos + caracteresEspeciales
        val tamaño = 10
        val random = SecureRandom()
        val password = StringBuilder(tamaño)
        for (i in 0 until tamaño) {
            val index = random.nextInt(permitidas.length)
            password.append(permitidas[index])
        }
        return password.toString()
    }

    private fun dialogoPassword(password: String) {
        val builder = AlertDialog.Builder(this@RegistrarTrabajadorActivity)
        builder.setTitle("Contraseña Generada")
        builder.setMessage("La contraseña generada es: $password")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun limpiarCampos() {
        binding.etNombreTrabajador.setText("")
        binding.etEdad.setText("")
        binding.etCorreoTrabajador.setText("")
        binding.rbMujer.isActivated = false
        binding.rbHombre.isActivated = false
    }

    private fun validarCampos(): Boolean {
        var valido = true
        val correoValido = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        if(binding.etNombreTrabajador.text.isEmpty()) {
            valido = false
            binding.etNombreTrabajador.error = "Campo Obligatorio"
        }
        if(binding.etEdad.text.isEmpty()){
            valido = false
            binding.etEdad.error = "Campo Obligatorio"
        }
        if(binding.etCorreoTrabajador.text.isEmpty()){
            valido = false
            binding.etCorreoTrabajador.error = "Campo Obligatorio"
        }
        if(!binding.etCorreoTrabajador.text.matches(correoValido.toRegex())){
            valido = false
            binding.etCorreoTrabajador.error = "Correo electronico invalido"
        }
        return valido
    }

}