package com.example.nasapractica.activities

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import android.view.View
import android.widget.Toast
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
        const val DATE = "DATE"

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
        val date = intent.getStringExtra(DATE)!!



        datosNasa=DatosNasa(id,date,explanation,titulo,url)



        Log.d("Lifecycle", "HAS LLAMADO AL ONCREATE");

        mostrar_datos_details(datosNasa)

        comprobar_si_existe_ya_en_database(datosNasa)

        binding.menuGrabar.setOnClickListener(){
            mostrar_cuadro_dialogo_grabar(datosNasa)
        }

       /* binding.menuDelete.setOnClickListener(){

            mostrar_cuadro_dialogo_delete(datosNasa)
        }*/

    }

    private fun comprobar_si_existe_ya_en_database(datosNasa: DatosNasa) {



        val respuesta=dao.findByDate(datosNasa.date)
        if (respuesta){
            binding.menuGrabar.isEnabled=false
            binding.menuGrabar.alpha=0.1f
            //PODEMOS AÑADIR UNA PAPELERA PARA QUE BORRE DESDE AQUI
            binding.menuBorrar.isEnabled=true
            binding.menuBorrar.alpha=1f

        }
        else{
            binding.menuGrabar.isEnabled=true
            binding.menuGrabar.alpha=1f
            binding.menuBorrar.isEnabled=false
            binding.menuBorrar.alpha=0.1f
        }

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

           val ok= dao.insert(datosNasa)
            if(ok!=-1L){
                Toast.makeText(this, R.string.insert_ok, Toast.LENGTH_SHORT).show()
                binding.menuGrabar.isEnabled=false
                binding.menuGrabar.alpha=0.5f

            }

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