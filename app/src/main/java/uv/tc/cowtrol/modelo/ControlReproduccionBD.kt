package uv.tc.cowtrol.modelo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import uv.tc.cowtrol.poko.ControlReproduccion
import uv.tc.cowtrol.poko.Potrero

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
        private const val COL_RANCHO = "rancho"
        private const val VERSION_BD = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_BECERROS =
            ("CREATE TABLE IF NOT EXISTS ${NOMBRE_TABLA} ($COL_POTRERO INTEGER, $COL_SINIIGA_ANIMAL INTEGER, $COL_FECHA_REVISION TEXT, $COL_TEMPORADA_REPRODUCCION TEXT, $COL_DIA_PARTO TEXT" +
                    "$COL_TIPO TEXT, $COL_Descripcion TEXT, $COL_CARGADA INTEGER, $COL_RANCHO TEXT)")
        db?.execSQL(CREATE_TABLE_BECERROS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearTabla() {
        val db = writableDatabase
        val CREATE_TABLE_BECERROS =
            ("CREATE TABLE IF NOT EXISTS $NOMBRE_TABLA ($COL_POTRERO INTEGER, $COL_SINIIGA_ANIMAL INTEGER, $COL_FECHA_REVISION TEXT, $COL_TEMPORADA_REPRODUCCION TEXT, $COL_DIA_PARTO TEXT, $COL_TIPO TEXT, $COL_Descripcion TEXT, $COL_CARGADA INTEGER, $COL_RANCHO TEXT)")
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
            put(COL_RANCHO, control.rancho)
        }
        val filasAfectadas = db.insert(NOMBRE_TABLA, null, valoresInsert)
        db.close()
        return filasAfectadas
    }

    @SuppressLint("Range")
    fun obtenerControles(rancho: String): List<ControlReproduccion>{
        val db = readableDatabase
        val controles = mutableListOf<ControlReproduccion>()
        val resultadoConsulta: Cursor? = db.query(NOMBRE_TABLA, null, "$COL_RANCHO = ?",
            arrayOf(rancho),null,null,null)
        if(resultadoConsulta != null){
            while (resultadoConsulta.moveToNext()){
                val numeroPotrero = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_POTRERO))
                val siniiga = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_SINIIGA_ANIMAL
                ))
                val fechaRevision = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_FECHA_REVISION
                ))
                val temporadaReproduccion = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_TEMPORADA_REPRODUCCION
                ))
                val diaParto = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_DIA_PARTO
                ))
                val tipo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_TIPO
                ))
                val descripcion = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_Descripcion
                ))
                val cargada = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_CARGADA
                ))
                val rancho = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_RANCHO))
                val control = ControlReproduccion(numeroPotrero, siniiga, fechaRevision, temporadaReproduccion, diaParto, tipo, descripcion, cargada.toString().toBoolean(), rancho)
                controles.add(control)
            }
            resultadoConsulta.close()
        }
        db.close()
        return controles
    }

}