package com.example.nasapractica.data

import com.google.gson.annotations.SerializedName


data class DatosNasa(
    val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String,
)

/*data class DatosNasa(
    val title: String,
    val image: Image
)

data class Image(
    val url: String
)*/


