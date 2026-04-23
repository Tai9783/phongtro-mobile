    package com.example.apptimphongtro.data.api

    import com.example.apptimphongtro.model.dto.RentalRoomRequest
    import com.example.apptimphongtro.model.entity.RentalRoom
    import retrofit2.http.Body
    import retrofit2.http.GET
    import retrofit2.http.POST
    import retrofit2.http.Query

    interface RoomApiService {
        @GET("api/rooms/featured")
        suspend fun getPhongNoiBat():List<RentalRoom>

        @GET("api/rooms/roomByPriceAndCity")
        suspend fun getLocPhongHome(
            @Query("minPrice") minPrice:Double?,
            @Query("maxPrice") maxPrice:Double?,
            @Query("city") city:String?
        ):List<RentalRoom>

        @POST("api/rooms/createRentalRoom")
        suspend fun insertOrPostRoom(
            @Body room: RentalRoomRequest
        ): RentalRoom
    }

