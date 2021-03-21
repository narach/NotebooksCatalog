package com.example.notebookscatalog.interfaces

import com.example.notebookscatalog.db.entities.Device

interface IFragmentCommunication {
    fun createDevice()
    fun updateDevice(deviceItem: Device)
    fun listDevices()
}