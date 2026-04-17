package com.example.apptimphongtro.model.entity

data class Amenity(
    val amenityId: String,
    val amenityName: String,
    val icon: Int=0,
    var isSelected:Boolean=false

)