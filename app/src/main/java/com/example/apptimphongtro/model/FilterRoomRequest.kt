package com.example.apptimphongtro.model

import com.example.apptimphongtro.util.PriceRange

data class FilterRoomRequest(
    val nameCity: String,
    val nameWard: List<String>,
    val listPrice: List<PriceRange>,
    val listAmenity: List<String>   
)
