package com.example.apptimphongtro.model

data class  RentalRoom(
    val roomId: String="",
    val landlordId: String="",
    val title: String="",
    val description: String="",
    val price: Double=0.0,
    val area: Double=0.0,
    val imagesJson: List<String> = emptyList(),
    val status: Int=0,
    val createdAt: String="",
    val address: String="",
    val city: String="",
    val ward: String="",
    val amenities: List<Amenity> = emptyList(),
    val lag: Double=0.0,
    val lng: Double=0.0
)
