package com.example.apptimphongtro.data.local

import android.content.Context

class SharedPrefManager(context: Context) {
    //tạo/mở file sharedPrefManager có tên là UserPrioritize(người dùng ưu tiên)
    private val sharedPrefManager= context.getSharedPreferences("UserPrioritize", Context.MODE_PRIVATE)
    //MODE_PRIVATE: chỉ app này mới có thể đọc và ghi

    fun saveUser(userId: String){
        sharedPrefManager.edit().putString("userId",userId).apply()
        //edit() mở chế độ chỉnh sửa
        //putString lưu theo dạng key-value
        //apply() lưu bất đồng bộ(nhanh, không chặn UI)
    }
    fun getUser():String?{
        return sharedPrefManager.getString("userId",null)
        //lấy dữ liệu trong biến tên là userId, nếu ko có thì trả về null
    }

    // xóa dữ liệu khi đăng xuất
    fun clear(){
        sharedPrefManager.edit().clear().apply()
    }


}