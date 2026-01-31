package com.example.apptimphongtro.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.diffcallback.DiffCallBackAmenityAddPost
import com.example.apptimphongtro.model.Amenity

class AmenityAdapter( private val onClickAmenity: OnClick) : ListAdapter<Amenity,AmenityAdapter.AmenityHolder>(
    DiffCallBackAmenityAddPost()
) {
    inner class AmenityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageIcon: ImageView = itemView.findViewById(R.id.img_icon)
        private val nameAmenity: TextView = itemView.findViewById(R.id.tv_name)
        private val layoutContraint: LinearLayout = itemView.findViewById(R.id.layout_container)
        fun bin(item: Amenity) {
            nameAmenity.text = item.amenityName
            imageIcon.setImageResource(item.icon)
            layoutContraint.isSelected = item.isSelected

            if (item.isSelected) {
                nameAmenity.setTextColor(Color.parseColor("#1A1A1A"))
                imageIcon.setColorFilter(Color.parseColor("#2D5BFF"))
            } else {
                nameAmenity.setTextColor(Color.parseColor("#000000"))
                imageIcon.setColorFilter(Color.parseColor("#000000"))
            }

            itemView.setOnClickListener {
                onClickAmenity.onClickItem(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmenityHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_amenity, parent, false)
        return AmenityHolder(view)
    }

    override fun onBindViewHolder(holder: AmenityHolder, position: Int) {
        val item = getItem(position)
        holder.bin(item)
    }

    fun listSelected(): List<Amenity> {
        return currentList.filter { it.isSelected }
    }
}

 interface OnClick{
    fun onClickItem(postion: Int)
}