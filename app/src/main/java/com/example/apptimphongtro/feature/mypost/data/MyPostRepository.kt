package com.example.apptimphongtro.feature.mypost.data

import javax.inject.Inject

class MyPostRepository @Inject constructor(private val myPostApiService: MyPostApiService) {
    suspend fun getMyPost(landlordId: String): Result<List<MyPostRespone>> {
        return runCatching {
            myPostApiService.getMyPost(landlordId)
        }
    }
}

