package com.example.apptimphongtro.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptimphongtro.data.api.RoomApiService
import com.example.apptimphongtro.data.repository.RoomRepository
import com.example.apptimphongtro.viewmodel.RoomViewModel

class RoomViewModelFactory(private val repository: RoomRepository):ViewModelProvider.Factory {
    @Suppress("UNCCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java))
                return RoomViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}