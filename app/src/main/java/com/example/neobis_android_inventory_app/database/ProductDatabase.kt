package com.example.neobis_android_inventory_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataProduct ::class], version = 1, exportSchema = false)
abstract class ProductDatabase: RoomDatabase() {
        abstract fun productDao():ProductDao
        companion object{
            @Volatile
            private var INSTANCE: ProductDatabase? =null
            fun getDatabase (context: Context):ProductDatabase{
                val tempInstance = INSTANCE
                if(tempInstance != null){
                    return tempInstance
                }
                synchronized(ProductDatabase::class){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        "productDatabase"
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            }
        }

}