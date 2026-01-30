package com.example.apptimphongtro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.model.Amenity

class AmenityAdapter(private val list: List<Amenity>) : RecyclerView.Adapter<AmenityAdapter.AmenityHolder>() {
    inner class AmenityHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageIcon: ImageView= itemView.findViewById(R.id.img_icon)
        val nameAmenity: TextView=itemView.findViewById(R.id.tv_name)
        val layoutContraint: LinearLayout= itemView.findViewById(R.id.layout_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmenityHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_item_amenity,parent,false)
        return AmenityHolder(view)
    }

    override fun onBindViewHolder(holder: AmenityHolder, position: Int) {
        val item= list[position]
        holder.nameAmenity.text= item.amenityName
        holder.imageIcon.setImageResource(item.icon)
    }

    override fun getItemCount(): Int {
       return list.size
    }
}