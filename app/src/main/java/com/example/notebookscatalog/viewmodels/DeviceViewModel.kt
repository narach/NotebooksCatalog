package com.example.notebookscatalog.viewmodels

import androidx.lifecycle.*
import com.example.notebookscatalog.db.entities.Device
import com.example.notebookscatalog.db.repos.DeviceRepository
import kotlinx.coroutines.launch

class DeviceViewModel(private val repository: DeviceRepository) : ViewModel() {

    val devicesData: LiveData<List<Device>> = repository.allDevices.asLiveData()

    fun insert(device: Device) = viewModelScope.launch {
        repository.insert(device)
    }

    fun update(device: Device) = viewModelScope.launch {
        repository.update(device)
    }
}

class DeviceViewModelFactory(private val repository: DeviceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeviceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeviceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}