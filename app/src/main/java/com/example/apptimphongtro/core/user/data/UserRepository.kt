package com.example.apptimphongtro.core.user.data

import com.example.apptimphongtro.core.model.User

class UserRepository(private val userApiService: UserApiService) {
    suspend fun getUser(taikhoan: String,pass: String): User {
        return userApiService.getUser(taikhoan,pass)
    }
    suspend fun getUserById(userId: String): User{
        return userApiService.getUserById(userId)
    }
}