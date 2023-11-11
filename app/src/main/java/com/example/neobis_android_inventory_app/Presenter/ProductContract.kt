package com.example.neobis_android_inventory_app.Presenter

import com.example.neobis_android_inventory_app.database.DataProduct

interface ProductContract {
    interface View {
        fun showProducts(products: List<DataProduct>)
        fun showInsertSuccessMessage()
        fun showUpdateSuccessMessage()
        fun showDeleteSuccessMessage()
    }

    interface Presenter {
         fun getAllProducts()
          fun insertProduct(dataProduct: DataProduct)
          fun updateProduct(dataProduct: DataProduct)
          fun deleteProduct(dataProduct: DataProduct)
    }
}

//
//// DataContract.kt
//interface DataContract {
//
//    interface Model {
//        suspend fun getAllItems(): List<Item>
//        suspend fun insertItem(item: Item)
//    }
//
//    interface Presenter {
//        fun fetchData()
//        fun addNewItem(item: Item)
//    }
//
//    interface View {
//        fun showData(items: List<Item>)
//        fun showMessage(message: String)
//    }
//}