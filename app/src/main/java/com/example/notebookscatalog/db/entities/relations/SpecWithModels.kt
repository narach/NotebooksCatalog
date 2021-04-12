package com.example.notebookscatalog.db.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.notebookscatalog.db.entities.Model
import com.example.notebookscatalog.db.entities.Spec

data class SpecWithModels (
    @Embedded
    val spec: Spec,
    @Relation(
            parentColumn = "specId",
            entityColumn = "modelId",
            associateBy = Junction(ModelSpecCrossRef::class)
    )
    val models: List<Model>
)