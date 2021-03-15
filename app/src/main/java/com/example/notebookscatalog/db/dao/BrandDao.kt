package com.example.notebookscatalog.db.dao

import androidx.room.*
import com.example.notebookscatalog.db.entities.Brand
import kotlinx.coroutines.flow.Flow

@Dao
interface BrandDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(brand: Brand)

    @Query("SELECT * FROM brand ORDER BY name")
    fun getAllBrands() : Flow<List<Brand>>

    @Transaction
    @Query("DELETE FROM brand")
    suspend fun deleteAll()
}