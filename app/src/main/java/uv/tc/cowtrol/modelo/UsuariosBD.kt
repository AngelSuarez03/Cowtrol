package uv.tc.cowtrol.modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uv.tc.cowtrol.poko.Usuario

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
        private const val COL_RANCHO = "nombreRancho"
        private const val COL_POTRERO = "potrero_asignado"
        private const val COL_EDAD = "edad"
        private const val VERSION_BD = 1

    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_USUARIOS = ("CREATE TABLE ${NOMBRE_TABLA} ($COL_CORREO TEXT PRIMARY KEY, $COL_PASSWORD TEXT, $COL_TIPO TEXT, $COL_NOMBRE TEXT, $COL_PUESTO TEXT, $COL_SEXO TEXT, $COL_RANCHO TEXT,$COL_POTRERO INTEGER, $COL_EDAD INTEGER)")
        db!!.execSQL(CREATE_TABLE_USUARIOS);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

     fun insertarUsuario(usuario: Usuario): Long {
        val db = writableDatabase
        val valoresInsert = ContentValues()
        valoresInsert.put(COL_CORREO, usuario.correo)
        valoresInsert.put(COL_PASSWORD, usuario.password)
        valoresInsert.put(COL_TIPO, usuario.tipo)
        valoresInsert.put(COL_NOMBRE, usuario.nombre)
        valoresInsert.put(COL_PUESTO, usuario.puesto)
        valoresInsert.put(COL_SEXO, usuario.sexo)
        valoresInsert.put(COL_RANCHO, usuario.nombreRancho)
        valoresInsert.put(COL_EDAD, usuario.edad)
        val filasAfectadas = db.insert(NOMBRE_TABLA, null, valoresInsert)
        db.close()
        return filasAfectadas
    }

    fun validarUsuario(correo: String, password: String): Boolean{
        val db = readableDatabase
        var usuarioValido = false
        val resultadoConsulta: Cursor = db.query(NOMBRE_TABLA, null, "$COL_CORREO = ? AND $COL_PASSWORD = ?", arrayOf(correo, password), null, null, null)
        if(resultadoConsulta != null){
            if(resultadoConsulta.moveToFirst()){
                usuarioValido = true
            }
            resultadoConsulta.close()
        }
        db.close()
        return usuarioValido
    }

}