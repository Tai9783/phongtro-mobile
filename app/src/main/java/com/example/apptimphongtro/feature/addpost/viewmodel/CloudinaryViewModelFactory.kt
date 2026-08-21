package com.example.apptimphongtro.feature.addpost.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptimphongtro.feature.addpost.data.CloudinaryRepository

class CloudinaryViewModelFactory(private val repository: CloudinaryRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CloudinaryViewModel::class.java)){
            return CloudinaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}