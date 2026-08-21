package com.example.apptimphongtro.core.room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptimphongtro.core.room.data.RoomRepository

class RoomViewModelFactory(private val repository: RoomRepository):ViewModelProvider.Factory {
    @Suppress("UNCCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java))
                return RoomViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}