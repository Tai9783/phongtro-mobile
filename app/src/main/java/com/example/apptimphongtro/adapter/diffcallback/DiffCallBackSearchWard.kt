package com.example.apptimphongtro.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.example.apptimphongtro.model.Ward

class DiffCallBackSearchWard: DiffUtil.ItemCallback<Ward>()
{
    override fun areItemsTheSame(oldItem: Ward, newItem: Ward): Boolean {
        return oldItem.wardName==newItem.wardName
    }

    override fun areContentsTheSame(oldItem: Ward, newItem: Ward): Boolean {
        return oldItem== newItem
    }
}