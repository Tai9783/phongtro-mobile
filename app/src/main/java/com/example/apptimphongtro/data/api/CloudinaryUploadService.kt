package com.example.apptimphongtro.data.api

import com.example.apptimphongtro.model.CloudinaryRespone
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface CloudinaryUploadService {
    @Multipart //thông báo với Retrofit rằng yêu cầu này sẽ gửi nhiều loại dữ liệu khác nhau cùng lúc"
    @POST("v1_1/{cloudName}/image/upload")
    suspend fun uploadImage(
        @Path("cloudName") cloudName: String,
        @Part file: MultipartBody.Part,
        @Part("api_key") apiKey: RequestBody,
        @Part("timestamp") timestamp: RequestBody,
        @Part("signature") signature: RequestBody
    ): CloudinaryRespone
}