package com.example.notebookscatalog.db.repos

import androidx.annotation.WorkerThread
import com.example.notebookscatalog.db.dao.DeviceDao
import com.example.notebookscatalog.db.entities.Device
import kotlinx.coroutines.flow.Flow

class DeviceRepository(private val deviceDao: DeviceDao) {

    val allDevices: Flow<List<Device>> = deviceDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(device: Device) {
        deviceDao.insert(device)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(device: Device) {
        deviceDao.update(device)
    }
}