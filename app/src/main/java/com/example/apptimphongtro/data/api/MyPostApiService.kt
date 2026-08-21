package com.example.apptimphongtro.data.api

import com.example.apptimphongtro.model.dto.MyPostRespone
import retrofit2.http.GET
import retrofit2.http.Query

interface MyPostApiService {
    @GET("api/mypost/getListPost")
    suspend fun  getMyPost(
        @Query("landlordId") landlordId: String
    ): List<MyPostRespone>
}