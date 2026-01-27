package com.example.apptimphongtro.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val roomApiService: RoomApiService by lazy {
        retrofit.create(RoomApiService::class.java)
    }
    val searchApiService: SearchApiService by lazy {
        retrofit.create(SearchApiService::class.java)
    }
    val userApiService: UserApiService by lazy {
        retrofit.create((UserApiService::class.java))
    }
}

