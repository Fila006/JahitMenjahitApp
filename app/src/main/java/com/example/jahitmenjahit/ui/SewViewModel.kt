package com.example.jahitmenjahit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.jahitmenjahit.model.Sew
import com.example.jahitmenjahit.repository.SewRepository
import kotlinx.coroutines.launch

class SewViewModel(private val repository: SewRepository): ViewModel() {
    val allSew: LiveData<List<Sew>> = repository.allSew.asLiveData()

    fun insert(sew: Sew) = viewModelScope.launch {
        repository.insertSew(sew)
    }
    fun delete(sew: Sew) = viewModelScope.launch {
        repository.deleteSew(sew)
    }
    fun update(sew: Sew) = viewModelScope.launch {
        repository.updateSew(sew)
    }
}

class SewViewModelFactory(private val repository: SewRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SewViewModel::class.java)){
            return SewViewModel(repository) as T

        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}