package com.example.nasapractica.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nasapractica.R
import com.example.nasapractica.databinding.ActivityDetailBinding
import com.example.nasapractica.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    companion object {
        //creamos constante donde irá el id pasado del main a aqui y lo inicializamos
        const val TITLE = "TITLE"
        const val URL = "URL"
        const val EXPLANATION = "EXPLANATION"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //lo primero recoger los datos pasados del main activity
        val title = intent.getStringExtra(TITLE)
        val url = intent.getStringExtra(URL)
        val explanation = intent.getStringExtra(EXPLANATION)


        mostrar_datos_details(title,url,explanation)

    }

    private fun mostrar_datos_details(title:String?, url: String?, explanation: String?) {

        binding.txttitulo.text=title
        Picasso.get().load(url).into(binding.imgDetail)
        binding.txtexplanation.text=explanation




    }
}