package uv.tc.cowtrol

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.cowtrol.databinding.ActivityMainBinding
import uv.tc.cowtrol.databinding.ActivityRegistroRanchoBinding
import uv.tc.cowtrol.modelo.RanchoBD
import uv.tc.cowtrol.poko.Rancho

class RegistroRanchoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroRanchoBinding
    private lateinit var modelo: RanchoBD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroRanchoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        modelo = RanchoBD(this@RegistroRanchoActivity)
        modelo.crearTabla()

        binding.btRegistrarRancho.setOnClickListener{
            if (validarCampos()){
                val nuevoRancho = Rancho(binding.etNombreRegistroRancho.text.toString(),binding.etUbicacion.text.toString(),binding.etTelefono.text.toString().toInt(),
                    binding.etProvincia.text.toString())
                agregarRancho(nuevoRancho)
            }
        }
        binding.btRegresarRegistroRancho.setOnClickListener {
            finish()
        }
    }

    private fun agregarRancho(rancho: Rancho){
        val resultadoInsercion = modelo.crearRancho(rancho)
        val mensaje:String
        if (resultadoInsercion>0){
            mensaje = "Rancho creado con éxito"
            binding.etNombreRegistroRancho.setText("")
            binding.etTelefono.setText("")
            binding.etUbicacion.setText("")
            binding.etProvincia.setText("")
        }else {
            mensaje = "Hubo un error al registrar el rancho, intentelo nuevamente"
        }
        Toast.makeText(this@RegistroRanchoActivity, mensaje, Toast.LENGTH_LONG).show()
    }

     private fun validarCampos():Boolean{
        var validacion = true

        val nombre = binding.etNombreRegistroRancho.text.toString()
        val ubicacion =binding.etUbicacion.text.toString()
        val telefono =binding.etTelefono.text.toString()
        val provincia =binding.etProvincia.text.toString()

        if (nombre.isEmpty()){
            binding.etNombreRegistroRancho.error="No pueden quedar espacios en blanco"
            validacion=false
        }
         if (ubicacion.isEmpty()){
             binding.etUbicacion.error="No pueden quedar espacios en blanco"
             validacion=false
         }
         if (telefono.isEmpty()){
             binding.etTelefono.error="No pueden quedar espacios en blanco"
             validacion=false
         }
         if (provincia.isEmpty()){
             binding.etProvincia.error="No pueden quedar espacios en blanco"
             validacion=false
         }
        return validacion
    }
}