package com.example.apptimphongtro.feature.mypost

import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.feature.mypost.data.MyPostRepository
import com.example.apptimphongtro.feature.mypost.viewmodel.MyPostViewModelFactory

object InitMyPostViewModel {
    private val api by lazy { RetrofitClient.myPostApiService }
    private val repository by lazy { MyPostRepository(api) }
    val factory by lazy { MyPostViewModelFactory(repository) }
}