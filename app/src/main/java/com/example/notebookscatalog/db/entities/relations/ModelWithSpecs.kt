package com.example.notebookscatalog.db.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.notebookscatalog.db.entities.Model
import com.example.notebookscatalog.db.entities.Spec

data class ModelWithSpecs(
        @Embedded val model: Model,
        @Relation(
                parentColumn = "modelId",
                entityColumn = "specId",
                associateBy = Junction(ModelSpecCrossRef::class)
        )
        val specs: List<Spec>
)