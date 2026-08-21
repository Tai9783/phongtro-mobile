package com.example.apptimphongtro.feature.search.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.apptimphongtro.core.model.RentalRoom

class DiffCallBackSearchRoom: DiffUtil.ItemCallback<RentalRoom>() {
    override fun areItemsTheSame(oldItem: RentalRoom, newItem: RentalRoom): Boolean {
        return oldItem.roomId == newItem.roomId
    }

    override fun areContentsTheSame(oldItem: RentalRoom, newItem: RentalRoom): Boolean {
       return oldItem== newItem
    }
}