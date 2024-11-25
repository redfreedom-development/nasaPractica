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

    fun insert(datosNasa: DatosNasa) {
        open()

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(DatosNasa.COLUMN_TITLE, datosNasa.title)
            put(DatosNasa.COLUMN_URL, datosNasa.url)
            put(DatosNasa.COLUMN_EXPLANATION, datosNasa.explanation)

        }

        try {
            // Insert the new row, returning the primary key value of the new row
            val id = db.insert(DatosNasa.TABLE_NAME, null, values)
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
    }

    fun findAll() : MutableList<DatosNasa> {
        open()

        val list: MutableList<DatosNasa> = mutableListOf()

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(DatosNasa.COLUMN_ID, DatosNasa.COLUMN_TITLE, DatosNasa.COLUMN_URL,DatosNasa.COLUMN_EXPLANATION)

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

                val datosNasa = DatosNasa(id, title, url,explanation)
                list.add(datosNasa)
            }
        } catch (e: Exception) {
            Log.e("DB", e.stackTraceToString())
        } finally {
            close()
        }
        return list
    }


}