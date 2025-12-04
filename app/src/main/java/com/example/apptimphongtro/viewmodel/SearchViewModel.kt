package com.example.apptimphongtro.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptimphongtro.data.repository.SearchRepository
import com.example.apptimphongtro.model.CityRoomCount
import com.example.apptimphongtro.model.Ward
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository): ViewModel() {
    private val _listCityRoomCount= MutableLiveData<List<CityRoomCount>>()
    val listCityRoomCount: LiveData<List<CityRoomCount>> get()= _listCityRoomCount

    private val _listWard=MutableLiveData<List<Ward>>()
    val listWard: LiveData<List<Ward>> get() = _listWard

    fun fetchListCityRoomCount(){
        viewModelScope.launch{
            try{
                val listCityRoomCount= searchRepository.getListCityRoomCout()
                _listCityRoomCount.value=listCityRoomCount
            }catch (e: Exception){
                Log.e("searchVIEWMODEL", "Lỗi lấy dữ liệu listCity: ${e.message}")
            }
        }
    }

    fun fechListWardByCity(city:String){
        viewModelScope.launch {
            try{
                val list= searchRepository.getLisWard(city) // nhận 1 List<String>

                val listWard= list.map { Ward(wardName = it, isCheck = false) } // convert sang Object Ward
                _listWard.value=listWard
                Log.d("SearchWard","Lay du lieu thanh con $listWard")
            }catch (e:Exception){
                Log.e("searchVIEWMODEL", "Lỗi lấy dữ liệu listWard: ${e.message}")
            }
        }
    }

}