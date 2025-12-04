package com.example.apptimphongtro.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class Ward(

    @SerializedName("ward")
    val wardName: String,
    val isCheck: Boolean= false
)
