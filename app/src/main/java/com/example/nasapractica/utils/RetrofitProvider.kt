package com.example.nasapractica.utils

import com.example.nasapractica.data.DatosNasa
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {

    companion object {
        fun getRetrofit() : DatosNasa {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/planetary/apod?api_key=emRrHnNrToierMGyGwwWNqqj085fSC66lDoV4Dt8")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(DatosNasa::class.java)
        }
    }
}