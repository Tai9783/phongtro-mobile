package com.example.apptimphongtro.feature.addpost.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptimphongtro.feature.addpost.data.RoomPostRepository

class RoomPostViewModelFactory(private val repository: RoomPostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RoomPostViewModel::class.java))
            return RoomPostViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}