package com.example.neobis_android_inventory_app.database

class ProductRepository(private val productDao: ProductDao) {
      fun getAllProducts(): List<DataProduct>{
        return productDao.getAll()
    }
       fun insertProduct(dataProduct: DataProduct){
        return productDao.insert(dataProduct)
    }
       fun updateProduct(dataProduct: DataProduct) {
        return productDao.update(dataProduct)
    }
       fun deleteProduct(dataProduct: DataProduct) {
        return productDao.delete(dataProduct)
    }
}