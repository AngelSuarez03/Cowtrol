package uv.tc.cowtrol.modelo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uv.tc.cowtrol.poko.Potrero

class PotreroBD (contexto: Context): SQLiteOpenHelper(contexto, NOMBRE_BD, null, VERSION_BD) {

    companion object{
        private const val NOMBRE_BD = "COWTROL"
        private const val NOMBRE_TABLA = "potrero"
        private const val COL_NUMERO_POTRERO = "numero_potrero"
        private const val COL_NOMBRE = "nombre"
        private const val COL_CAPACIDAD = "capacidad"
        private const val COL_UBICACION = "ubicacion"
        private const val COL_ANCHO = "ancho"
        private const val COL_LARGO = "largo"
        private const val COL_ALTO = "alto"
        private const val VERSION_BD = 1

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_POTRERO = ("CREATE TABLE ${NOMBRE_TABLA} (${COL_NUMERO_POTRERO} INT PRIMARY KEY, ${COL_NOMBRE} TEXT, ${COL_CAPACIDAD} INT, ${COL_UBICACION} TEXT, ${COL_ANCHO} REAL, ${COL_LARGO} REAL, ${COL_ALTO} )")
        db!!.execSQL(CREATE_TABLE_POTRERO);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun crearTablaPotrero(){
        val db = writableDatabase
        val CREATE_TABLE_POTRERO = ("CREATE TABLE ${NOMBRE_TABLA} (${COL_NUMERO_POTRERO} INT PRIMARY KEY, ${COL_NOMBRE} TEXT, ${COL_CAPACIDAD} INT, ${COL_UBICACION} TEXT, ${COL_ANCHO} REAL, ${COL_LARGO} REAL, ${COL_ALTO} )")
        db!!.execSQL(CREATE_TABLE_POTRERO);
    }

    fun crearPotrero(potrero: Potrero): Long {
        val db = writableDatabase
        val valoresInsert = ContentValues().apply {
            put(COL_NUMERO_POTRERO, potrero.numeroPotrero)
            put(COL_NOMBRE, potrero.nombre)
            put(COL_CAPACIDAD, potrero.capacidad)
            put(COL_UBICACION, potrero.ubicacion)
            put(COL_ANCHO, potrero.ancho)
            put(COL_LARGO, potrero.largo)
            put(COL_ALTO, potrero.alto)
        }
        val filasAfectadas = db.insert(NOMBRE_TABLA, null, valoresInsert)
        db.close()
        return filasAfectadas
    }

    @SuppressLint("Range")
    fun retornarPotrerosRegistrados(): List<Potrero> {
        val potreros = mutableListOf<Potrero>()
        val db = readableDatabase
        val resultadoConsulta: Cursor? = db.query(NOMBRE_TABLA, null, null,null,null,null,null)
        if(resultadoConsulta != null){
            while (resultadoConsulta.moveToNext()){
                val numeroPotrero = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_NUMERO_POTRERO))
                val nombrePotrero = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_NOMBRE))
                val capacidadPotrero = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(
                    COL_CAPACIDAD))
                val ubicacionPotrero = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(
                    COL_UBICACION))
                val anchoPotrero = resultadoConsulta.getDouble(resultadoConsulta.getColumnIndex(
                    COL_ANCHO))
                val largoPotrero = resultadoConsulta.getDouble(resultadoConsulta.getColumnIndex(
                    COL_LARGO))
                val altoPotrero = resultadoConsulta.getDouble(resultadoConsulta.getColumnIndex(COL_ALTO))
                val potrero = Potrero(numeroPotrero, nombrePotrero, capacidadPotrero, ubicacionPotrero, anchoPotrero, largoPotrero, altoPotrero)
                potreros.add(potrero)
            }
            resultadoConsulta.close()
        }
        db.close()
        return potreros
    }

}