package com.example.apptimphongtro.feature.addpost.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.apptimphongtro.core.model.Amenity

class DiffCallBackAmenityAddPost: DiffUtil.ItemCallback<Amenity>() {
    override fun areItemsTheSame(oldItem: Amenity, newItem: Amenity): Boolean {
        return oldItem.amenityId== newItem.amenityId
    }

    override fun areContentsTheSame(oldItem: Amenity, newItem: Amenity): Boolean {
       return oldItem==newItem
    }
}