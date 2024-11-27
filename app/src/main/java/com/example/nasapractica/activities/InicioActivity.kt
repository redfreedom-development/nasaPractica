package com.example.nasapractica.activities

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.nasapractica.R
import com.example.nasapractica.databinding.ActivityDetailBinding
import com.example.nasapractica.databinding.ActivityInicioBinding
import com.example.nasapractica.databinding.ActivityMainBinding


class InicioActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var binding: ActivityInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicia la música
        mediaPlayer = MediaPlayer.create(this, R.raw.startrek)
        mediaPlayer?.start()

        efecto_imagen()


        android.os.Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cierra el SplashActivity
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }, 100) // Tiempo en milisegundos (10 segundos)
    }


    @SuppressLint("Recycle")
    private fun efecto_imagen() {
        val layout = binding.root as ConstraintLayout  // Obtén el ConstraintLayout de fondo
        // Establecer la escala inicial de la imagen para hacerla pequeña
        binding.imgWelcome.scaleX = 0.1f
        binding.imgWelcome.scaleY = 0.1f

        // Crear la animación de escala
        val scaleX = ObjectAnimator.ofFloat(binding.imgWelcome, "scaleX", 0.1f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.imgWelcome, "scaleY", 0.1f, 1f)

        // Crear la animación de cambio de color de fondo (con ObjectAnimator)
        val colorAnimator = ObjectAnimator.ofArgb(
            layout,
            "backgroundColor",
            resources.getColor(android.R.color.white),  // Color de inicio (blanco)
            resources.getColor(android.R.color.black)   // Color final (negro)
        )
        colorAnimator.duration = 7000  // Duración de la animación (7 segundos)
        colorAnimator.interpolator = AccelerateDecelerateInterpolator()

        // Configurar la duración y el interpolador para que la animación sea suave
        scaleX.duration = 7000  // 7 segundos
        scaleY.duration = 7000  // 7 segundos
        scaleX.interpolator = AccelerateDecelerateInterpolator()
        scaleY.interpolator = AccelerateDecelerateInterpolator()

        // Iniciar las animaciones
        scaleX.start()
        scaleY.start()
        colorAnimator.start()

        // Puedes agregar un delay para que después de la animación inicie otra actividad si es necesario
        binding.imgWelcome.postDelayed({
            // Si deseas continuar con una nueva acción, como cambiar de actividad, puedes hacerlo aquí.
            // Por ejemplo, iniciar la siguiente actividad
            // startActivity(Intent(this, NextActivity::class.java))
        }, 7000)  // Después de 7 segundos (el tiempo de la animación)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Libera el MediaPlayer si la actividad se destruye
        mediaPlayer?.release()
        mediaPlayer = null
    }
}