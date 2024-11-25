package com.example.nasapractica.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nasapractica.R
import com.example.nasapractica.adapters.AdapterMainActivity
import com.example.nasapractica.data.DatosNasa
import com.example.nasapractica.databinding.ActivityMainBinding
import com.example.nasapractica.utils.RetrofitProvider
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    //variables de entorno globales
    lateinit var binding: ActivityMainBinding
    var datosNasaList: List<DatosNasa> = emptyList()
    lateinit var adapter: AdapterMainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding para usar los objetos de mi pantalla
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //vamos a probar al iniciar la app que carga datos al azar LUEGO ESTO SE BORRARA
        obtener_datos_nasa_retrofit("4")

        // creamos el adapter con la funcionalidad del onClick
        adapter = AdapterMainActivity(datosNasaList) { position ->
            try {
                // Intenta acceder al elemento de la lista usando el position
                val data = datosNasaList[position]
                navigateToDetail(data)
            } catch (e: IndexOutOfBoundsException) {
                Log.e("RecyclerView", "Error al acceder al índice $position: ${e.message}")
            } catch (e: Exception) {
                Log.e("RecyclerView", "Error inesperado al hacer clic: ${e.message}")
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Listener del menu_azar
        binding.menuAzar.setOnClickListener {
            pulsa_menu_azar() // Llamabas a la función para manejar la acción
        }
        binding.menuDate.setOnClickListener{
            pulsa_menu_date()
        }


    }

    fun navigateToDetail(data: DatosNasa ){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.TITLE, data.title)
        print(data.title)
        intent.putExtra(DetailActivity.URL, data.url)
        print(data.url)
        intent.putExtra(DetailActivity.EXPLANATION, data.explanation)
        print(data.explanation)
        startActivity(intent)

    }

    private fun obtener_datos_nasa_retrofit(query: String) {

        val service = RetrofitProvider.getRetrofit()
        var result: List<DatosNasa> = emptyList()

        CoroutineScope(Dispatchers.IO).launch {
            try {



                if (query.toIntOrNull() != null) {
                 result = service.mostrarAlAzar(query)

                }
                else{
                     result = service.mostrarPorFechaInicio(query)
                }

                //leno la lista en la variable global pues despues la
                //usaremos para pasar a la pantalla de details y result
                //que tambien contiene la lista es una variable local
                datosNasaList=result



                CoroutineScope(Dispatchers.Main).launch{


                    adapter.updateItems(datosNasaList)

                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }


        }


    }
    private fun pulsa_menu_date() {
        CoroutineScope(Dispatchers.Main).launch {
            val fecha = mostrar_cuadro_dialogo_fecha()
            Log.d("FechaSeleccionada", "Fecha obtenida: $fecha")
            obtener_datos_nasa_retrofit(fecha)
        }
    }
    private fun pulsa_menu_azar() {
        val builder = AlertDialog.Builder(this@MainActivity)
        val editText = EditText(this@MainActivity)

        builder.setTitle(R.string.menu_azar)
        builder.setMessage(R.string.mensaje_dialog_azar)
        builder.setView(editText)

        builder.setPositiveButton(R.string.aceptar) { _, _ ->
            val resultado = editText.text.toString()
            if (resultado.toIntOrNull() != null) {
                obtener_datos_nasa_retrofit(resultado)
            } else {
                val toast = Toast.makeText(this, R.string.aviso_int, Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                toast.show()
            }
        }

        builder.setNegativeButton(R.string.cancelar, null)
        builder.show()
    }
    private suspend fun mostrar_cuadro_dialogo_fecha(): String {
        val deferred = CompletableDeferred<String>()
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this@MainActivity,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val formattedDate = "$selectedYear-${selectedMonth + 1}-${selectedDayOfMonth}"
                deferred.complete(formattedDate)
            },
            year,
            month,
            day
        )

        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.set(1995, Calendar.JUNE, 17)
        datePickerDialog.datePicker.minDate = minDateCalendar.timeInMillis

        datePickerDialog.show()
        return deferred.await()
    }
}