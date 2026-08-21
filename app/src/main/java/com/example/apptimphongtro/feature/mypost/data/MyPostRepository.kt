package com.example.apptimphongtro.feature.mypost.data

class MyPostRepository(private val myPostApiService: MyPostApiService) {
    suspend fun getMyPost(landlordId: String): Result<List<MyPostRespone>> {
        return runCatching {
            myPostApiService.getMyPost(landlordId)
        }
    }
}

