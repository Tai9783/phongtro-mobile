package com.example.apptimphongtro.feature.addpost.data

import retrofit2.http.GET

interface CloudinaryApiService {
    @GET("/api/cloudinary/signature")
    suspend fun getCloudinarySignature(): CloudinarySignatureResponse
}