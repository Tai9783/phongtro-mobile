package com.example.apptimphongtro.data.repository

import com.example.apptimphongtro.data.api.RoomPostApiSevice
import com.example.apptimphongtro.model.dto.RoomPostRepsonse

class RoomPostRepository(private val apiSever: RoomPostApiSevice) {
    suspend fun saveRoom(roomId: String): Result<RoomPostRepsonse>{
       return runCatching {
           apiSever.saverRoom(roomId)
       }
    }
}
