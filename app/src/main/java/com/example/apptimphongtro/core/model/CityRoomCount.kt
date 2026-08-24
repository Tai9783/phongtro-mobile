package com.example.apptimphongtro.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityRoomCount(
    val idCity: Int=0,
    @SerializedName("city")
    val city: String,

    @SerializedName("roomCount")
    val roomCount: Long=0,
    var isSelected: Boolean=false
): Parcelable