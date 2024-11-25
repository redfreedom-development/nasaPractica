package com.example.nasapractica.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nasapractica.R
import com.example.nasapractica.data.DatosNasa
import com.example.nasapractica.database.NasaDAO
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
    lateinit var dao:NasaDAO
    private lateinit var datosNasa: DatosNasa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //lo primero recoger los datos pasados del main activity
        val titulo = intent.getStringExtra(TITLE)
        val url = intent.getStringExtra(URL)
        val explanation = intent.getStringExtra(EXPLANATION)

        datosNasa(explanation!!,titulo!!,url!!)

        //llenamos el objeto datosNasa con los datos recibidos al cambiar
        //de pantalla por si
        /* datosNasa.url=url!!
        datosNasa.id=0
        datosNasa.title=title!!
        datosNasa.explanation=explanation!!*/



        mostrar_datos_details(titulo,url,explanation)

        binding.menuGrabar.setOnClickListener(){
            mostrar_cuadro_dialogo_grabar(datosNasa)
        }

    }

    private fun mostrar_cuadro_dialogo_grabar(datosNasa: DatosNasa) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.confirmation)
        builder.setMessage(R.string.mensaje_grabar)


        builder.setPositiveButton(R.string.aceptar) { dialog, which ->
            // Acción al aceptar
            dialog.dismiss()

            dao.insert(datosNasa)

        }
        builder.setNegativeButton(R.string.cancelar) { dialog, which ->
            // Acción al cancelar
            dialog.dismiss()
        }

        // Deshabilitar la cancelación al tocar fuera del diálogo
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

    private fun mostrar_datos_details(title:String?, url: String?, explanation: String?) {

        binding.txttitulo.text=title
        Picasso.get().load(url).into(binding.imgDetail)
        binding.txtexplanation.text=explanation




    }
}