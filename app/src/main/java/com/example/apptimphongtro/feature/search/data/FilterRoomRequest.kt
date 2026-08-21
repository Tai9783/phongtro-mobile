package com.example.apptimphongtro.feature.search.data

data class FilterRoomRequest(
    val nameCity: String,
    val nameWard: List<String>,
    val listPrice: List<PriceRange>,
    val listAmenity: List<String>
)