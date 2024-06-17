package uv.tc.cowtrol.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uv.tc.cowtrol.poko.Rancho

class RanchoBD (contexto: Context) : SQLiteOpenHelper(contexto, NOMBRE_BD, null, VERSION_BD) {

    companion object{
        private const val NOMBRE_BD = "COWTROL"
        private const val NOMBRE_TABLA = "rancho"
        private const val COL_NOMBRE = "nombre"
        private const val COL_UBICACION = "ubicacion"
        private const val COL_TELEFONO = "telefono"
        private const val COL_PROVINCIA = "provincia"
        private const val VERSION_BD = 1
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_RANCHO = ("CREATE TABLE ${NOMBRE_TABLA} (${COL_NOMBRE} TEXT PRIMARY KEY, ${COL_UBICACION} TEXT, ${COL_TELEFONO} TEXT, ${COL_PROVINCIA} TEXT)")
        db!!.execSQL(CREATE_TABLE_RANCHO)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun crearTabla(){
        val db = writableDatabase
        val CREATE_TABLE_RANCHO = ("CREATE TABLE ${NOMBRE_TABLA} (${COL_NOMBRE} TEXT PRIMARY KEY, ${COL_UBICACION} TEXT, ${COL_TELEFONO} TEXT, ${COL_PROVINCIA} TEXT)")
        db!!.execSQL(CREATE_TABLE_RANCHO)
    }

    fun crearRancho(rancho: Rancho):Long{
        val db = writableDatabase
        val valoresInsert = ContentValues()
        valoresInsert.put(COL_NOMBRE,rancho.nombre)
        valoresInsert.put(COL_UBICACION,rancho.ubicacion)
        valoresInsert.put(COL_TELEFONO,rancho.telefono)
        valoresInsert.put(COL_PROVINCIA,rancho.provincia)
        val filasAfectadas =db.insert(NOMBRE_TABLA,null,valoresInsert)
        db.close()
        return filasAfectadas
    }
}