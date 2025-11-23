package com.example.apptimphongtro.model

data class RentalRoom(
    val roomId: String,
    val landlordId: String?, // Có thể null
    val title: String,
    val description: String,
    val price: Double,
    val area: Double,
    val imagesJson: String?,
    val status: Int,
    val createdAt: String, // Hoặc LocalDateTime nếu bạn xử lý bằng thư viện phụ
    val address: String,
    val city: String,
    val ward: String
)
