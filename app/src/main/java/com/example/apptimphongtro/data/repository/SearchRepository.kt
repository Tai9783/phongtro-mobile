package com.example.apptimphongtro.data.repository

import android.service.autofill.FillRequest
import com.example.apptimphongtro.data.api.SearchApiService
import com.example.apptimphongtro.model.CityRoomCount
import com.example.apptimphongtro.model.FilterRoomRequest
import com.example.apptimphongtro.model.RentalRoom
import com.example.apptimphongtro.model.Ward
import com.example.apptimphongtro.util.PriceRange

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