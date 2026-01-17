package com.example.apptimphongtro.data.api

import com.example.apptimphongtro.model.CityRoomCount
import com.example.apptimphongtro.model.FilterRoomRequest
import com.example.apptimphongtro.model.RentalRoom
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchApiService {

    @GET("api/rooms/listCity")
    suspend fun getListCityAndCountRoom(): List<CityRoomCount>

    @GET("api/rooms/listWard")
    suspend fun getLisWardByCity(
        @Query("city") city: String?
    ):List<String>

    @POST("api/rooms/filterRoom")
    suspend fun getFilterRoomAtSearch(
        @Body req: FilterRoomRequest
    ): List<RentalRoom>
}