package com.example.apptimphongtro.feature.search

import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.feature.search.data.SearchRepository
import com.example.apptimphongtro.feature.search.viewmodel.SearchViewModelFactory

object InitSearchViewModel {
    private val api by lazy { RetrofitClient.searchApiService }
    private val repository by lazy {  SearchRepository(api) }
    val factory by lazy { SearchViewModelFactory(repository) }
}