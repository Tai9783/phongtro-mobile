package com.example.apptimphongtro.feature.addpost.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptimphongtro.feature.addpost.data.CloudinaryRepository
import com.example.apptimphongtro.feature.addpost.data.CloudinarySignatureResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CloudinaryViewModel @Inject constructor (private val repository: CloudinaryRepository): ViewModel() {
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