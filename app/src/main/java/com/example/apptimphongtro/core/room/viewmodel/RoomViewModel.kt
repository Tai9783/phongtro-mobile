package com.example.apptimphongtro.core.room.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptimphongtro.common.RoomUIState
import com.example.apptimphongtro.core.model.RentalRoom
import com.example.apptimphongtro.core.model.RentalRoomRequest
import com.example.apptimphongtro.core.room.data.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min
@HiltViewModel
class RoomViewModel @Inject constructor (private val repository: RoomRepository): ViewModel() {
    private val phongNoiBat= MutableLiveData<List<RentalRoom>>()
    val _phongNoiBat: LiveData<List<RentalRoom>> get()= phongNoiBat;

    private val _roomByPrice= MutableLiveData<List<RentalRoom>>()
    val roomByPrice : LiveData<List<RentalRoom>> get()=_roomByPrice

    private val _createRoomId= MutableLiveData<String>()
    val createRoomId: LiveData<String> get()= _createRoomId

    private val _uiState= MutableLiveData<RoomUIState>(RoomUIState.Idle)
    val uiState: LiveData<RoomUIState> get()= _uiState



    fun saveRoom(rentalRoom: RentalRoomRequest){
        _uiState.value= RoomUIState.Loading
        viewModelScope.launch {
            val result= repository.insertOrPostRoom(rentalRoom)

            result.onSuccess{room->
                _uiState.value= RoomUIState.Success(room)
                _createRoomId.value = room.roomId
            }
            result.onFailure{error->
                _uiState.value= RoomUIState.Error(error.message ?: "Lỗi không xác định")
            }

        }
    }
    fun reSetState(){
        _uiState.value= RoomUIState.Idle
    }


    fun fetchPhongNoiBat(){
        viewModelScope.launch{
            try{
                val rooms= repository.getFeaturedRooms()
                phongNoiBat.value=rooms
                Log.e("VIEWMODEL", "Lấy dữ liệu thành công,lấy đưuocj ${rooms[0].title}")
            }catch (e: Exception){
                Log.e("VIEWMODEL", "Lỗi lấy dữ liệu: ${e.message}")
            }
        }
    }
    fun filterByPriceAndCity(minPrice: Double?, maxPrice: Double?,city: String) {
        viewModelScope.launch {
            val result = repository.locRoomHome(minPrice,maxPrice,city)
            result.fold(
                onSuccess = { _roomByPrice.value = it },
                onFailure = {   }
            )
        }
    }



}