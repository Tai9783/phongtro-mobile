package com.example.apptimphongtro.feature.addpost.data

import javax.inject.Inject

class RoomPostRepository @Inject constructor (private val apiSever: RoomPostApiSevice) {
    suspend fun saveRoom(roomId: String): Result<RoomPostRepsonse>{
       return runCatching {
           apiSever.saverRoom(roomId)
       }
    }
}
