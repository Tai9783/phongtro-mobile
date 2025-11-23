package com.example.apptimphongtro.data.repository

import com.example.apptimphongtro.data.api.RoomApiService
import com.example.apptimphongtro.model.RentalRoom

class RoomRepository(private val apiService: RoomApiService) {
    suspend fun getFeaturedRooms(): List<RentalRoom> {
        // Gọi hàm từ service
        return apiService.getPhongNoiBat()
    }
}