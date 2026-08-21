package com.example.apptimphongtro.feature.addpost.data

import retrofit2.http.Body
import retrofit2.http.POST

interface RoomPostApiSevice {
    @POST("api/roompost/saveroompost")
    suspend fun saverRoom(
        @Body roomId: String
    ): RoomPostRepsonse
}
