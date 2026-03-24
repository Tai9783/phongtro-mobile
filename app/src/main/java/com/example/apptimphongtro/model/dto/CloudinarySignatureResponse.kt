package com.example.apptimphongtro.model.dto

data class CloudinarySignatureResponse(
    val timestamp: Long,
    val signature: String,
    val apiKey: String,
    val cloudName: String
)