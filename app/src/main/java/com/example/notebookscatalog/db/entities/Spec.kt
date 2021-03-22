package com.example.notebookscatalog.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spec")
data class Spec(
        @PrimaryKey(autoGenerate = true)
        val specId: Int?,
        val screenSize: Float,
        val screenResolutionLow: Int,
        val screenResolutionHigh: Int,
        val ramSize: Int,
        var hddSize: Int
)
