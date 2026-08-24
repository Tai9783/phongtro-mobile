package com.example.apptimphongtro.feature.mypost.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptimphongtro.feature.mypost.data.MyPostRepository
import com.example.apptimphongtro.feature.mypost.data.MyPostRespone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPostViewModel @Inject constructor(private val repository: MyPostRepository): ViewModel() {
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