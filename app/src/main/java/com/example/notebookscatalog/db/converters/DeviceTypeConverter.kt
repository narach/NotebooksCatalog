package com.example.notebookscatalog.db.converters

import androidx.room.TypeConverter
import com.example.notebookscatalog.db.enums.DeviceType

class DeviceTypeConverter {
    @TypeConverter
    fun toDeviceType(value: Int) = enumValues<DeviceType>()[value]

    @TypeConverter
    fun fromDeviceType(value: DeviceType) = value.ordinal
}