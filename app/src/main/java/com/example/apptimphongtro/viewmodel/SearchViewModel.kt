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

    private val _selectedCityName= MutableLiveData<String?>("Tp Hồ Chí Minh")
    val selectedCityName: LiveData<String?> get()= _selectedCityName

    private val _selectedWard= MutableLiveData<List<Ward>>(emptyList())
    val selectedWard: LiveData<List<Ward>> get()= _selectedWard

    fun updateSelectedWard(listWard: List<Ward>){
        _selectedWard.value= listWard
    }
    fun clearSelectedWard(){
        _selectedWard.value= emptyList()
    }


    fun updateSelectedCityName(city: String?){
        _selectedCityName.value= city
    }

    val selectedCity= MutableLiveData<String>()

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
                val selectedName: Set<String> =
                    _selectedWard.value
                        ?.map { it.wardName.trim() }//chuyển list Ward sang list String
                        ?.toSet()// chuyển list string sang Set<String> nằm tránh trùng lặp dữ liệu và kiểm tra contains() nhanh hơn list
                        ?: emptySet()

              // convert sang Object Ward
                val listWard= list.map {name->
                    val key= name.trim()
                    Ward( wardName = name, isCheck = selectedName.contains(key))
                }
                _listWard.value=listWard
                Log.d("SearchWard","Lay du lieu thanh con $listWard")
            }catch (e:Exception){
                Log.e("searchVIEWMODEL", "Lỗi lấy dữ liệu listWard: ${e.message}")
            }
        }
    }

}