package com.example.notebookscatalog.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brand")
data class Brand(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String
)