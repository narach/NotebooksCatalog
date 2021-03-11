package com.example.notebookscatalog.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notebookscatalog.data.DeviceItem

class DeviceListviewModel : ViewModel() {
    val devicesListLiveData = MutableLiveData<MutableList<DeviceItem>>(mutableListOf())

    var selectedIndex = MutableLiveData<Int>(0)

    fun selectItem(index: Int) {
        selectedIndex.value = index
    }

    fun loadDevices(devices: MutableList<DeviceItem>) {
        devicesListLiveData.value = devices
    }

    fun addDevice(device: DeviceItem) {
        devicesListLiveData.value!!.add(device)
    }

    fun getDeviceAtPosition(position: Int) : DeviceItem {
        return devicesListLiveData.value!![position]
    }

    fun updateDeviceAtPosition(updatedItem: DeviceItem, position: Int) {
        devicesListLiveData.value!!.set(position, updatedItem)
    }
}