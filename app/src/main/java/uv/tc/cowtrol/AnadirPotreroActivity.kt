package uv.tc.cowtrol

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import uv.tc.cowtrol.databinding.ActivityAnadirPotreroBinding
import uv.tc.cowtrol.modelo.PotreroBD
import uv.tc.cowtrol.poko.Potrero

class AnadirPotreroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnadirPotreroBinding
    private lateinit var modelo: PotreroBD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnadirPotreroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        modelo = PotreroBD(this@AnadirPotreroActivity)
        //modelo.crearTablaPotrero()
        binding.btRegistrarPotrero.setOnClickListener{
            if (validarDatos()){
                val potreroNuevo = Potrero(binding.etNumeroPotrero.text.toString().toInt(),binding.etNombre.text.toString(),binding.etCapacidad.text.toString().toInt(),
                    binding.etUbicacion.text.toString(),binding.etAncho.text.toString().toDouble(),binding.etLargo.text.toString().toDouble(),binding.etAlto.text.toString().toDouble())
                agregarPotrero(potreroNuevo)
            }
        }
        binding.btRegresarAnadirPotrero.setOnClickListener {
            finish()
        }
    }

    private fun agregarPotrero(potrero: Potrero){
        val resultadoInsercion = modelo.crearPotrero(potrero)
        val mensaje:String

        if (resultadoInsercion>0){
            mensaje = "Potrero registrado con éxito"
            binding.etNombre.setText("")
            binding.etUbicacion.setText("")
            binding.etAlto.setText("")
            binding.etAncho.setText("")
            binding.etLargo.setText("")
            binding.etCapacidad.setText("")
            binding.etNumeroPotrero.setText("")
        }else {
            mensaje = "Hubo un error al registrar el potrero, intentelo nuevamente"
        }
        Toast.makeText(this@AnadirPotreroActivity, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun validarDatos():Boolean{
        var validacion = true
        val nombre = binding.etNombre.text.toString()
        val ubicacion = binding.etUbicacion.text.toString()
        val alto = binding.etAlto.text.toString()
        val ancho = binding.etAncho.text.toString()
        val largo = binding.etLargo.text.toString()
        val numeroPotrero = binding.etNumeroPotrero.text.toString()
        val cantidad = binding.etCapacidad.text.toString()

        if (nombre.isEmpty()){
            binding.etNombre.error = "No pueden existir campos vacios"
            validacion=false
        }
        if (ubicacion.isEmpty()){
            binding.etUbicacion.error = "No pueden existir campos vacios"
            validacion=false
        }
        if (ancho.isEmpty()){
            binding.etAncho.error = "No pueden existir campos vacios"
            validacion=false
        }
        if (largo.isEmpty()){
            binding.etLargo.error = "No pueden existir campos vacios"
            validacion=false
        }
        if (alto.isEmpty()){
            binding.etAlto.error = "No pueden existir campos vacios"
            validacion=false
        }
        if (numeroPotrero.isEmpty()){
            binding.etNumeroPotrero.error = "No pueden existir campos vacios"
            validacion=false
        }
        if (cantidad.isEmpty()){
            binding.etCapacidad.error = "No pueden existir campos vacios"
            validacion=false
        }

        return validacion
    }
}