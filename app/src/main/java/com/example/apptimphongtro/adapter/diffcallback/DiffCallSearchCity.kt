package com.example.apptimphongtro.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.example.apptimphongtro.model.CityRoomCount

class DiffCallSearchCity: DiffUtil.ItemCallback<CityRoomCount>() {
    override fun areItemsTheSame(oldItem: CityRoomCount, newItem: CityRoomCount): Boolean {
        return oldItem.city==newItem.city
    }

    override fun areContentsTheSame(oldItem: CityRoomCount, newItem: CityRoomCount): Boolean {
        return oldItem== newItem
    }
}