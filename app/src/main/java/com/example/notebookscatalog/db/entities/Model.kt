package com.example.notebookscatalog.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "models")
data class Model(
        @PrimaryKey(autoGenerate = true)
        val modelId: Int?,
        val name: String
)
