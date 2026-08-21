package com.example.apptimphongtro.core.user

import com.example.apptimphongtro.core.user.data.UserRepository
import com.example.apptimphongtro.core.user.viewmodel.UserViewModelFactory
import com.example.apptimphongtro.data.api.RetrofitClient

object InitUserViewModel {
    private val api by lazy { RetrofitClient.userApiService }
    private val repository by lazy { UserRepository(api) }
    val factory by lazy { UserViewModelFactory(repository) }
}