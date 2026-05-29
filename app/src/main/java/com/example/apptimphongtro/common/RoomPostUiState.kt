package com.example.apptimphongtro.common

sealed class RoomPostUiState {
    object Idle : RoomPostUiState()
    object Loading: RoomPostUiState()
    data class Success(val postId: String): RoomPostUiState()
    data class Error(val message: String): RoomPostUiState()
}