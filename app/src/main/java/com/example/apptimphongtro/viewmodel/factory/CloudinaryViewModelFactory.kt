package com.example.apptimphongtro.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptimphongtro.data.repository.CloudinaryRepository
import com.example.apptimphongtro.viewmodel.CloudinaryViewModel
import kotlin.jvm.Throws

class CloudinaryViewModelFactory(private val repository: CloudinaryRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CloudinaryViewModel::class.java)){
            return CloudinaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}