package com.example.apptimphongtro.feature.addpost.data

import javax.inject.Inject

class CloudinaryRepository @Inject constructor (private val cloudinaryApiService: CloudinaryApiService) {
    suspend fun getCloudinarySignature(): CloudinarySignatureResponse{
        return cloudinaryApiService.getCloudinarySignature()
    }
}