package com.example.apptimphongtro.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.example.apptimphongtro.model.Amenity
import com.example.apptimphongtro.model.CityRoomCount

class DiffCallBackAdressCityAddPost: DiffUtil.ItemCallback<CityRoomCount>() {

    override fun areItemsTheSame(oldItem: CityRoomCount, newItem: CityRoomCount): Boolean {
        return oldItem.idCity==newItem.idCity
    }
    override fun areContentsTheSame(oldItem: CityRoomCount, newItem: CityRoomCount): Boolean {
        return oldItem==newItem
    }
}