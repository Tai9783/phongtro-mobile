package com.example.apptimphongtro.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.diffcallback.DiffCallSearchCity
import com.example.apptimphongtro.model.CityRoomCount

class SearchCityAdapter(
    private val oncitySelected: (String)-> Unit
): ListAdapter<CityRoomCount, SearchCityAdapter.SearchViewHoder>(DiffCallSearchCity()) {
    private var currentSelected= ""

    inner class SearchViewHoder(itemView: View): RecyclerView.ViewHolder(itemView){
         val cityName: TextView =itemView.findViewById(R.id.txtCityName)
         val coutRoom=itemView.findViewById<TextView>(R.id.txtCountRoom)
        val cvImgAddress: FrameLayout = itemView.findViewById(R.id.cvImgAddress)
        val imgLocation: ImageView= itemView.findViewById(R.id.imgLocation)

        fun bin(cityRoomCount: CityRoomCount){
            cityName.text=cityRoomCount.city
            Log.d("SearchAdaper","laays duwojc thanh pho ${cityRoomCount.city} +${cityRoomCount.roomCount} so phongf")
            coutRoom.text=cityRoomCount.roomCount.toString()+" Phòng trọ"
        }
    }
    fun updateSelectCity(city:String){
        currentSelected= city
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHoder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_itemsearchcity,parent,false)
        return SearchViewHoder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHoder, position: Int) {
       val currentCity= getItem(position)
        holder.bin(currentCity)

        val isSelected = currentCity.city == currentSelected


        holder.imgLocation.isSelected= isSelected
        holder.cvImgAddress.isSelected=isSelected
        holder.itemView.isSelected= isSelected

        val iconColor= if(isSelected){
            Color.WHITE
        } else {
            Color.parseColor("#666666")
        }
        holder.imgLocation.setColorFilter(iconColor)

        holder.itemView.setOnClickListener {
            oncitySelected(currentCity.city)
        }
        holder.itemView.setBackgroundResource(R.drawable.item_city_selected)
        if(currentCity.city==currentSelected)
        {
            holder.cityName.setTextColor(Color.WHITE)
            holder.coutRoom.setTextColor(Color.WHITE)
        }
        else{
            holder.cityName.setTextColor(Color.BLACK) // Trả về màu mặc định
            holder.coutRoom.setTextColor(Color.BLACK) // Trả về màu mặc định

        }
    }
}