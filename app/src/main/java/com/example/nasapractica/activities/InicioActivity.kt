package com.example.nasapractica.activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.nasapractica.R


class InicioActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)

        // Inicia la música
        mediaPlayer = MediaPlayer.create(this, R.raw.startrek)
        mediaPlayer?.start()


        android.os.Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cierra el SplashActivity
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }, 10000) // Tiempo en milisegundos (10 segundos)
    }
    override fun onDestroy() {
        super.onDestroy()
        // Libera el MediaPlayer si la actividad se destruye
        mediaPlayer?.release()
        mediaPlayer = null
    }
}