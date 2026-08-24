package com.example.apptimphongtro.core.room.data

import com.example.apptimphongtro.core.model.RentalRoom
import com.example.apptimphongtro.core.model.RentalRoomRequest
import javax.inject.Inject

class RoomRepository @Inject constructor (private val apiService: RoomApiService) {
    suspend fun getFeaturedRooms(): List<RentalRoom> {
        // Gọi hàm từ service
        return apiService.getPhongNoiBat()
    }

    suspend fun locRoomHome(minPrice: Double?, maxPrice: Double?, city: String): Result<List<RentalRoom>> {
        return try {
            val list = apiService.getLocPhongHome(minPrice,maxPrice,city)
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun insertOrPostRoom(room: RentalRoomRequest): Result<RentalRoom> {
        return try {
            val createdRoom = apiService.insertOrPostRoom(room)
            Result.success(createdRoom)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}