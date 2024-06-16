package uv.tc.cowtrol.modelo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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
        private const val COL_CORREO_USUARIO = "correo"
        private const val VERSION_BD = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_BECERROS =
            ("CREATE TABLE IF NOT EXISTS $NOMBRE_TABLA ($COL_SEXO TEXT, $COL_NOMBRE TEXT, $COL_SINIIGA INTEGER PRIMARY KEY,$COL_EDAD INTEGER, $COL_PESO_NACER REAL, $COL_PES0_DESTETE REAL, $COL_PESO_12 REAL, $COL_POTRERO INTEGER, $COL_FECHA_NAC TEXT, $COL_CORREO_USUARIO TEXT )")
        Log.d("BecerroBD", "Sentencia SQL: $CREATE_TABLE_BECERROS")
        db?.execSQL(CREATE_TABLE_BECERROS)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun crearTabla(){
        val db = writableDatabase
        val CREATE_TABLE_BECERRO = ("CREATE TABLE IF NOT EXISTS $NOMBRE_TABLA ($COL_SEXO TEXT, $COL_NOMBRE TEXT, $COL_SINIIGA INTEGER PRIMARY KEY,$COL_EDAD INTEGER, $COL_PESO_NACER REAL, $COL_PES0_DESTETE REAL, $COL_PESO_12 REAL, $COL_POTRERO INTEGER, $COL_FECHA_NAC TEXT, $COL_CORREO_USUARIO TEXT)")
        db!!.execSQL(CREATE_TABLE_BECERRO)
    }

    fun dropTable() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $NOMBRE_TABLA")
        db.close()
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
        valoresInsert.put(COL_CORREO_USUARIO, becerro.correoUsuario)
        val filasAfectadas = db.insert(NOMBRE_TABLA, null, valoresInsert)
        db.close()
        return filasAfectadas
    }

    @SuppressLint("Range")
    fun seleccionarBecerrosPorCorreo(correo : String) : List<Becerro>{
        val misBecerros = mutableListOf<Becerro>()
        val db = readableDatabase
        val resultadoConsulta : Cursor = db.query(NOMBRE_TABLA, null, "$COL_CORREO_USUARIO = ?", arrayOf(correo), null, null, null)
        if(resultadoConsulta != null){
            while (resultadoConsulta.moveToNext()){
                val sexo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_SEXO))
                val nombre = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_NOMBRE))
                val siniiga = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_SINIIGA))
                val edad = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_EDAD))
                val peso_nacer = resultadoConsulta.getFloat(resultadoConsulta.getColumnIndex(
                    COL_PESO_NACER))
                val peso_destete = resultadoConsulta.getFloat(resultadoConsulta.getColumnIndex(
                    COL_PES0_DESTETE))
                val peso_12 = resultadoConsulta.getFloat(resultadoConsulta.getColumnIndex(
                    COL_PESO_12))
                val potrero = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_POTRERO))
                val fecha_nac = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_FECHA_NAC))
                val correo_usuario = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_CORREO_USUARIO))
                val becerro = Becerro(sexo, nombre, siniiga, edad, peso_nacer, peso_destete, peso_12, potrero, fecha_nac, correo_usuario)
                misBecerros.add(becerro)
            }
            resultadoConsulta.close()
        }
        db.close()
        return  misBecerros
    }
}