package com.example.apptimphongtro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.diffcallback.DiffCallBackMyPost
import com.example.apptimphongtro.model.dto.MyPostRespone
import com.example.apptimphongtro.util.FormatMoney
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson

class MyPostAdapter: ListAdapter<MyPostRespone, MyPostAdapter.MyPostViewHolder>(DiffCallBackMyPost()) {
    inner class MyPostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ShapeableImageView>(R.id.imgMyPost)
        private val title = itemView.findViewById<TextView>(R.id.txtItemTitle)
        private val price = itemView.findViewById<TextView>(R.id.txtItemPrice)
        private val area = itemView.findViewById<TextView>(R.id.txtItemArea)
        private val status = itemView.findViewById<TextView>(R.id.txtItemStatus)
        private val expireAt = itemView.findViewById<TextView>(R.id.txtItemExpireAt)

        fun bind(post: MyPostRespone) {
            title.text = post.title
            price.text = FormatMoney().formatMoney(post.price) + "/tháng"
            area.text = post.area.toString() + "m²"
            expireAt.text = itemView.context.getString(R.string.MyPost_item_time) + " " + post.expireAt

            if (post.status) {
                status.text = itemView.context.getString(R.string.MyPost_status_active)
                status.setBackgroundResource(R.drawable.bo4goc_vienxanhla)
            } else {
                status.text = itemView.context.getString(R.string.MyPost_status_expired)
                status.setBackgroundResource(R.drawable.bo4goc_vien)
            }

            val imageList: List<String> = runCatching {
                Gson().fromJson(post.imageJson, Array<String>::class.java).toList()
            }.getOrDefault(emptyList())

            Glide.with(itemView.context)
                .load(imageList.firstOrNull())
                .placeholder(R.drawable.anhdefault)
                .error(R.drawable.anhdefault)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_post, parent, false)
        return MyPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}