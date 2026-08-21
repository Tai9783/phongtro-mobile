package com.example.apptimphongtro.data.api

import com.example.apptimphongtro.core.room.data.RoomApiService
import com.example.apptimphongtro.core.user.data.UserApiService
import com.example.apptimphongtro.feature.addpost.data.CloudinaryApiService
import com.example.apptimphongtro.feature.addpost.data.CloudinaryUploadService
import com.example.apptimphongtro.feature.addpost.data.RoomPostApiSevice
import com.example.apptimphongtro.feature.mypost.data.MyPostApiService
import com.example.apptimphongtro.feature.search.data.SearchApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"
    private const val URL_CLOUDINARY="https://api.cloudinary.com/"
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val retrofitCloudinary: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL_CLOUDINARY)
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
    val cloudinaryApiService: CloudinaryApiService by lazy {
        retrofit.create(CloudinaryApiService::class.java)
    }
    val cloudinaryUploadService: CloudinaryUploadService by lazy {
        retrofitCloudinary.create(CloudinaryUploadService::class.java)
    }
    val roomPostApiService: RoomPostApiSevice by lazy {
        retrofit.create(RoomPostApiSevice::class.java)
    }

    val myPostApiService: MyPostApiService by lazy {
        retrofit.create(MyPostApiService::class.java)
    }
}

