package com.example.notebookscatalog.interfaces

import com.example.notebookscatalog.data.DeviceItem

interface IFragmentCommunication {
    fun createDevice()
    fun updateDevice(index: Int)
    fun listDevices()
    fun onDeviceCreated(device: DeviceItem?)
}