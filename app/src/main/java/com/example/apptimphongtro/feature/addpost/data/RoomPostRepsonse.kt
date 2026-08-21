package com.example.apptimphongtro.feature.addpost.data

import java.util.Date

data class RoomPostRepsonse(
    val postId: String ="",
    val roomId: String ="",
    val createdAt: Date = Date(),
)
