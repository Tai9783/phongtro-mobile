package com.example.apptimphongtro.feature.mypost.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.apptimphongtro.feature.mypost.data.MyPostRespone

class DiffCallBackMyPost: DiffUtil.ItemCallback<MyPostRespone>() {
    override fun areItemsTheSame(oldItem: MyPostRespone, newItem: MyPostRespone): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: MyPostRespone, newItem: MyPostRespone): Boolean {
        return oldItem == newItem
    }
}