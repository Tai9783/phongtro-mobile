package com.example.apptimphongtro.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptimphongtro.data.repository.SearchRepository
import com.example.apptimphongtro.model.CityRoomCount
import com.example.apptimphongtro.model.FilterState
import com.example.apptimphongtro.model.RentalRoom
import com.example.apptimphongtro.model.Ward
import com.example.apptimphongtro.util.PriceRange
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository): ViewModel() {
    private val _listCityRoomCount= MutableLiveData<List<CityRoomCount>>()
    val listCityRoomCount: LiveData<List<CityRoomCount>> get()= _listCityRoomCount

    private val _listWard=MutableLiveData<List<Ward>>()
    val listWard: LiveData<List<Ward>> get() = _listWard

    private val _selectedCityName= MutableLiveData("Tp Hồ Chí Minh")
    val selectedCityName: LiveData<String?> get()= _selectedCityName

    private val _selectedWard= MutableLiveData<List<Ward>>(emptyList())
    val selectedWard: LiveData<List<Ward>> get()= _selectedWard

    private val _selectedPrice= MutableLiveData<Set<String>>()

    private val _selectedAmenity= MutableLiveData<Set<String>>()

    private val _selectedSort= MutableLiveData<String>() // lưu trữ loại sắp xếp để xử lý khi ng dùng muốn sắp xếp
    val selectedSort: MutableLiveData<String> get()= _selectedSort

    fun updateTypeSort(typeSort: String){
        _selectedSort.value= typeSort
    }
    fun getSortedList(list: List<RentalRoom>, sortType: String):List<RentalRoom>{
        return when(sortType){
            "PRICE_ASC"->list.sortedBy { it.price } // thấp đến cao
            "PRICE_DESC"-> list.sortedByDescending { it.price }// cao đến thấp
            "NEWEST"->list.sortedByDescending { it.createdAt }// bài đăng mới nhất
            "NEAREST"->list // gần tôi nhất
            else-> list
        }
    }


    //lấy ds phòng khi lọc chuyên sâu bằng màn hình tìm kiếm
    private val _getListRoomFilter= MutableLiveData<List<RentalRoom>>()
    val getListRoomFilter: MutableLiveData<List<RentalRoom>> get()= _getListRoomFilter

    fun fecthListRoomFilter(nameCity: String, nameWard: List<String>, listPrice: List<PriceRange>, listAmenity: List<String>){
        viewModelScope.launch {
            val result= searchRepository.getResultListRoomFillter(nameCity,nameWard,listPrice,listAmenity)
            _getListRoomFilter.value= result
        }
    }


    //Gộp các livedata lại để gọi qua màn hình trả ResultSearchFragment
    val filterState= MediatorLiveData<FilterState>().apply{
        fun rebuild(){
            value= FilterState(
                city = _selectedCityName.value?: "Tp Hồ Chí Minh",
                wards = _selectedWard.value?: emptyList(),
                prices = _selectedPrice.value?: emptySet(),
                aminities = _selectedAmenity.value?: emptySet()
            )
        }
        addSource(_selectedCityName){rebuild()}
        addSource(_selectedWard){rebuild()}
        addSource(_selectedPrice) {rebuild()}
        addSource(_selectedAmenity){rebuild()}
    }

    fun updateSelectedAmenity(listAmenity: Set<String>){
        _selectedAmenity.value= listAmenity
    }

    fun updateSelectedPrice(listChip: Set<String>){
        _selectedPrice.value= listChip
    }


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