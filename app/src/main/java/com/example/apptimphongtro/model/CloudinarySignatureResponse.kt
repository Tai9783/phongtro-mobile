package com.example.apptimphongtro.model

import java.security.Signature
import java.sql.Timestamp

data class CloudinarySignatureResponse(
    val timestamp: Long,
    val signature: String,
    val apiKey: String,
    val cloudName: String
)
