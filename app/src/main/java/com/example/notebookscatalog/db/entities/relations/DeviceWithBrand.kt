package com.example.notebookscatalog.db.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.notebookscatalog.db.entities.Brand
import com.example.notebookscatalog.db.entities.Device

data class DeviceWithBrand(
    @Embedded val device: Device,
    @Relation(
        parentColumn = "brandName",
        entityColumn = "name"
    )
    val brand: Brand
)
