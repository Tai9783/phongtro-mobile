package com.example.apptimphongtro.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.example.apptimphongtro.model.RentalRoom

class DiffCallBackRoom: DiffUtil.ItemCallback<RentalRoom>() {
    override fun areItemsTheSame(oldItem: RentalRoom, newItem: RentalRoom): Boolean {
        return oldItem.roomId== newItem.roomId
    }

    override fun areContentsTheSame(oldItem: RentalRoom, newItem: RentalRoom): Boolean {
        return oldItem==newItem
    }
}