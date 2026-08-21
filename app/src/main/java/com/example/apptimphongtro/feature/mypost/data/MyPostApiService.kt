package com.example.apptimphongtro.feature.mypost.data

import retrofit2.http.GET
import retrofit2.http.Query

interface MyPostApiService {
    @GET("api/mypost/getListPost")
    suspend fun  getMyPost(
        @Query("landlordId") landlordId: String
    ): List<MyPostRespone>
}