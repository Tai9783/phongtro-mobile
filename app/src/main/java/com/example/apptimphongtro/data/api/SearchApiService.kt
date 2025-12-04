package com.example.apptimphongtro.data.api

import com.example.apptimphongtro.model.CityRoomCount
import com.example.apptimphongtro.model.Ward
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {

    @GET("api/rooms/listCity")
    suspend fun getListCityAndCountRoom(): List<CityRoomCount>

    @GET("api/rooms/listWard")
    suspend fun getLisWardByCity(
        @Query("city") city: String?
    ):List<String>

}