package com.example.neobis_android_inventory_app.Presenter

import com.example.neobis_android_inventory_app.database.DataProduct

interface ProductContract {
    interface View {
        fun showProducts(products: List<DataProduct>)
        fun showError(message: String)
    }

    interface Presenter {
         fun getAllProducts()
          fun insertProduct(dataProduct: DataProduct)
          fun updateProduct(dataProduct: DataProduct)
          fun deleteProduct(dataProduct: DataProduct)
    }
}