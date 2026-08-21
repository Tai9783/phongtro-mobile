package com.example.apptimphongtro.feature.addpost.data

class RoomPostRepository(private val apiSever: RoomPostApiSevice) {
    suspend fun saveRoom(roomId: String): Result<RoomPostRepsonse>{
       return runCatching {
           apiSever.saverRoom(roomId)
       }
    }
}
