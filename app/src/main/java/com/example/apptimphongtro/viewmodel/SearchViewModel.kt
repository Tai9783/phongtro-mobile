package com.example.apptimphongtro.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptimphongtro.data.repository.SearchRepository
import com.example.apptimphongtro.model.CityRoomCount
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository): ViewModel() {
    private val _listCityRoomCount= MutableLiveData<List<CityRoomCount>>()
    val listCityRoomCount: LiveData<List<CityRoomCount>> get()= _listCityRoomCount

    fun fetchListCityRoomCount(){
        viewModelScope.launch{
            try{
                val listCityRoomCount= searchRepository.getListCityRoomCout()
                _listCityRoomCount.value=listCityRoomCount

            }catch (e: Exception){
                Log.e("searchVIEWMODEL", "Lỗi lấy dữ liệu: ${e.message}")
            }
        }
    }

}