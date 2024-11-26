package com.example.nasapractica.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.nasapractica.data.DatosNasa

class NasaDAO(val context: Context) {

    private lateinit var db: SQLiteDatabase

    private fun open() {
        db = DataBaseManager(context).writableDatabase
    }

    private fun close() {
        db.close()
    }

    fun insert(datosNasa: DatosNasa) : Long {

        var id = -1L //inicializo así porque es un Long y el valor si falla el insert será de -1
        open()

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(DatosNasa.COLUMN_TITLE, datosNasa.title)
            put(DatosNasa.COLUMN_URL, datosNasa.url)
            put(DatosNasa.COLUMN_EXPLANATION, datosNasa.explanation)
            put(DatosNasa.COLUMN_DATE,datosNasa.date)

        }

        try {
            // Insert the new row, returning the primary key value of the new row
              id = db.insert(DatosNasa.TABLE_NAME, null, values)

        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }

        return id
    }

    fun findAll() : MutableList<DatosNasa> {
        open()

        val list: MutableList<DatosNasa> = mutableListOf()

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(DatosNasa.COLUMN_ID, DatosNasa.COLUMN_TITLE, DatosNasa.COLUMN_URL,DatosNasa.COLUMN_EXPLANATION,DatosNasa.COLUMN_DATE)

        try {
            val cursor = db.query(
                DatosNasa.TABLE_NAME,                    // The table to query
                projection,                         // The array of columns to return (pass null to get all)
                null,                       // The columns for the WHERE clause
                null,                   // The values for the WHERE clause
                null,                       // don't group the rows
                null,                         // don't filter by row groups
                null                         // The sort order
            )

            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatosNasa.COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DatosNasa.COLUMN_TITLE))
                val url = cursor.getString(cursor.getColumnIndexOrThrow(DatosNasa.COLUMN_URL))
                val explanation = cursor.getString(cursor.getColumnIndexOrThrow(DatosNasa.COLUMN_EXPLANATION))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(DatosNasa.COLUMN_DATE))

                val datosNasa = DatosNasa(id, date, explanation,title,url)
                list.add(datosNasa)
            }
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
        return list
    }

    fun deleteById(datosNasa: DatosNasa): Int {

        var deletedRows=0
        open()


        try {
            // Delete the existing row, returning the number of affected rows
            deletedRows = db.delete(
                DatosNasa.TABLE_NAME,
                "${DatosNasa.COLUMN_ID} = ?",
                arrayOf(datosNasa.id.toString()) // Asegúrate de convertir los valores a String
            )



        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()


        }
        return deletedRows

    }

    fun findByDate(date: String) : Boolean {
        open()

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(DatosNasa.COLUMN_DATE)
        var respuesta = true


        try {
            val cursor = db.query(
                DatosNasa.TABLE_NAME,                    // The table to query
                projection,                         // The array of columns to return (pass null to get all)
                "${DatosNasa.COLUMN_DATE} = $date",  // The columns for the WHERE clause
                null,                   // The values for the WHERE clause
                null,                       // don't group the rows
                null,                         // don't filter by row groups
                null                         // The sort order
            )

            if (cursor.moveToNext()) {
              //  val id = cursor.getLong(cursor.getColumnIndexOrThrow(DatosNasa.COLUMN_ID)).toInt()
                val fech = cursor.getString(cursor.getColumnIndexOrThrow(DatosNasa.COLUMN_DATE))
                //val explanation = cursor.getString(cursor.getColumnIndexOrThrow(DatosNasa.COLUMN_EXPLANATION))
                //val title = cursor.getString(cursor.getColumnIndexOrThrow(DatosNasa.COLUMN_TITLE))
                //val url = cursor.getString(cursor.getColumnIndexOrThrow(DatosNasa.COLUMN_URL))

                respuesta = false

            }


        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
       return respuesta

    }


}