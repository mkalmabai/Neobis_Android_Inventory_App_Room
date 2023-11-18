package com.example.neobis_android_inventory_app.Presenter

import android.content.Context
import com.example.neobis_android_inventory_app.database.DataProduct
import com.example.neobis_android_inventory_app.database.ProductDatabase
import com.example.neobis_android_inventory_app.database.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BottomSheetPresenter( private val context: Context):ProductContract.BottomSheetPresenter {
    private var view:ProductContract.MainView? =null
    private val productRepository: ProductRepository
    init {
        val productDao = ProductDatabase.getDatabase(context)?.productDao()
        productRepository = productDao?.let { ProductRepository(it) }!!
    }
    override fun archiveProduct(dataProduct: DataProduct) {
       CoroutineScope(Dispatchers.IO).launch {
           productRepository.updateProduct(dataProduct)
       }
        dataProduct.archive = true
    }

    override fun deleteProduct(dataProduct: DataProduct) {
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.deleteProduct(dataProduct)
        }
    }

    override fun restoreProduct(dataProduct: DataProduct) {
        CoroutineScope(Dispatchers.IO).launch{
            productRepository.updateProduct(dataProduct)
        }
        dataProduct.archive = false
    }


}