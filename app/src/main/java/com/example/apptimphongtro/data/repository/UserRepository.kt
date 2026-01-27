package com.example.apptimphongtro.data.repository

import com.example.apptimphongtro.data.api.UserApiService
import com.example.apptimphongtro.model.User

class UserRepository(private val userApiService: UserApiService) {
    suspend fun getUser(taikhoan: String,pass: String): User {
        return userApiService.getUser(taikhoan,pass)
    }
    suspend fun getUserById(userId: String): User{
        return userApiService.getUserById(userId)
    }
}