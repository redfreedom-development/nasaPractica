package com.example.nasapractica.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nasapractica.data.DatosNasa
import com.example.nasapractica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //variables de entorno globales
    lateinit var binding: ActivityMainBinding
    var datosNasa: MutableList<DatosNasa> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding para usar los objetos de mi pantalla
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}