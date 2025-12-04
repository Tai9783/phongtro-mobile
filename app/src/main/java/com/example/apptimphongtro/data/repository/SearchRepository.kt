package com.example.apptimphongtro.data.repository

import com.example.apptimphongtro.data.api.SearchApiService
import com.example.apptimphongtro.model.CityRoomCount
import com.example.apptimphongtro.model.Ward

class SearchRepository(private val searchApiService: SearchApiService) {
    suspend fun getListCityRoomCout(): List<CityRoomCount>{
        return searchApiService.getListCityAndCountRoom()
    }

    suspend fun getLisWard(city:String):List<String>{
        return searchApiService.getLisWardByCity(city)
    }
}