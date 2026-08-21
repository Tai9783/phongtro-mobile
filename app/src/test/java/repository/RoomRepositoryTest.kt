package repository

import com.example.apptimphongtro.core.room.data.RoomApiService
import com.example.apptimphongtro.core.room.data.RoomRepository
import com.example.apptimphongtro.core.model.RentalRoomRequest
import com.example.apptimphongtro.core.model.RentalRoom
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RoomRepositoryTest {
    private val apiService: RoomApiService = mockk()
    private lateinit var repository: RoomRepository

    @Before
    fun setup(){
        repository=RoomRepository(apiService)
    }
    @Test
    fun insertOrPostRoom_success_returnSuccess(): Unit = runBlocking {
        val room= RentalRoomRequest(landlordId = "1", title = "Test Room", description = "Test Description")
        val fakeRoomResponse= RentalRoom(roomId = "1", landlordId = "1", title = "Test Room", description = "Test Description")
        coEvery { apiService.insertOrPostRoom(room) } returns fakeRoomResponse
        val result= repository.insertOrPostRoom(room)
        assertTrue(result.isSuccess)
        assertEquals("1",result.getOrNull()?.roomId)
        assertEquals("Test Room",result.getOrNull()?.title)
    }

    @Test
    fun insertOrPostRoom_failure(): Unit= runBlocking{
        val room= RentalRoomRequest(landlordId = "1", title = "Test Room", description = "Test Description")
        coEvery{apiService.insertOrPostRoom(room)} throws Exception("Lỗi Server")

        val result= repository.insertOrPostRoom(room)
        assertTrue(result.isFailure)
        assertEquals("Lỗi Server",result.exceptionOrNull()?.message)
    }
}