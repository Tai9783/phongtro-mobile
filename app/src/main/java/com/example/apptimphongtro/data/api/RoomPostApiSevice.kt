package com.example.apptimphongtro.data.api

import com.example.apptimphongtro.model.dto.RoomPostRepsonse
import retrofit2.http.Body
import retrofit2.http.POST

interface RoomPostApiSevice {
    @POST("api/roompost/saveroompost")
    suspend fun saverRoom(
        @Body roomId: String
    ): RoomPostRepsonse
}
