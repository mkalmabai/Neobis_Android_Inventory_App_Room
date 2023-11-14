package com.example.neobis_android_inventory_app.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import com.example.neobis_android_inventory_app.database.DataProduct
import com.example.neobis_android_inventory_app.databinding.ItemRecyclerviewBinding


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    var dataProduct = emptyList<DataProduct>()
    class ViewHolder(private val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root){
        val text1 = binding.itemNameProduct
        val text2 = binding.itemManufacturer

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = dataProduct.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataProduct = dataProduct[position]
        holder.apply {
            text1.text = dataProduct.name
            text2.text = dataProduct.manufacturer
        }
    }
}