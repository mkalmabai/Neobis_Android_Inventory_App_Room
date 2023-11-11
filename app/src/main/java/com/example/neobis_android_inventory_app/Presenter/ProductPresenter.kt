package com.example.neobis_android_inventory_app.Presenter

import android.content.Context
import com.example.neobis_android_inventory_app.database.DataProduct
import com.example.neobis_android_inventory_app.database.ProductDatabase
import com.example.neobis_android_inventory_app.database.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductPresenter(private val context: Context): ProductContract.Presenter {
    private var view:ProductContract.View? = null
    private val productRepository: ProductRepository
    init {
        val productDao = ProductDatabase.getDatabase(context)?.productDao()
        productRepository = productDao?.let { ProductRepository(it) }!!
    }

     override fun getAllProducts() {
//        val products = productRepository.getAllProducts()
//        view?.showProducts(products)
    }

     override fun insertProduct(dataProduct: DataProduct) {
         CoroutineScope(Dispatchers.IO).launch {
             productRepository.insertProduct(dataProduct)
         }
    }

    override  fun updateProduct(dataProduct: DataProduct) {
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.updateProduct(dataProduct)
        }

    }

    override fun deleteProduct(dataProduct: DataProduct) {
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.deleteProduct(dataProduct)
        }

    }

}