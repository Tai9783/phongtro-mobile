package com.example.apptimphongtro.util

import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.data.repository.UserRepository
import com.example.apptimphongtro.viewmodel.factory.UserViewModelFactory

object InitUserViewModel {
    private val api by lazy { RetrofitClient.userApiService }
    private val repository by lazy { UserRepository(api) }
    val factory by lazy { UserViewModelFactory(repository) }
}