package com.example.apptimphongtro.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptimphongtro.data.repository.MyPostRepository
import com.example.apptimphongtro.model.dto.MyPostRespone
import kotlinx.coroutines.launch

class MyPostViewModel(private val repository: MyPostRepository): ViewModel() {
    private val _listMyPost= MutableLiveData<List<MyPostRespone>>()
    val listMyPost: MutableLiveData<List<MyPostRespone>> get()= _listMyPost

    fun getMyPost(landlordId: String){
        viewModelScope.launch {
            val result = repository.getMyPost(landlordId)
            result.fold(
                onSuccess = {
                    _listMyPost.value = it
                    Log.d(" Thanh cong MyPostViewModel", "getMyPost: $it")
                },
                onFailure = {
                    Log.d(" Loi MyPostViewModel", "getMyPost: $it")
                }
            )
        }
    }
}