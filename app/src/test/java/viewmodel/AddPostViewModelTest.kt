package com.example.apptimphongtro.viewmodel

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.apptimphongtro.core.model.CityRoomCount
import com.example.apptimphongtro.feature.addpost.viewmodel.AddPostViewModel
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddPostViewModelTest {

    @Rule //
    @JvmField
    val instantTaskExecutorRule= InstantTaskExecutorRule() //bẻ gãy cơ chế bất đồng bộ của livedata và ép chạy trên 1 luồng duy nhất
    private lateinit var addPostViewModel: AddPostViewModel

    @Before
    fun setup(){
      addPostViewModel= AddPostViewModel(SavedStateHandle())
    }

    @Test
    fun resetAddPost_cleansAllDataSuccessfully(){
        addPostViewModel.setCurrentStep(2)
        addPostViewModel.updateSelectCity("Hồ Chí Minh")
        addPostViewModel.updateSelectWard("Bình Thạnh")
        val fakeUri= mockk<Uri>(relaxed = true)
        addPostViewModel.addImage(listOf(fakeUri))
        addPostViewModel.updateStep1Infor("Test Title", "Test Description", 100.0, 200.0, listOf("1","2"))
        addPostViewModel.updateStep2Address("Test Address", 123.456, 789.012)
        addPostViewModel.updateStep3("LandlordId", listOf("Image1", "Image2"))

        addPostViewModel.resetAddPost()// gọi hàm chính cần test

        assertEquals(0,addPostViewModel.currentStep.value)
        assertNull(addPostViewModel.selectedCity.value)
        assertNull(addPostViewModel.selectedWard.value)
        assertEquals(emptyList<Uri>(),addPostViewModel.allImage.value)
        val amenities= addPostViewModel.allAmenities.value
        if(amenities !=null){
            amenities.forEach {
                assertEquals(false,it.isSelected)
            }
        }

        val currentRequest= addPostViewModel.addPost.value
            assertEquals("",currentRequest?.title)
            assertEquals("",currentRequest?.description)
            assertEquals(0.0,currentRequest?.area?:0.0,0.0)
    }

    @Test
    fun updateItemClick_in_addPostViewModel(){
        val city1= CityRoomCount(1, "Hồ Chí Minh", 10, isSelected = false)
        val city2= CityRoomCount(2, "Hà Nội", 5, isSelected = false)
        val city3= CityRoomCount(3, "Đà Nẵng", 8, isSelected = true)
        val listCity= listOf(city1,city2,city3)

        addPostViewModel.initCityList(listCity)
        addPostViewModel.updateSelectWard("Bình Thạnh")

        addPostViewModel.updateItemClick(1)
        val currentList= addPostViewModel.allCity.value

        assertTrue(currentList?.find { it.idCity==1 }?.isSelected?:false)
        assertFalse(currentList?.find { it.idCity==2 }?.isSelected?:true)
        assertFalse(currentList?.find { it.idCity==3 }?.isSelected?:true)

        assertEquals("Hồ Chí Minh",addPostViewModel.selectedCity.value?.city)
        assertNull(addPostViewModel.selectedWard.value)
    }

    @Test
    fun updateStep1Infor_inputsValidate(){
        addPostViewModel.updateStep1Infor(
            "Test Title",
            "Test Description",
            100.0,
            200.0,
            listOf("1","2"))

        val currentRequest= addPostViewModel.addPost.value

        assertNotNull(currentRequest)

        assertEquals("Test Title",currentRequest?.title)
        assertEquals("Test Description",currentRequest?.description)
        assertEquals(100.0,currentRequest?.area ?: 0.0,0.0)
        assertEquals(200.0,currentRequest?.price ?: 0.0,0.0)
        assertEquals(listOf("1","2"),currentRequest?.amenities)

    }
    @Test
    fun removeImage_removesImageSuccessfully(){
        val fakeUri1= mockk<Uri>(relaxed = true)
        val fakeUri2= mockk<Uri>(relaxed = true)

        val listUri= listOf(fakeUri1,fakeUri2)
        addPostViewModel.addImage(listUri)
        addPostViewModel.removeImage(fakeUri1)

        val currentListImage= addPostViewModel.allImage.value

        assertEquals(1,currentListImage?.size)
        assertEquals(fakeUri2, currentListImage?.first())
    }

    @Test
    fun initCityList_addPostViewModel(){
        addPostViewModel.updateSelectCity("Hà Nội")

        val city1= CityRoomCount(1, "Hồ Chí Minh", 10, isSelected = false)
        val city2= CityRoomCount(2, "Hà Nội", 5, isSelected = false)
        val city3= CityRoomCount(3, "Đà Nẵng", 8, isSelected = false)
        val listCity= listOf(city1,city2,city3)

        addPostViewModel.initCityList(listCity)

        val currrentList= addPostViewModel.allCity.value
        val resultHaNoi= currrentList?.find { it.idCity==2 }
        assertEquals(true, resultHaNoi?.isSelected)

        val resultDaNang= currrentList?.find { it.idCity ==3 }
        assertEquals(false, resultDaNang?.isSelected)
    }
}