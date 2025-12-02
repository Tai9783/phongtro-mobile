package com.example.apptimphongtro.data.repository

import com.example.apptimphongtro.data.api.SearchApiService
import com.example.apptimphongtro.model.CityRoomCount

class SearchRepository(private val searchApiService: SearchApiService) {
    suspend fun getListCityRoomCout(): List<CityRoomCount>{
        return searchApiService.getListCityAndCountRoom()
    }
}