package com.example.apptimphongtro.feature.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptimphongtro.feature.search.data.SearchRepository

class SearchViewModelFactory(private val searchRepository: SearchRepository): ViewModelProvider.Factory {
    @Suppress("UNCCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java))
            return SearchViewModel(searchRepository) as T
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}