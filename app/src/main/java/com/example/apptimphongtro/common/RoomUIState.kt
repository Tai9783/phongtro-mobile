package com.example.apptimphongtro.common

import com.example.apptimphongtro.model.entity.RentalRoom
import com.example.apptimphongtro.viewmodel.RoomViewModel

sealed class RoomUIState {
    object Idle : RoomUIState()
    object Loading: RoomUIState()
    data class Success(val room: RentalRoom): RoomUIState()
    data class Error(val message: String): RoomUIState()
}