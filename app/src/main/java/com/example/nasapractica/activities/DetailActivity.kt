package com.example.nasapractica.activities

import android.content.Intent
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
        const val ID = "ID"
        const val TITLE = "TITLE"
        const val URL = "URL"
        const val EXPLANATION = "EXPLANATION"

    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var datosNasa: DatosNasa
    private var dao = NasaDAO(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //lo primero recoger los datos pasados del main activity
        val id = intent.getIntExtra(ID,0)
        val titulo = intent.getStringExtra(TITLE)!!
        val url = intent.getStringExtra(URL)!!
        val explanation = intent.getStringExtra(EXPLANATION)!!



        datosNasa=DatosNasa(id,explanation,titulo,url)





        mostrar_datos_details(datosNasa)

        binding.menuGrabar.setOnClickListener(){
            mostrar_cuadro_dialogo_grabar(datosNasa)
        }

       /* binding.menuDelete.setOnClickListener(){

            mostrar_cuadro_dialogo_delete(datosNasa)
        }*/

    }


    /*########COMENTO ESTA FUNCION PORQUE HE QUITADO EL BOTON DE BORRAR DE DETAILS#######

    private fun mostrar_cuadro_dialogo_delete(datosNasa: DatosNasa) {

        val builder = AlertDialog.Builder(this)


        builder.setTitle(R.string.confirmation)
        builder.setMessage(R.string.mensaje_delete)


        builder.setPositiveButton(R.string.aceptar) { dialog, which ->
            // Acción al aceptar
            dialog.dismiss()

            dao.deleteById(datosNasa)
            val intent = Intent(this, MainActivity::class.java)
            finish()
            //startActivity(intent)

        }
        builder.setNegativeButton(R.string.cancelar) { dialog, which ->
            // Acción al cancelar
            dialog.dismiss()
        }

        // Deshabilitar la cancelación al tocar fuera del diálogo
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()


    }*/

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

    private fun mostrar_datos_details(datosNasa: DatosNasa) {

        binding.txttitulo.text=datosNasa.title
        Picasso.get().load(datosNasa.url).into(binding.imgDetail)
        binding.txtexplanation.text=datosNasa.explanation




    }
}