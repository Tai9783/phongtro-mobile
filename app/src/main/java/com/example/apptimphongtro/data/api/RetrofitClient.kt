package com.example.apptimphongtro.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"
    val apiService: RoomApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RoomApiService::class.java)
    }
}
/*
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    // Khởi tạo Retrofit Core, chỉ cần làm một lần.
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 1. Tái sử dụng đối tượng Retrofit cho RoomApiService
    val roomApiService: RoomApiService by lazy {
        retrofit.create(RoomApiService::class.java)
    }

    // 2. Thêm một API Service mới cho màn hình/chức năng tìm kiếm
    val searchApiService: SearchApiService by lazy {
        retrofit.create(SearchApiService::class.java)
    }

    // ... Có thể thêm UserApiService, v.v.
}*/
