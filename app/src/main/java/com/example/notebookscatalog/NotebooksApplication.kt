package com.example.notebookscatalog

import android.app.Application
import com.example.notebookscatalog.db.DevicesDatabase
import com.example.notebookscatalog.db.repos.BrandRepository
import com.example.notebookscatalog.db.repos.DeviceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotebooksApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { DevicesDatabase.getDatabase(this, applicationScope) }
    val brandRepository by lazy { BrandRepository(database.brandDao()) }
    val deviceRepository by lazy { DeviceRepository(database.deviceDao()) }
}