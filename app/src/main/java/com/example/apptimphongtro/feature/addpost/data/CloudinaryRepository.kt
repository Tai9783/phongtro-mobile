package com.example.apptimphongtro.feature.addpost.data

class CloudinaryRepository(private val cloudinaryApiService: CloudinaryApiService) {
    suspend fun getCloudinarySignature(): CloudinarySignatureResponse{
        return cloudinaryApiService.getCloudinarySignature()
    }
}