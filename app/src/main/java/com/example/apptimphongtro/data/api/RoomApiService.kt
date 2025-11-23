    package com.example.apptimphongtro.data.api

    import com.example.apptimphongtro.model.RentalRoom
    import retrofit2.http.GET

    interface RoomApiService {
        @GET("api/rooms/featured")
        suspend fun getPhongNoiBat():List<RentalRoom>
    }