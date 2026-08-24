package com.example.apptimphongtro.core.di

import com.example.apptimphongtro.core.user.data.UserApiService
import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.feature.mypost.data.MyPostApiService
import com.example.apptimphongtro.feature.search.data.SearchApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideMyPostApiService(): MyPostApiService = RetrofitClient.myPostApiService

    @Provides
    fun provideSearchApiService(): SearchApiService = RetrofitClient.searchApiService

    @Provides
    fun provideUserApiService(): UserApiService = RetrofitClient.userApiService
}
