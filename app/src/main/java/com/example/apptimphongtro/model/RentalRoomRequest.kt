package com.example.apptimphongtro.model

data class RentalRoomRequest(
    val landlordId: String="",
    val title: String="",
    val description: String="",
    val price: Double=0.0,
    val area: Double=0.0,
    val imagesJson: List<String> = emptyList(),
    val status: Int=0,
    val address: String="",
    val city: String="",
    val ward: String="",
    val amenities: List<String> = emptyList(),
    val lat: Double=0.0,
    val lng: Double=0.0
)
