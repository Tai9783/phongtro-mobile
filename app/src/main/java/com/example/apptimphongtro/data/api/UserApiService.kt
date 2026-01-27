package com.example.apptimphongtro.data.api

import com.example.apptimphongtro.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {

    @GET("api/user/getuser")
    suspend fun getUser(
        @Query("taikhoan") taikhoan: String,
        @Query("pass") pass : String
    ): User

    @GET("api/user/getuserbyid")
    suspend fun getUserById(@Query("userId") userId: String): User

}