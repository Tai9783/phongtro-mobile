package repository
import com.example.apptimphongtro.data.api.RoomPostApiSevice
import com.example.apptimphongtro.data.repository.RoomPostRepository
import com.example.apptimphongtro.model.dto.RoomPostRepsonse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RoomPostRepositoryTest {
    private val apiService: RoomPostApiSevice = mockk()
    private lateinit var repository: RoomPostRepository

    @Before
    fun setUp(){
        repository= RoomPostRepository(apiService)
    }
    @Test
    fun saveRoom_Success(): Unit = runBlocking{
        val roomId= "RoomTest1"
        val fakeResponse= mockk<RoomPostRepsonse>(relaxed = true)
        coEvery{apiService.saverRoom(roomId)} returns fakeResponse
        val result= repository.saveRoom(roomId)

        assert(result.isSuccess)
        assertEquals(fakeResponse,result.getOrNull())
    }

    @Test
    fun saveRoom_Failure(): Unit= runBlocking{
        val roomId= "RoomTest1"
        coEvery { apiService.saverRoom(roomId) } throws Exception("Lỗi Server")
        val result= repository.saveRoom(roomId)
        assertTrue(result.isFailure)
        assertEquals("Lỗi Server",result.exceptionOrNull()?.message)
    }
}