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
    cityNameSelect: String,
    private val oncitySelected: (String)-> Unit
): ListAdapter<CityRoomCount, SearchCityAdapter.SearchViewHoder>(DiffCallSearchCity()) {
    private var currentSelected= cityNameSelect

    inner class SearchViewHoder(itemView: View): RecyclerView.ViewHolder(itemView){
         val cityName: TextView =itemView.findViewById(R.id.txtCityName)
         val countRoom=itemView.findViewById<TextView>(R.id.txtCountRoom)
        val cvImgAddress: FrameLayout = itemView.findViewById(R.id.cvImgAddress)
        val imgLocation: ImageView= itemView.findViewById(R.id.imgLocation)

        fun bin(cityRoomCount: CityRoomCount){
            cityName.text=cityRoomCount.city
            Log.d("SearchAdaper","laays duwojc thanh pho ${cityRoomCount.city} +${cityRoomCount.roomCount} so phongf")
            countRoom.text=cityRoomCount.roomCount.toString()+" Phòng trọ"
        }
        fun updateSelectState(isSelected: Boolean){
            imgLocation.isSelected= isSelected
            cvImgAddress.isSelected=isSelected
            itemView.isSelected= isSelected
            cityName.isSelected= isSelected
            countRoom.isSelected= isSelected

        }
    }
    fun updateSelectCity(city:String){
        val old= currentSelected
        currentSelected= city

        //update item cũ
        val oldIndex=  currentList.indexOfFirst { it.city==old }
        if(oldIndex !=-1) notifyItemChanged(oldIndex)

        //update item mới
        val newIndex= currentList.indexOfFirst { it.city==city }
        if (newIndex !=-1) notifyItemChanged(newIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHoder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_itemsearchcity,parent,false)
        return SearchViewHoder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHoder, position: Int) {

       val currentCity= getItem(position)
        holder.bin(currentCity)

        val isSelected = currentCity.city == currentSelected

        holder.updateSelectState(isSelected)

        val iconColor= if(isSelected){
            Color.WHITE
        } else {
            Color.parseColor("#666666")
        }
        holder.imgLocation.setColorFilter(iconColor)

        holder.itemView.setOnClickListener {
            updateSelectCity(currentCity.city)
            oncitySelected(currentCity.city)
        }

    }
}