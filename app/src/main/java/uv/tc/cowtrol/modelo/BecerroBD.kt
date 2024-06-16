package uv.tc.cowtrol.modelo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import uv.tc.cowtrol.poko.Becerro

class BecerroBD (contexto: Context) : SQLiteOpenHelper(contexto, NOMBRE_BD, null, VERSION_BD) {

    companion object{
        private const val NOMBRE_BD = "COWTROL"
        private const val NOMBRE_TABLA = "becerro"
        private const val COL_SEXO = "sexo"
        private const val COL_NOMBRE = "nombre"
        private const val COL_SINIIGA = "siniiga"
        private const val COL_EDAD = "edad"
        private const val COL_PESO_NACER = "peso_nacer"
        private const val COL_PES0_DESTETE = "peso_destete"
        private const val COL_PESO_12 = "peso_doce"
        private const val COL_POTRERO = "potrero"
        private const val COL_FECHA_NAC = "fecha_nacimiento"
        private const val VERSION_BD = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_BECERROS =
            ("CREATE TABLE IF NOT EXISTS $NOMBRE_TABLA ($COL_SEXO TEXT, $COL_NOMBRE TEXT, $COL_SINIIGA INTEGER PRIMARY KEY,$COL_EDAD INTEGER, $COL_PESO_NACER REAL, $COL_PES0_DESTETE REAL, $COL_PESO_12 REAL, $COL_POTRERO INTEGER, $COL_FECHA_NAC TEXT )")
        Log.d("BecerroBD", "Sentencia SQL: $CREATE_TABLE_BECERROS")
        db?.execSQL(CREATE_TABLE_BECERROS)
        Log.d("BecerroBD", "Tabla becerro creada")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun crearTabla(){
        val db = writableDatabase
        val CREATE_TABLE_BECERRO = ("CREATE TABLE IF NOT EXISTS $NOMBRE_TABLA ($COL_SEXO TEXT, $COL_NOMBRE TEXT, $COL_SINIIGA INTEGER PRIMARY KEY,$COL_EDAD INTEGER, $COL_PESO_NACER REAL, $COL_PES0_DESTETE REAL, $COL_PESO_12 REAL, $COL_POTRERO INTEGER, $COL_FECHA_NAC TEXT )")
        db!!.execSQL(CREATE_TABLE_BECERRO)
    }

    fun insertarBecerro(becerro : Becerro): Long{
        val db = writableDatabase
        val valoresInsert = ContentValues()
        valoresInsert.put(COL_SEXO, becerro.sexo)
        valoresInsert.put(COL_NOMBRE, becerro.nombre)
        valoresInsert.put(COL_SINIIGA, becerro.siiniga)
        valoresInsert.put(COL_EDAD,becerro.edad)
        valoresInsert.put(COL_PESO_NACER, becerro.pesoNacer)
        valoresInsert.put(COL_PES0_DESTETE, becerro.pesoDestete)
        valoresInsert.put(COL_PESO_12, becerro.pesoDoce)
        valoresInsert.put(COL_POTRERO, becerro.potrero)
        valoresInsert.put(COL_FECHA_NAC, becerro.fechaNacimiento)
        val filasAfectadas = db.insert(NOMBRE_TABLA, null, valoresInsert)
        db.close()
        return filasAfectadas
    }


}