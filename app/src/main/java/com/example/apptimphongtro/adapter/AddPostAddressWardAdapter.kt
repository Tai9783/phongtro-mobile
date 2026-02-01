package com.example.apptimphongtro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.diffcallback.DiffCallBackSearchWard
import com.example.apptimphongtro.model.Ward

class AddPostAddressWardAdapter(private val onClick: OnClickItemWard): ListAdapter<Ward, AddPostAddressWardAdapter.AddressWardViewHolder>(
    DiffCallBackSearchWard()
) {
    inner class AddressWardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val nameCity: TextView = itemView.findViewById(R.id.txtCityName)
        private val constraintCity: ConstraintLayout =itemView.findViewById(R.id.constraintCity)
        fun bin(item: Ward){
            nameCity.text=item.wardName
            constraintCity.isSelected= item.isCheck
            nameCity.isSelected=item.isCheck

           itemView.setOnClickListener {
                onClick.onclick(item.wardName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressWardViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_city_addpost,parent,false)
        return AddressWardViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressWardViewHolder, position: Int) {
        val item= getItem(position)
        holder.bin(item)
    }
    interface OnClickItemWard{
        fun onclick(wardName: String)
    }
}