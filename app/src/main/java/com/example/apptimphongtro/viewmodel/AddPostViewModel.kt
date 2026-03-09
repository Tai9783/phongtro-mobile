package com.example.apptimphongtro.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apptimphongtro.model.Amenity
import com.example.apptimphongtro.model.CityRoomCount
import com.example.apptimphongtro.model.RentalRoom
import com.example.apptimphongtro.model.Ward

class AddPostViewModel : ViewModel() {
    private val _currentStep=MutableLiveData(0) // lưu vị trí hiện tại của viewpage2
    val currentStep: LiveData<Int> get()=_currentStep
    private var isLocationHandled = false // biến đánh dấu xem sự kiện này đã được xử lý chưa
    //chứa thông tin bài đăng khi landlord đăng bài
    private var _addPost= MutableLiveData(RentalRoom())
    val addPost: LiveData<RentalRoom> get()=_addPost

    private val _allAmenities= MutableLiveData<List<Amenity>>()
    val allAmenities: MutableLiveData<List<Amenity>> get()= _allAmenities

    //ds chứa tất cả các city
    private var _allCity= MutableLiveData<List<CityRoomCount>>()
    val allCity: MutableLiveData<List<CityRoomCount>> get()= _allCity
    //ds chỉ chứa city được chọn
    private var _selectedCity= MutableLiveData<CityRoomCount?>(null)
    val selectedCity : LiveData<CityRoomCount?> get()= _selectedCity
    //ds chứa tất các ward
    private var _allWard= MutableLiveData<List<Ward>>()
    val allWard: MutableLiveData<List<Ward>> get()= _allWard
    //ds chứa các uri ảnh do người dùng đưa chọn từ điện thoại đưa lên
    private var _allImage= MutableLiveData<List<Uri>>()
    val allImage: LiveData<List<Uri>> get()= _allImage

    private var _selectedWard= MutableLiveData<Ward?>(null)
    val selectedWard : LiveData<Ward?> get()= _selectedWard

    var wardScrollPos: Int = 0
    var wardScrollOffset: Int = 0

    fun setCurrentStep(step: Int){
        _currentStep.value= step
    }
    fun updateSelectWard(wardName: String){
        val trimmedWard = wardName.trim()
        val foundWard = _allWard.value?.find { it.wardName.equals(trimmedWard, ignoreCase = true) }

        if (foundWard != null) {
            _selectedWard.value = foundWard
        } else {
            _selectedWard.value = Ward(wardName = trimmedWard, isCheck = true)
        }
    }
    fun updateSelectCity(cityName: String){
        val trimmedName = cityName.trim()
        // 1. Thử tìm trong danh sách đã load (để lấy đúng ID 1, 2, 3)
        val foundCity = _allCity.value?.find { it.city.equals(trimmedName, ignoreCase = true) }

        if (foundCity != null) {
            _selectedCity.value = foundCity
        } else {
            // Nếu chưa load danh sách, tạo tạm đối tượng với ID -1 để không bị null
            // Sau này khi mở BottomSheet, hàm initCityList sẽ giúp khớp lại sau
            _selectedCity.value = CityRoomCount(idCity = -1, city = trimmedName, isSelected = true)
        }
    }

    fun addImage(uris: List<Uri>){
        _allImage.value= uris
    }
    fun removeImage(uri: Uri){
        val currentList= _allImage.value?.toMutableList()?: mutableListOf()
        currentList.remove(uri)
        _allImage.value= currentList
    }

    fun updateStep1Infor(title: String, decription: String,area: Double, price: Double, amenity: List<Amenity>){
        _addPost.value=_addPost.value!!.copy(title = title, description = decription, area = area, price = price, amenities = amenity)
    }
    fun updateStep2Address(address: String, lag: Double, lng: Double){
        isLocationHandled= false
        _addPost.value= addPost.value!!.copy(address = address, lag = lag, lng = lng)
    }
    fun markAsHandled(){
        isLocationHandled= true
    }
    fun isHandle()=isLocationHandled

    fun initAmenityList(list:List<Amenity>){
        if(_allAmenities.value==null)
            allAmenities.value=list
    }
    fun updateItemClickAmenity(pos: Int){
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








    fun initWartList(listWard: List<Ward>) {
        val selectedName = _selectedWard.value?.wardName?.trim()
        val newList = listWard.map { w ->
            // So sánh an toàn để highlight
            w.copy(isCheck = w.wardName.trim().equals(selectedName, ignoreCase = true))
        }
        _allWard.value = newList

        val found = newList.find { it.isCheck }
        if (found != null) {
            _selectedWard.value = found
        }
    }

    fun initCityList(listCity: List<CityRoomCount>){
        // Luôn cập nhật lại list để đảm bảo highlight đúng
        val selectedName = _selectedCity.value?.city?.trim()
        val newList = listCity.map { c ->
            c.copy(isSelected = c.city.trim().equals(selectedName, ignoreCase = true))
        }
        _allCity.value = newList

        // Nếu chưa có city nào được chọn chính thức (có ID), hãy lấy thằng vừa khớp
        if (_selectedCity.value == null || _selectedCity.value?.idCity == -1) {
            _selectedCity.value = newList.find { it.isSelected }
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