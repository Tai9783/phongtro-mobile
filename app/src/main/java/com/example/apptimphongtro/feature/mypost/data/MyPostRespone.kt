package com.example.apptimphongtro.feature.mypost.data

data class MyPostRespone(
    val postId: String,
    val roomId: String,
    val status: Boolean,
    val createAt: String,
    val expireAt: String,
    val title: String,
    val price: Double,
    val area: Double,
    val imageJson: String
)
