package com.example.neobis_android_inventory_app.Presenter

import com.example.neobis_android_inventory_app.database.DataProduct

interface ProductContract {
    interface MainView {
        fun showProducts(products: List<DataProduct>)
        fun showError(message: String)
    }

    interface Presenter {
         fun getAllProducts()
          fun insertProduct(dataProduct: DataProduct)
          fun updateProduct(dataProduct: DataProduct)
          fun deleteProduct(dataProduct: DataProduct)
    }
    interface BottomSheetPresenter{
        fun archiveProduct(dataProduct: DataProduct)
        fun deleteProduct(dataProduct: DataProduct)
        fun restoreProduct(dataProduct: DataProduct)
    }
}