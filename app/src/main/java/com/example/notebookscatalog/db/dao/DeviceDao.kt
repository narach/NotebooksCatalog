package com.example.notebookscatalog.db.dao

import androidx.room.*
import com.example.notebookscatalog.db.entities.Device
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(device: Device)

    @Update
    suspend fun update(vararg devices: Device)

    @Query("SELECT * FROM device ORDER BY model")
    fun getAll() : Flow<List<Device>>

    @Transaction
    @Query("DELETE FROM device")
    suspend fun deleteAll()

    @Transaction
    @Query("DELETE FROM device WHERE id = :deviceId")
    suspend fun delete(deviceId: Int)
}