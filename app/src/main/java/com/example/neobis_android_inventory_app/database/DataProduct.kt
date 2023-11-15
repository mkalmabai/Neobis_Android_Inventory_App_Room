package com.example.neobis_android_inventory_app.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "productTable")
@Parcelize
data class DataProduct(
    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
    val id: Int? = null,
    val imagePath: String,
    val name: String,
    val price: Int,
    val manufacturer: String,
    val quantity: Int
):Parcelable
