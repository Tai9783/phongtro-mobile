package com.example.apptimphongtro.util

import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.data.repository.MyPostRepository
import com.example.apptimphongtro.viewmodel.factory.MyPostViewModelFactory

object InitMyPostViewModel {
    private val api by lazy { RetrofitClient.myPostApiService }
    private val repository by lazy { MyPostRepository(api) }
    val factory by lazy { MyPostViewModelFactory(repository) }
}