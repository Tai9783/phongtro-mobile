package com.example.apptimphongtro.feature.addpost.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptimphongtro.common.RoomPostUiState
import com.example.apptimphongtro.feature.addpost.data.RoomPostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomPostViewModel @Inject constructor  (private val repository: RoomPostRepository): ViewModel() {

    private val _uiStateRoomPost= MutableLiveData<RoomPostUiState>(RoomPostUiState.Idle)
    val uiStateRoomPost: MutableLiveData<RoomPostUiState> get()= _uiStateRoomPost

    fun saveRoomPost(roomId: String){
        _uiStateRoomPost.value= RoomPostUiState.Loading
        viewModelScope.launch {
            val result= repository.saveRoom(roomId)
            result.fold(
                onSuccess = {
                    _uiStateRoomPost.value= RoomPostUiState.Success(it.postId)
                },
                onFailure = {
                    _uiStateRoomPost.value= RoomPostUiState.Error(it.message ?: "Đăng bài thất bai!")
                }
            )

        }
    }
    fun resetStatePostRoom(){
        _uiStateRoomPost.value= RoomPostUiState.Idle
    }


}