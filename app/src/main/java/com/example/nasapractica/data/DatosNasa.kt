package com.example.nasapractica.data

import com.google.gson.annotations.SerializedName



data class DatosNasa(

    @SerializedName ("title") val title: String,
    @SerializedName ("date") val date: String,
    @SerializedName ("explanation") val explanation: String,
    @SerializedName ("url") val url: String)

{}



