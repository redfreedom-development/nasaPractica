package com.example.nasapractica.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.nasapractica.data.DatosNasa

class DataBaseManager(context: Context) : SQLiteOpenHelper( context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object {
        const val DATABASE_NAME = "nasa_database.db"
        const val DATABASE_VERSION = 1



        private const val CREATE_TABLE = """
            CREATE TABLE ${DatosNasa.TABLE_NAME} (
                ${DatosNasa.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${DatosNasa.COLUMN_TITLE} TEXT,
                ${DatosNasa.COLUMN_URL} TEXT,  
                 ${DatosNasa.COLUMN_EXPLANATION} TEXT

                
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${DatosNasa.TABLE_NAME}")
        onCreate(db)
    }




}