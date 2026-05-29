package com.example.apptimphongtro.viewmodel.factory

import android.widget.ViewSwitcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptimphongtro.data.repository.RoomPostRepository
import com.example.apptimphongtro.viewmodel.RoomPostViewModel

class RoomPostViewModelFactory(private val repository: RoomPostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RoomPostViewModel::class.java))
            return RoomPostViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}