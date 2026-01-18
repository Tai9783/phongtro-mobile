package com.example.apptimphongtro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.diffcallback.DiffCallBackSearchRoom
import com.example.apptimphongtro.model.RentalRoom
import com.example.apptimphongtro.util.FormatMoney
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ResultSearchAdapter : ListAdapter<RentalRoom,ResultSearchAdapter.SearchViewHolder>(
    DiffCallBackSearchRoom()
) {

    inner class SearchViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        private val title= itemView.findViewById<TextView>(R.id.txtTitle)
        private val ward= itemView.findViewById<TextView>(R.id.txtWard)
        private val city= itemView.findViewById<TextView>(R.id.txtCity)
        private val price=itemView.findViewById<TextView>(R.id.txtPrice)
        private val image= itemView.findViewById<ShapeableImageView>(R.id.imgRoom)
        private val chipGroup= itemView.findViewById<ChipGroup>(R.id.chipGroup)

        fun bin(room: RentalRoom){
            title.text= room.title
            ward.text= room.ward+","
            city.text=room.city
            price.text= FormatMoney().formatMoney(room.price)+"/tháng"

            val imgJson= room.imagesJson
            val listType= object : TypeToken<List<String>>(){}.type
            val imageList:List<String> = Gson().fromJson(imgJson,listType)

            Glide.with(itemView.context)
                .load(imageList[0])
                .placeholder(R.drawable.anhdefault)
                .error(R.drawable.anhdefault)
                .into(image)

            chipGroup.removeAllViews()
            val maxChips=3
            val list= room.amenities
            list.take(maxChips).forEach {text->
                val chip=com.google.android.material.chip.Chip(itemView.context).apply {
                    this.text= text.amenityName
                    isClickable=false
                    isChecked=false
                    isFocusable=false

                    setEnsureMinTouchTargetSize(false)
                    minHeight= dp(20)
                    textSize=10f
                    chipStartPadding = dpF(8)
                    chipEndPadding   = dpF(8)
                    textStartPadding = dpF(0)
                    textEndPadding   = dpF(0)
                    minimumWidth = 0
                    maxWidth = Int.MAX_VALUE
                    ellipsize = android.text.TextUtils.TruncateAt.END
                }
                chipGroup.addView(chip)
            }

        }
        private fun dp(v: Int) = (v * itemView.resources.displayMetrics.density).toInt()
        private fun dpF(v: Int) = v * itemView.resources.displayMetrics.density
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
       val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_item_room,parent,false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
       val currentRoom= getItem(position)
        holder.bin(currentRoom)
    }
}