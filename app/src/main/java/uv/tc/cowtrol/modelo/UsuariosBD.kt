package uv.tc.cowtrol.modelo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UsuariosBD (contexto: Context) : SQLiteOpenHelper (contexto, NOMBRE_BD, null, VERSION_BD) {

    companion object{
        private const val NOMBRE_BD = "COWTROL"
        private const val NOMBRE_TABLA = "usuario"
        private const val COL_CORREO = "correo"
        private const val COL_PASSWORD = "password"
        private const val COL_TIPO = "tipo"
        private const val COL_NOMBRE = "nombre"
        private const val COL_PUESTO = "puesto"
        private const val COL_SEXO = "sexo"
        private const val COL_POTRERO = "potrero_asignado"
        private const val COL_EDAD = "edad"
        private const val VERSION_BD = 1

    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_USUARIOS = ("CREATE TABLE ${NOMBRE_TABLA} ($COL_CORREO TEXT PRIMARY KEY, $COL_PASSWORD TEXT, $COL_TIPO TEXT, $COL_NOMBRE TEXT, $COL_PUESTO TEXT, $COL_SEXO TEXT, $COL_POTRERO INTEGER, $COL_EDAD INTEGER)")
        db!!.execSQL(CREATE_TABLE_USUARIOS);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


}