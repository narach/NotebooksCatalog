package com.example.notebookscatalog.db.entities.relations

import androidx.room.Entity

@Entity(primaryKeys = ["modelId", "specId"], tableName = "model_spec_joint")
data class ModelSpecCrossRef(
    val modelId: Int,
    val specId: Int
)
