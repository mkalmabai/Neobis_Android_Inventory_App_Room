package com.example.neobis_android_inventory_app.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neobis_android_inventory_app.MainFragmentDirections
import com.example.neobis_android_inventory_app.database.DataProduct
import com.example.neobis_android_inventory_app.databinding.ItemRecyclerviewBinding

interface ClickListener {
    fun onBottomSheetClick(position: Int, dataProduct: DataProduct)
    fun onRecyclerViewItemClick(dataProduct: DataProduct)
}
class RecyclerViewAdapter(
     val clickListener: ClickListener
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    var dataProduct = emptyList<DataProduct>()

    class ViewHolder(private val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root){
        val img = binding.itemImageview
        val name = binding.itemNameProduct
        val price = binding.itemPrice
        val manufacturer = binding.itemManufacturer
        val quantity =binding.itemQuantity
        val item = binding.itemCardView
        val itemMenu = binding.itemMenu
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
          clickListener.onRecyclerViewItemClick(dataProduct)
        }
        holder.itemMenu.setOnClickListener {
            clickListener.onBottomSheetClick(position,dataProduct)
        }
    }
    fun updateProduct(newList: List<DataProduct>){

        val diffUtil = DataProductDiffCallBack(dataProduct,newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
            dataProduct = newList

            diffResult.dispatchUpdatesTo(this)

    }
}
class DataProductDiffCallBack(
    private val oldItem: List<DataProduct>,
    private val newItem: List<DataProduct>
): DiffUtil.Callback(){


    override fun getOldListSize(): Int {
        return oldItem.size
    }

    override fun getNewListSize(): Int {
        return newItem.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItem[oldItemPosition].id== newItem[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldItem[oldItemPosition].id != newItem[newItemPosition].id -> false
            oldItem[oldItemPosition].name != newItem[newItemPosition].name -> false
            oldItem[oldItemPosition].imagePath != newItem[newItemPosition].imagePath -> false
            oldItem[oldItemPosition].manufacturer != newItem[newItemPosition].manufacturer -> false
            oldItem[oldItemPosition].archive != newItem[newItemPosition].archive -> false
            oldItem[oldItemPosition].price != newItem[newItemPosition].price -> false
            oldItem[oldItemPosition].quantity != newItem[newItemPosition].quantity -> false
        else -> true
        }


    }

}