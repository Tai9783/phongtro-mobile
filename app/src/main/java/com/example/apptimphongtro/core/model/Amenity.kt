package com.example.apptimphongtro.core.model

data class Amenity(
    val amenityId: String,
    val amenityName: String,
    val icon: Int=0,
    var isSelected:Boolean=false

)