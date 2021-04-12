package com.example.notebookscatalog.db.repos

import androidx.annotation.WorkerThread
import com.example.notebookscatalog.db.dao.BrandDao
import com.example.notebookscatalog.db.entities.Brand
import kotlinx.coroutines.flow.Flow

class BrandRepository(private val brandDao: BrandDao) {

    val allBrands: Flow<List<Brand>> = brandDao.getAllBrands()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(brand: Brand) {
        brandDao.insert(brand)
    }
}