package com.example.apptimphongtro.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apptimphongtro.model.Amenity

class AddPostViewModel : ViewModel() {

    private val _allAmenities= MutableLiveData<List<Amenity>>()
    val allAmenities: MutableLiveData<List<Amenity>> get()= _allAmenities


    fun initAmenityList(list:List<Amenity>){
        if(_allAmenities.value==null)
            allAmenities.value=list
    }
    fun updateItemClick(pos: Int){
        // Lấy list hiện tại, nếu null thì thoát
        val currentList = _allAmenities.value ?: return

        // Tạo một list mới và copy toàn bộ data sang (để DiffUtil nhận ra sự khác biệt)
        val newList = currentList.mapIndexed { index, amenity ->
            if (index == pos) {
                // Copy đối tượng và đảo ngược trạng thái isSelected
                amenity.copy(isSelected = !amenity.isSelected)
            } else {
                // Giữ nguyên các đối tượng khác
                amenity
            }
        }
        //Cập nhật lại LiveData với list MỚI
        _allAmenities.value = newList
    }


}