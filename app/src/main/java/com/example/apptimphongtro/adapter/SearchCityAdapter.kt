package com.example.apptimphongtro.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.diffcallback.DiffCallSearchCity
import com.example.apptimphongtro.model.CityRoomCount

class SearchCityAdapter: ListAdapter<CityRoomCount, SearchCityAdapter.SearchViewHoder>(DiffCallSearchCity()) {

    inner class SearchViewHoder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val cityName=itemView.findViewById<TextView>(R.id.txtCityName)
        private val coutRoom=itemView.findViewById<TextView>(R.id.txtCountRoom)

        fun bin(cityRoomCount: CityRoomCount){
            cityName.text=cityRoomCount.city
            Log.d("SearchAdaper","laays duwojc thanh pho ${cityRoomCount.city} +${cityRoomCount.roomCount} so phongf")
            coutRoom.text=cityRoomCount.roomCount.toString()+" Phòng trọ"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHoder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_itemsearchcity,parent,false)
        return SearchViewHoder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHoder, position: Int) {
       val currentCity= getItem(position)
        holder.bin(currentCity)
    }
}