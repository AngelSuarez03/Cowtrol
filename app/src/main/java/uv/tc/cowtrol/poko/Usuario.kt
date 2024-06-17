package uv.tc.cowtrol.poko

data class Usuario(
    val correo: String,
    val password: String,
    val tipo: String,
    val nombre: String?,
    val puesto: String?,
    val sexo: String?,
    val nombreRancho: String?,
    val potreroAsignado: String?,
    val edad: Int?
)
