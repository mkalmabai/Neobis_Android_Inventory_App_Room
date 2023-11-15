package com.example.neobis_android_inventory_app.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neobis_android_inventory_app.MainFragmentDirections
import com.example.neobis_android_inventory_app.database.DataProduct
import com.example.neobis_android_inventory_app.databinding.ItemRecyclerviewBinding



class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    var dataProduct = emptyList<DataProduct>()
    class ViewHolder(private val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root){

        val img = binding.itemImageview
        val name = binding.itemNameProduct
        val price = binding.itemPrice
        val manufacturer = binding.itemManufacturer
        val quantity =binding.itemQuantity
        val item = binding.itemCardView

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
            Glide.with(img).load(dataProduct.imagePath).into(img)
                name.text = dataProduct.name
                price.text = dataProduct.price.toString()
                manufacturer.text = dataProduct.manufacturer
                quantity.text = dataProduct.quantity.toString()

        }
        holder.item.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToUpdateFragment(dataProduct)
            holder.item.findNavController().navigate(action)
        }
    }

}