package com.example.nasapractica.utils

import com.example.nasapractica.data.DatosNasa
import com.example.nasapractica.services.DataNasaService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {

    companion object {

        private const val API_KEY = "emRrHnNrToierMGyGwwWNqqj085fSC66lDoV4Dt8"

        fun getRetrofit(): DataNasaService {
            // Interceptor para añadir la API Key
            val interceptor = Interceptor { chain ->
                val original = chain.request()
                val originalUrl = original.url
                val url = originalUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            }

            // Cliente HTTP con el Interceptor
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            // Retrofit con cliente HTTP personalizado
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/planetary/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(DataNasaService::class.java)
        }
    }
}