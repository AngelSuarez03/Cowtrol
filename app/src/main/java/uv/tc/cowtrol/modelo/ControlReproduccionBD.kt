package uv.tc.cowtrol.modelo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import uv.tc.cowtrol.poko.ControlReproduccion

class ControlReproduccionBD (contexto: Context): SQLiteOpenHelper(contexto, NOMBRE_BD, null, VERSION_BD) {

    companion object{
        private const val NOMBRE_BD = "COWTROL"
        private const val NOMBRE_TABLA = "controlReproduccion"
        private const val COL_POTRERO = "potrero"
        private const val COL_SINIIGA_ANIMAL = "siniigaAnimal"
        private const val COL_FECHA_REVISION = "fechaRevision"
        private const val COL_TEMPORADA_REPRODUCCION = "temporadaReproduccion"
        private const val COL_DIA_PARTO = "diaParto"
        private const val COL_TIPO = "tipo"
        private const val COL_Descripcion = "descripcion"
        private const val COL_CARGADA = "cargada"
        private const val VERSION_BD = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_BECERROS =
            ("CREATE TABLE IF NOT EXISTS ${NOMBRE_TABLA} ($COL_POTRERO INTEGER, $COL_SINIIGA_ANIMAL INTEGER, $COL_FECHA_REVISION TEXT, $COL_TEMPORADA_REPRODUCCION TEXT, $COL_DIA_PARTO TEXT" +
                    "$COL_TIPO TEXT, $COL_Descripcion TEXT, $COL_CARGADA INTEGER)")
        db?.execSQL(CREATE_TABLE_BECERROS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearTabla() {
        val db = writableDatabase
        val CREATE_TABLE_BECERROS =
            ("CREATE TABLE IF NOT EXISTS $NOMBRE_TABLA ($COL_POTRERO INTEGER, $COL_SINIIGA_ANIMAL INTEGER, $COL_FECHA_REVISION TEXT, $COL_TEMPORADA_REPRODUCCION TEXT, $COL_DIA_PARTO TEXT, $COL_TIPO TEXT, $COL_Descripcion TEXT, $COL_CARGADA INTEGER)")
        db?.execSQL(CREATE_TABLE_BECERROS)
    }

    fun dropTabla() {
        val db = writableDatabase
        val DROP_TABLE = ("DROP TABLE $NOMBRE_TABLA")
        db?.execSQL(DROP_TABLE)
    }

    fun añadirControl(control: ControlReproduccion): Long {
        val db = writableDatabase
        val valoresInsert = ContentValues().apply {
            put(COL_POTRERO, control.potrero)
            put(COL_SINIIGA_ANIMAL, control.siniigaAnimal)
            put(COL_FECHA_REVISION, control.fechaRevision)
            put(COL_TEMPORADA_REPRODUCCION, control.temporada)
            put(COL_DIA_PARTO, control.diaParto)
            put(COL_TIPO, control.tipo)
            put(COL_Descripcion, control.descripcion)
            put(COL_CARGADA, control.cargada)
        }
        val filasAfectadas = db.insert(NOMBRE_TABLA, null, valoresInsert)
        db.close()
        return filasAfectadas
    }

}