package com.example.neobis_android_inventory_app.Presenter

import android.content.Context
import com.example.neobis_android_inventory_app.database.DataProduct
import com.example.neobis_android_inventory_app.database.ProductDatabase
import com.example.neobis_android_inventory_app.database.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArchivePresenter(private val context: Context):ProductContract.ArchivePresenter {
    private var view:ProductContract.MainView? = null
    private val productRepository: ProductRepository
    init {
        val productDao = ProductDatabase.getDatabase(context)?.productDao()
        productRepository = productDao?.let { ProductRepository(it) }!!
    }
    override fun getAllProducts() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val products = withContext(Dispatchers.IO) {
                    productRepository.getAllArchive()
                }
                view?.showProducts(products)
            } catch (e: Exception) {
                view?.showError(e.message ?: "Unknown error occurred")
            }
        }
    }

    override fun insertProduct(dataProduct: DataProduct) {
        TODO("Not yet implemented")
    }

    override fun updateProduct(dataProduct: DataProduct) {
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.updateProduct(dataProduct)
        }
    }



    override fun deleteProduct(dataProduct: DataProduct) {
        CoroutineScope(Dispatchers.IO).launch {
            productRepository.deleteProduct(dataProduct)
        }
    }
    override fun searchProduct(nameProduct: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val allProducts = productRepository.getAllArchive()
            val filteredProducts = allProducts.filter { it.name.contains(nameProduct, ignoreCase = true) }

            withContext(Dispatchers.Main) {
                view?.showProducts(filteredProducts)
            }
        }
    }
    override fun restoreProduct(dataProduct: DataProduct) {
            dataProduct.archive = false
            CoroutineScope(Dispatchers.IO).launch {
                productRepository.updateProduct(dataProduct)

        }
    }

    fun attachView(view: ProductContract.MainView) {
        this.view = view
    }
}