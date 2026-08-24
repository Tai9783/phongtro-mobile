package com.example.apptimphongtro.core.di

import com.example.apptimphongtro.core.room.data.RoomApiService
import com.example.apptimphongtro.core.user.data.UserApiService
import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.feature.addpost.data.CloudinaryApiService
import com.example.apptimphongtro.feature.addpost.data.RoomPostApiSevice
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

    @Provides
    fun provideRoomApiService(): RoomApiService = RetrofitClient.roomApiService

    @Provides
    fun provideCloudinaryApiService(): CloudinaryApiService = RetrofitClient.cloudinaryApiService

    @Provides
    fun provideRoomPostApiService(): RoomPostApiSevice = RetrofitClient.roomPostApiService
}
