package com.example.nasapractica.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nasapractica.data.DatosNasa
import com.example.nasapractica.databinding.ItemDatoNasaBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.NonDisposableHandle.parent

class AdapterMainActivity(var itemList: MutableList<DatosNasa>, val onItemClick: (Int) -> Unit):
    RecyclerView.Adapter<AdapterMainActivity.ViewHolder>() {

    class ViewHolder(val binding: ItemDatoNasaBinding) : RecyclerView.ViewHolder(binding.root) {
      //aqui diremos donde va a pintar
      fun render(datosNasa: DatosNasa) {

          Picasso.get().load(datosNasa.url).into(binding.imgNasa)
          binding.txttitulo.text = datosNasa.title

      }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =ItemDatoNasaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val datosNasa = itemList[position]
        holder.render(datosNasa)
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    fun updateItems(items: MutableList<DatosNasa>) {
        itemList = items
        notifyDataSetChanged()
    }


}