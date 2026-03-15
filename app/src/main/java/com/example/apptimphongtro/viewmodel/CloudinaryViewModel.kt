package com.example.apptimphongtro.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptimphongtro.data.repository.CloudinaryRepository
import com.example.apptimphongtro.model.CloudinarySignatureResponse
import kotlinx.coroutines.launch

class CloudinaryViewModel(private val repository: CloudinaryRepository): ViewModel() {
    private val _cloudinary= MutableLiveData<CloudinarySignatureResponse>()
    val clodinary: LiveData<CloudinarySignatureResponse> get()=_cloudinary

    fun getCloudinarySignature(){
        viewModelScope.launch {
            try{
                val cloudinary= repository.getCloudinarySignature()
                _cloudinary.value=cloudinary
                Log.d("CloudinaryViewModel","Xin Chữ ký thành công")

            }catch (e: Exception){
                Log.e("CloudinaryViewModel",e.toString())
            }
        }
    }

}