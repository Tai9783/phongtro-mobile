package com.example.apptimphongtro.data.api

import com.example.apptimphongtro.model.CloudinarySignatureResponse
import retrofit2.http.GET

interface CloudinaryApiService {
    @GET("/api/cloudinary/signature")
    suspend fun getCloudinarySignature(): CloudinarySignatureResponse
}