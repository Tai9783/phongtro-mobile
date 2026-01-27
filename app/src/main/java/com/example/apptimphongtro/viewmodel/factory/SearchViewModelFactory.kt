package com.example.apptimphongtro.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptimphongtro.data.repository.SearchRepository
import com.example.apptimphongtro.viewmodel.SearchViewModel

class SearchViewModelFactory(private val searchRepository: SearchRepository): ViewModelProvider.Factory {
    @Suppress("UNCCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java))
            return SearchViewModel(searchRepository) as T
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}