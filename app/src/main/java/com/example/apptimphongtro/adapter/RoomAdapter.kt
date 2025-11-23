package com.example.apptimphongtro.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.diffcallback.DiffCallBackRoom
import com.example.apptimphongtro.model.RentalRoom
import com.example.apptimphongtro.util.FormatMoney
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class RoomAdapter: ListAdapter<RentalRoom, RoomAdapter.RoomViewHolder>(DiffCallBackRoom()){
    inner class RoomViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val title= itemView.findViewById<TextView>(R.id.txtItemTitle)
        private val ward= itemView.findViewById<TextView>(R.id.txtWard)
        private val city= itemView.findViewById<TextView>(R.id.txtCity)
        private val area= itemView.findViewById<TextView>(R.id.txtArea)
        private val price=itemView.findViewById<TextView>(R.id.txtPrice)
        private val image= itemView.findViewById<ShapeableImageView>(R.id.imgNhaTro)
        fun bin(room: RentalRoom){
            title.text= room.title
            ward.text= room.ward+","
            city.text=room.city
            area.text= room.area.toString()+"m²"
            price.text= FormatMoney().formatMoney(room.price*1000000)+"/tháng"

            val imgJson= room.imagesJson
            val listType= object : TypeToken<List<String>>(){}.type
            val imageList:List<String> =Gson().fromJson(imgJson,listType)

            Glide.with(itemView.context)
                .load(imageList[1])
                .placeholder(R.drawable.anhdefault)
                .error(R.drawable.anhdefault)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_itemphongtro,parent,false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
            val currentRoom= getItem(position)
        holder.bin(currentRoom)
    }
}
