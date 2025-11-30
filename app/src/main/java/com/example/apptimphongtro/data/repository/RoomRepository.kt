package com.example.apptimphongtro.data.repository

import com.example.apptimphongtro.data.api.RoomApiService
import com.example.apptimphongtro.model.RentalRoom

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



}