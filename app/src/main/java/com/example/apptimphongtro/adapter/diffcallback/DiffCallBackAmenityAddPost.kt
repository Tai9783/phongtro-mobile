package com.example.apptimphongtro.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.example.apptimphongtro.model.Amenity

class DiffCallBackAmenityAddPost: DiffUtil.ItemCallback<Amenity>() {
    override fun areItemsTheSame(oldItem: Amenity, newItem: Amenity): Boolean {
        return oldItem.amenityId== newItem.amenityId
    }

    override fun areContentsTheSame(oldItem: Amenity, newItem: Amenity): Boolean {
       return oldItem==newItem
    }
}