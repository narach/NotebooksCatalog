package com.example.notebookscatalog.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notebookscatalog.db.enums.DeviceType

@Entity(tableName = "device")
data class Device(
    @ColumnInfo(index = true)
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "brand_name")
    val brandName: String,
    val model: String,
    @ColumnInfo(name = "img_uri")
    val imgUri: String?,
    val screen: String,
    val hardware: String,
    @ColumnInfo(name = "device_type")
    val deviceType: DeviceType
)
