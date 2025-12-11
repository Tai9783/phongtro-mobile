package com.example.apptimphongtro.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Ward(

    @SerializedName("ward")
    val wardName: String,
    var isCheck: Boolean= false
): Parcelable
