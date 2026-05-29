package com.example.apptimphongtro.model.dto

import java.util.Date

data class RoomPostRepsonse(
    val postId: String ="",
    val roomId: String ="",
    val createdAt: Date = Date(),
)
