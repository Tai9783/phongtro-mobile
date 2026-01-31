package com.example.apptimphongtro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apptimphongtro.model.CityRoomCount

class AddPostAdressViewModel: ViewModel() {
    //ds chứa tất cả các city
    private var _allCity= MutableLiveData<List<CityRoomCount>>()
    val allCity: MutableLiveData<List<CityRoomCount>> get()= _allCity
    //ds chỉ chứa city được chọn
    private var _selectedCity= MutableLiveData<CityRoomCount>()
    val selectedCity get()= _selectedCity


    fun initCityList(listCity: List<CityRoomCount>){
        if (_allCity.value==null) {
            _allCity.value = listCity
            _selectedCity.value= listCity.firstOrNull{it.isSelected} // lấy cityName đã chọn trong ds nếu ds có sẵn
        }
    }
    //Cập nhật trạng thais bằng id
    fun updateItemClick(idCity: Int){
        //Nếu ds rỗng thì dừng
        val currentList= _allCity.value?.toMutableList() ?: return
        val newList= currentList.map{city->
            if (city.idCity==idCity){
               city.copy(isSelected = !city.isSelected)
            }
            else{
                city.copy(isSelected = false)// chỉ cho phép chọn 1 tỉnh thành, khi chọn tỉnh thành khác thì tất cả các tỉnh ko chọn sẽ set về fase
            }
        }
        _allCity.value=newList
        _selectedCity.value=newList.firstOrNull{it.isSelected}//lấy cityName do user chọn
    }

}