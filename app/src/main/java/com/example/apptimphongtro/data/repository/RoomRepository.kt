package com.example.apptimphongtro.data.repository

import com.example.apptimphongtro.data.api.RoomApiService
import com.example.apptimphongtro.model.dto.RentalRoomRequest
import com.example.apptimphongtro.model.entity.RentalRoom

class RoomRepository(private val apiService: RoomApiService) {
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