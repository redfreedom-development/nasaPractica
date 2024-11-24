package com.example.nasapractica.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nasapractica.R
import com.example.nasapractica.databinding.ActivityDetailBinding
import com.example.nasapractica.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        //creamos constante donde irá el id pasado del main a aqui y lo inicializamos
        const val TITLE = ""
        const val URL = ""
        const val EXPLANATION = ""
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}