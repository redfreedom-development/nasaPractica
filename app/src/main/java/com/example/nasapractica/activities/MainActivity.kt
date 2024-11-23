package com.example.nasapractica.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nasapractica.adapters.AdapterMainActivity
import com.example.nasapractica.data.DatosNasa
import com.example.nasapractica.databinding.ActivityMainBinding
import com.example.nasapractica.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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


    }

    fun navigateToDetail(data: DatosNasa ){
        //ya lo haremos

    }

    private fun obtener_datos_nasa_retrofit(query: String) {

        val service = RetrofitProvider.getRetrofit()

        CoroutineScope(Dispatchers.IO).launch {
            try {

                val result = service.mostrarAlAzar(query)
                for (data in result) {
                    Log.d("API Response", "Title: ${data.title}, Image URL: ${data.url}")
                }

                CoroutineScope(Dispatchers.Main).launch{


                    adapter.updateItems(result)

                }
            } catch (e: Exception) {
                Log.e("API", e.stackTraceToString())
            }


        }


    }
}