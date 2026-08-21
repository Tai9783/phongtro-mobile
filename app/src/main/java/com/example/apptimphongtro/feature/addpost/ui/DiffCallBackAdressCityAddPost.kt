package com.example.apptimphongtro.feature.addpost.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.apptimphongtro.core.model.Amenity
import com.example.apptimphongtro.core.model.CityRoomCount

class DiffCallBackAdressCityAddPost: DiffUtil.ItemCallback<CityRoomCount>() {

    override fun areItemsTheSame(oldItem: CityRoomCount, newItem: CityRoomCount): Boolean {
        return oldItem.idCity==newItem.idCity
    }
    override fun areContentsTheSame(oldItem: CityRoomCount, newItem: CityRoomCount): Boolean {
        return oldItem==newItem
    }
}