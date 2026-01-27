package com.example.apptimphongtro.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptimphongtro.data.repository.UserRepository
import com.example.apptimphongtro.model.User
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {


    private val _user= MutableLiveData<User?>()
    val user: MutableLiveData<User?> get()=_user

    //hàm lấy User từ ID đã lưu trong SharedPrefManager
    fun fetchUser(userId: String){
        viewModelScope.launch {
            try {
                _user.value= userRepository.getUserById(userId)
            }catch (e: Exception){
                Log.e("UserViewlModel","Lỗi lấy thông tin user từ userId: ${e.message}")
            }
        }

    }

    //hàm đăng nhập( dùng cho lần đầu, hoặc đăng nhập lại)
    fun updateUser(taikhoan: String, pass: String){
        viewModelScope.launch {
            try{
                val user= userRepository.getUser(taikhoan,pass)
                _user.value=user
            }catch (e:Exception){
                Log.e("UserViewlModel","Lỗi lấy thông tin user: ${e.message}")
            }
        }
    }

    fun clearUser(){
        _user.value= null
    }

}