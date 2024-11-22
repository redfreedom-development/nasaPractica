package com.example.nasapractica.services

import com.example.nasapractica.data.DatosNasa
import retrofit2.http.GET
import retrofit2.http.Path

interface DataNasaService {

    @GET("&count={num}")
    suspend fun mostrarAlAzar(@Path("num") query:String) : DatosNasa

    @GET("&start_date={fecha}")
    suspend fun mostrarPorFechaInicio(@Path("fecha") identifier:String) : DatosNasa
}