package com.example.nasapractica.services


import com.example.nasapractica.data.DatosNasa
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DataNasaService {

    @GET("apod")
    suspend fun mostrarAlAzar(
       @Query("count") num: String

    ): List<DatosNasa>

    @GET("&start_date={fecha}")
    suspend fun mostrarPorFechaInicio(@Path("fecha") identifier:String) : List<DatosNasa>
}