package com.example.notebookscatalog.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notebookscatalog.db.enums.DeviceType

@Entity(tableName = "device")
data class Device(
    @ColumnInfo(index = true)
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "brand_name")
    var brandName: String,
    var model: String,
    @ColumnInfo(name = "img_uri")
    var imgUri: String?,
    var screen: String,
    var hardware: String,
    @ColumnInfo(name = "device_type")
    var deviceType: DeviceType
)
