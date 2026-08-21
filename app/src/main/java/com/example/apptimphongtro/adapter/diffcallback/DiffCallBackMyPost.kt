package com.example.apptimphongtro.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.example.apptimphongtro.model.dto.MyPostRespone

class DiffCallBackMyPost: DiffUtil.ItemCallback<MyPostRespone>() {
    override fun areItemsTheSame(oldItem: MyPostRespone, newItem: MyPostRespone): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: MyPostRespone, newItem: MyPostRespone): Boolean {
        return oldItem == newItem
    }
}