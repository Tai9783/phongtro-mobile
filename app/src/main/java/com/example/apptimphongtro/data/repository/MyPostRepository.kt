package com.example.apptimphongtro.data.repository

import com.example.apptimphongtro.data.api.MyPostApiService
import com.example.apptimphongtro.model.dto.MyPostRespone

class MyPostRepository(private val myPostApiService: MyPostApiService) {
    suspend fun getMyPost(landlordId: String): Result<List<MyPostRespone>> {
        return runCatching {
            myPostApiService.getMyPost(landlordId)
        }
    }
}

