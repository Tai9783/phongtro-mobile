package com.example.apptimphongtro.feature.search.data

import com.example.apptimphongtro.core.model.CityRoomCount
import com.example.apptimphongtro.core.model.RentalRoom
import com.example.apptimphongtro.core.model.Ward

class SearchRepository(private val searchApiService: SearchApiService) {
    suspend fun getListCityRoomCout(): List<CityRoomCount>{
        return searchApiService.getListCityAndCountRoom()
    }

    suspend fun getLisWard(city:String):List<String>{
        return searchApiService.getLisWardByCity(city)
    }
    suspend fun getResultListRoomFillter(
        nameCity: String,
        nameWard: List<String>,
        listPrice: List<PriceRange>,
        listAmenity: List<String>
    ): List<RentalRoom>{
        val request= FilterRoomRequest(
            nameCity = nameCity,
            nameWard = nameWard,
            listPrice = listPrice,
            listAmenity = listAmenity
        )
        return searchApiService.getFilterRoomAtSearch(request)
    }

}