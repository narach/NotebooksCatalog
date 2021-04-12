package com.example.notebookscatalog.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_screen")
data class Screen(
        @PrimaryKey(autoGenerate = true)
        val id: Int?,
        val size: String,
        val resolution: String
)
