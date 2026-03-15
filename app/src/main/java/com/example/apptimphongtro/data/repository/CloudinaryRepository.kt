package com.example.apptimphongtro.data.repository

import com.example.apptimphongtro.data.api.CloudinaryApiService
import com.example.apptimphongtro.model.CloudinarySignatureResponse

class CloudinaryRepository(private val cloudinaryApiService: CloudinaryApiService) {
    suspend fun getCloudinarySignature(): CloudinarySignatureResponse{
        return cloudinaryApiService.getCloudinarySignature()
    }
}