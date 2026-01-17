package com.example.apptimphongtro.util

import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.data.repository.SearchRepository
import com.example.apptimphongtro.viewmodel.factory.SearchViewModelFactory

object InitSearchViewModel {
    private val api by lazy { RetrofitClient.searchApiService }
    private val repository by lazy {  SearchRepository(api) }
    val factory by lazy { SearchViewModelFactory(repository) }
}