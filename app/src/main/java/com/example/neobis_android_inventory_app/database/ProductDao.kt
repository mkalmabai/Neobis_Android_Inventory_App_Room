package com.example.neobis_android_inventory_app.database

import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT*FROM productTable WHERE archive = 0 ")
     fun getAll():List<DataProduct>
    @Query("SELECT*FROM productTable WHERE archive = 1 ")
    fun getAllArchived():List<DataProduct>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(dataProduct: DataProduct)
    @Update
    fun update(dataProduct: DataProduct)
    @Delete
    fun delete(dataProduct: DataProduct)


}