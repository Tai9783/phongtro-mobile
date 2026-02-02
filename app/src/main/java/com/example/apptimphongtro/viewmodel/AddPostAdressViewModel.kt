package com.example.apptimphongtro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apptimphongtro.model.CityRoomCount
import com.example.apptimphongtro.model.Ward

class AddPostAdressViewModel: ViewModel() {
    //ds chứa tất cả các city
    private var _allCity= MutableLiveData<List<CityRoomCount>>()
    val allCity: MutableLiveData<List<CityRoomCount>> get()= _allCity
    //ds chỉ chứa city được chọn
    private var _selectedCity= MutableLiveData<CityRoomCount?>(null)
    val selectedCity : LiveData<CityRoomCount?> get()= _selectedCity
    //ds chứa tất các ward
    private var _allWard= MutableLiveData<List<Ward>>()
    val allWard: MutableLiveData<List<Ward>> get()= _allWard

    private var _selectedWard= MutableLiveData<Ward?>(null)
    val selectedWard : LiveData<Ward?> get()= _selectedWard

    var wardScrollPos: Int = 0
    var wardScrollOffset: Int = 0

    fun initWartList(listWard: List<Ward>) {
        val selectedName = _selectedWard.value?.wardName
        val newList = listWard.map { w ->
            w.copy(isCheck = (w.wardName == selectedName))
        }
        _allWard.value = newList
        _selectedWard.value = newList.firstOrNull { it.isCheck }
    }



    fun initCityList(listCity: List<CityRoomCount>){
        if (_allCity.value==null) {
            _allCity.value = listCity
            _selectedCity.value= listCity.firstOrNull{it.isSelected} // lấy cityName đã chọn trong ds nếu ds có sẵn
        }
    }
    fun updateItemWardClick(wardName: String){
        val currentListWard= _allWard.value?.toMutableList() ?: return
        val newListWard= currentListWard.map{
            if (wardName==it.wardName){
                it.copy(isCheck = it.wardName==wardName) //bắt buộc chọn 1 item, ko nhiều hơn hay ít hơn
            }
            else{
                it.copy(isCheck = false)
            }
        }
        _allWard.value= newListWard
        _selectedWard.value= newListWard.firstOrNull{it.isCheck}
    }
    //Cập nhật trạng thais bằng id
    fun updateItemClick(idCity: Int){
        //Nếu ds rỗng thì dừng
        val currentList= _allCity.value?.toMutableList() ?: return
        val newList= currentList.map{city->
            if (city.idCity==idCity){
               city.copy(isSelected = city.idCity==idCity) // bắt buộc chọn 1 item, ko nhiều hơn hay ít hơn
            }
            else{
                city.copy(isSelected = false)// chỉ cho phép chọn 1 tỉnh thành, khi chọn tỉnh thành khác thì tất cả các tỉnh ko chọn sẽ set về fase
            }
        }
        _allCity.value=newList
        _selectedCity.value=newList.firstOrNull{it.isSelected}//lấy cityName do user chọn

        //reset lại ward khi user chọn city khác
        _selectedWard.value= null
        _allWard.value= emptyList()
    }

}