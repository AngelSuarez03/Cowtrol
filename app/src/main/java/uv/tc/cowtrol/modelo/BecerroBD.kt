package uv.tc.cowtrol.modelo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf
import uv.tc.cowtrol.poko.Becerro

class BecerroBD(contexto: Context) : SQLiteOpenHelper(contexto, NOMBRE_BD, null, VERSION_BD) {

    companion object {
        const val NOMBRE_BD = "COWTROL"
        const val NOMBRE_TABLA = "becerro"
        const val COL_SEXO = "sexo"
        const val COL_NOMBRE = "nombre"
        const val COL_SINIIGA = "siniiga"
        const val COL_EDAD = "edad"
        const val COL_PESO_NACER = "peso_nacer"
        const val COL_PESO_DESTETE = "peso_destete"
        const val COL_PESO_12 = "peso_doce"
        const val COL_POTRERO = "potrero"
        const val COL_FECHA_NAC = "fecha_nacimiento"
        const val COL_RANCHO = "rancho"
        const val VERSION_BD = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_BECERROS = (
            "CREATE TABLE IF NOT EXISTS $NOMBRE_TABLA (" +
                    "$COL_SEXO TEXT, " +
                    "$COL_NOMBRE TEXT, " +
                    "$COL_SINIIGA INTEGER PRIMARY KEY, " +
                    "$COL_EDAD INTEGER, " +
                    "$COL_PESO_NACER REAL, " +
                    "$COL_PESO_DESTETE REAL, " +
                    "$COL_PESO_12 REAL, " +
                    "$COL_POTRERO INTEGER, " +
                    "$COL_FECHA_NAC TEXT, " +
                    "$COL_RANCHO TEXT)"
        )
        db!!.execSQL(CREATE_TABLE_BECERROS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Código de actualización de la base de datos, si es necesario
    }
    fun crearTabla(){
        val db = writableDatabase
        val CREATE_TABLE_BECERROS = (
                "CREATE TABLE IF NOT EXISTS $NOMBRE_TABLA (" +
                        "$COL_SEXO TEXT, " +
                        "$COL_NOMBRE TEXT, " +
                        "$COL_SINIIGA INTEGER PRIMARY KEY, " +
                        "$COL_EDAD INTEGER, " +
                        "$COL_PESO_NACER REAL, " +
                        "$COL_PESO_DESTETE REAL, " +
                        "$COL_PESO_12 REAL, " +
                        "$COL_POTRERO INTEGER, " +
                        "$COL_FECHA_NAC TEXT, " +
                        "$COL_RANCHO TEXT)"
                )
        db!!.execSQL(CREATE_TABLE_BECERROS)
    }

    fun eliminarTabla() {
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS ${BecerroBD.NOMBRE_TABLA}") // Eliminar la tabla si existe
        db.close()
    }

    fun insertarBecerro(becerro: Becerro): Long {
        val db = writableDatabase
        val valoresInsert = ContentValues().apply {
            put(COL_SEXO, becerro.sexo)
            put(COL_NOMBRE, becerro.nombre)
            put(COL_SINIIGA, becerro.siiniga)
            put(COL_EDAD, becerro.edad)
            put(COL_PESO_NACER, becerro.pesoNacer)
            put(COL_PESO_DESTETE, becerro.pesoDestete)
            put(COL_PESO_12, becerro.pesoDoce)
            put(COL_POTRERO, becerro.potrero)
            put(COL_FECHA_NAC, becerro.fechaNacimiento)
            put(COL_RANCHO, becerro.rancho)
        }
        val filasAfectadas = db.insert(NOMBRE_TABLA, null, valoresInsert)
        db.close()
        return filasAfectadas
    }

    @SuppressLint("Range")
    fun seleccionarBecerrosPorRancho(rancho: String): List<Becerro> {
        val misBecerros = mutableListOf<Becerro>()
        val db = readableDatabase
        val resultadoConsulta: Cursor = db.query(NOMBRE_TABLA, null, "$COL_RANCHO = ?", arrayOf(rancho), null, null, null)
        if (resultadoConsulta != null) {
            while (resultadoConsulta.moveToNext()) {
                val sexo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_SEXO))
                val nombre = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_NOMBRE))
                val siniiga = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_SINIIGA))
                val edad = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_EDAD))
                val peso_nacer = resultadoConsulta.getFloat(resultadoConsulta.getColumnIndex(COL_PESO_NACER))
                val peso_destete = resultadoConsulta.getFloat(resultadoConsulta.getColumnIndex(COL_PESO_DESTETE))
                val peso_12 = resultadoConsulta.getFloat(resultadoConsulta.getColumnIndex(COL_PESO_12))
                val potrero = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_POTRERO))
                val fecha_nac = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_FECHA_NAC))
                val ranchoObtenido = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_RANCHO))
                val becerro = Becerro(sexo, nombre, siniiga, edad, peso_nacer, peso_destete, peso_12, potrero, fecha_nac, ranchoObtenido)
                misBecerros.add(becerro)
            }
            resultadoConsulta.close()
        }
        db.close()
        return misBecerros
    }

    @SuppressLint("Range")
    fun seleccionarBecerrosPorPotrero(potrero: Int, rancho: String): List<Becerro> {
        val misBecerros = mutableListOf<Becerro>()
        val db = readableDatabase
        val resultadoConsulta: Cursor = db.query(NOMBRE_TABLA, null, "$COL_POTRERO = ? AND $COL_RANCHO = ?", arrayOf(potrero.toString(),rancho), null, null, null)
        if (resultadoConsulta != null) {
            while (resultadoConsulta.moveToNext()) {
                val sexo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_SEXO))
                val nombre = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_NOMBRE))
                val siniiga = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_SINIIGA))
                val edad = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_EDAD))
                val peso_nacer = resultadoConsulta.getFloat(resultadoConsulta.getColumnIndex(COL_PESO_NACER))
                val peso_destete = resultadoConsulta.getFloat(resultadoConsulta.getColumnIndex(COL_PESO_DESTETE))
                val peso_12 = resultadoConsulta.getFloat(resultadoConsulta.getColumnIndex(COL_PESO_12))
                val potrero = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_POTRERO))
                val fecha_nac = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_FECHA_NAC))
                val ranchoObtenido = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_RANCHO))
                val becerro = Becerro(sexo, nombre, siniiga, edad, peso_nacer, peso_destete, peso_12, potrero, fecha_nac, ranchoObtenido)
                misBecerros.add(becerro)
            }
            resultadoConsulta.close()
        }
        db.close()
        return misBecerros
    }

    fun actualizarBecerro(becerro: Becerro): Int {
        val db = writableDatabase
        val valoresUpdate = ContentValues().apply {
            put(COL_NOMBRE, becerro.nombre)
            put(COL_EDAD, becerro.edad)
            put(COL_PESO_NACER, becerro.pesoNacer)
            put(COL_PESO_DESTETE, becerro.pesoDestete)
            put(COL_PESO_12, becerro.pesoDoce)
            put(COL_POTRERO, becerro.potrero)
            put(COL_FECHA_NAC, becerro.fechaNacimiento)
            put(COL_RANCHO, becerro.rancho)
        }
        val filasAfectadas = db.update(NOMBRE_TABLA, valoresUpdate, "$COL_SINIIGA = ?", arrayOf(becerro.siiniga.toString()))
        db.close()
        return filasAfectadas
    }
}
