package com.example.apptimphongtro.feature.search.data

import com.example.apptimphongtro.core.model.CityRoomCount
import com.example.apptimphongtro.core.model.RentalRoom
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