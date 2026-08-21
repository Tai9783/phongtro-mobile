package com.example.apptimphongtro.core.model

import androidx.recyclerview.widget.DiffUtil

class DiffCallBackSearchWard: DiffUtil.ItemCallback<Ward>()
{
    override fun areItemsTheSame(oldItem: Ward, newItem: Ward): Boolean {
        return oldItem.wardName==newItem.wardName
    }

    override fun areContentsTheSame(oldItem: Ward, newItem: Ward): Boolean {
        return oldItem== newItem
    }
}