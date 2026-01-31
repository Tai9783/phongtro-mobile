package com.example.apptimphongtro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.diffcallback.DiffCallBackAdressCityAddPost
import com.example.apptimphongtro.model.CityRoomCount

class AddPostAdressCityAdapter(private val onClick: OnClickItem): ListAdapter<CityRoomCount,AddPostAdressCityAdapter.AddressViewHolder>(
    DiffCallBackAdressCityAddPost()
) {
    inner  class AddressViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val nameCity: TextView = itemView.findViewById(R.id.txtCityName)
        private val constraintCity: ConstraintLayout=itemView.findViewById(R.id.constraintCity)
        fun bin(item: CityRoomCount){
            nameCity.text=item.city
            constraintCity.isSelected= item.isSelected
            nameCity.isSelected=item.isSelected

            itemView.setOnClickListener {
                onClick.onclick(item.idCity)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_city_addpost,parent,false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val item= getItem(position)
        holder.bin(item)
    }
}
interface OnClickItem{
    fun onclick(idCity: Int)
}