package uv.tc.cowtrol.poko

data class ControlReproduccion(
    val potrero: Int,
    val siniigaAnimal: Int,
    val fechaRevision: String,
    val temporada: String,
    val diaParto: String,
    val tipo: String,
    val descripcion: String?,
    val cargada: Boolean,
    val rancho: String
)
