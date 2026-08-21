package com.example.apptimphongtro.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ward(

    @SerializedName("ward")
    val wardName: String,
    var isCheck: Boolean= false
): Parcelable