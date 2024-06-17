package uv.tc.cowtrol.poko

data class Becerro(
    val sexo: String,
    val nombre: String,
    val siiniga: Int,
    val edad: Int,
    val pesoNacer: Float?,
    val pesoDestete: Float?,
    val pesoDoce: Float?,
    val potrero: String,
    val fechaNacimiento: String,
    val correoUsuario: String,
    val ranchoRegistrado: String?
)