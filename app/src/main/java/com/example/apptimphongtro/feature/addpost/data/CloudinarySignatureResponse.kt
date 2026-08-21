package com.example.apptimphongtro.feature.addpost.data

data class CloudinarySignatureResponse(
    val timestamp: Long,
    val signature: String,
    val apiKey: String,
    val cloudName: String
)