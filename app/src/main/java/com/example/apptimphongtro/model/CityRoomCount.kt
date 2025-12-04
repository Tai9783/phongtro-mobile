package com.example.apptimphongtro.model

import com.google.gson.annotations.SerializedName

data class CityRoomCount(
    @SerializedName("city")
    val city: String,

    @SerializedName("roomCount")
    val roomCount: Long
)
