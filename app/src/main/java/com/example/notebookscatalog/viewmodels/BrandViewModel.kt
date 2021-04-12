package com.example.notebookscatalog.viewmodels

import androidx.lifecycle.*
import com.example.notebookscatalog.db.entities.Brand
import com.example.notebookscatalog.db.repos.BrandRepository
import kotlinx.coroutines.launch

class BrandViewModel(private val repository: BrandRepository) : ViewModel() {

    val allBrands: LiveData<List<Brand>> = repository.allBrands.asLiveData()

    fun insert(brand: Brand) = viewModelScope.launch {
        repository.insert(brand)
    }
}

class BrandViewModelFactory(private val repository: BrandRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BrandViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BrandViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}