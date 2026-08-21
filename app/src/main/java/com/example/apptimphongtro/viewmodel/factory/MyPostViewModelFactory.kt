package com.example.apptimphongtro.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptimphongtro.data.repository.MyPostRepository
import com.example.apptimphongtro.viewmodel.MyPostViewModel

class MyPostViewModelFactory(private  val repository: MyPostRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyPostViewModel::class.java)) {
            return MyPostViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

