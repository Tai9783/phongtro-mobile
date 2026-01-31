package com.example.apptimphongtro.model

import com.google.gson.annotations.SerializedName

data class CityRoomCount(
    val idCity: Int=0,
    @SerializedName("city")
    val city: String,

    @SerializedName("roomCount")
    val roomCount: Long=0,
    var isSelected: Boolean=false
)
