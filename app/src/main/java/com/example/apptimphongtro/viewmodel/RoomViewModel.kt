package com.example.apptimphongtro.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptimphongtro.data.api.RoomApiService
import com.example.apptimphongtro.data.repository.RoomRepository
import com.example.apptimphongtro.model.RentalRoom
import kotlinx.coroutines.launch
import kotlin.math.min

class RoomViewModel(private val repository: RoomRepository): ViewModel() {
    private val phongNoiBat= MutableLiveData<List<RentalRoom>>()
    val _phongNoiBat: LiveData<List<RentalRoom>> get()= phongNoiBat;

    private val _roomByPrice= MutableLiveData<List<RentalRoom>>()
    val roomByPrice : LiveData<List<RentalRoom>> get()=_roomByPrice

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