package uv.tc.cowtrol.modelo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uv.tc.cowtrol.poko.Evento

class EventoBD (contexto: Context): SQLiteOpenHelper(contexto, NOMBRE_BD, null, VERSION_BD){
    companion object {
        const val NOMBRE_BD = "COWTROL"
        const val NOMBRE_TABLA = "evento"
        const val COL_SINIIGA = "siniiga"
        const val COL_FECHA = "fecha"
        const val COL_TIPO = "tipo"
        const val COL_DESCRIPCION = "descripcion"
        const val VERSION_BD = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_EVENTO = (
                "CREATE TABLE IF NOT EXISTS ${NOMBRE_TABLA} (" +
                        "$COL_SINIIGA INTEGER," +
                        "$COL_FECHA TEXT," +
                        "$COL_TIPO TEXT," +
                        "$COL_DESCRIPCION TEXT)"
                )
        db!!.execSQL(CREATE_TABLE_EVENTO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun crearTabla() {
        val db = writableDatabase
        val CREATE_TABLE_EVENTO = (
                "CREATE TABLE IF NOT EXISTS ${NOMBRE_TABLA} (" +
                        "$COL_SINIIGA INTEGER," +
                        "$COL_FECHA TEXT," +
                        "$COL_TIPO TEXT," +
                        "$COL_DESCRIPCION TEXT)"
                )
        db!!.execSQL(CREATE_TABLE_EVENTO)
    }

    fun añadirEvento(evento: Evento): Long {
        val db = writableDatabase
        val valoresInsert = ContentValues().apply {
            put(COL_SINIIGA, evento.siniiga)
            put(COL_FECHA, evento.fecha)
            put(COL_TIPO, evento.tipo)
            put(COL_DESCRIPCION, evento.descripcion)
        }
        val filasAfectadas = db.insert(NOMBRE_TABLA, null, valoresInsert)
        db.close()
        return filasAfectadas
    }
}