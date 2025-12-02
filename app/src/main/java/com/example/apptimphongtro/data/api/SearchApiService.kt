package com.example.apptimphongtro.data.api

import com.example.apptimphongtro.model.CityRoomCount
import retrofit2.http.GET

interface SearchApiService {

    @GET("api/rooms/listCity")
    suspend fun getListCityAndCountRoom(): List<CityRoomCount>

}