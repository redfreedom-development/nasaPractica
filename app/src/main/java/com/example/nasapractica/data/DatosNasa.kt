package com.example.nasapractica.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class DatosNasa(
    var id: Int,
    val date: String,
    var explanation: String,
   // val hdurl: String,
   // val media_type: String,
   // val service_version: String,
    var title: String,
    var url: String,
){


    // Constructor secundario sin el 'id', útil cuando el 'id' es autoincremental
    constructor(date:String,explanation: String, title: String,url:String) : this(0, date,explanation, title,url) {
        // El 'id' podría ser 0 por defecto, luego se asignará automáticamente al insertar en la base de datos.
    }
    companion object {
        const val TABLE_NAME = "imagenDelDiaNasa"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE= "titulo"
        const val COLUMN_URL = "url"
        const val COLUMN_EXPLANATION="explanation"
        const val COLUMN_DATE="date"

    }

}

/*data class DatosNasa(
    val title: String,
    val image: Image
)

data class Image(
    val url: String
)*/


