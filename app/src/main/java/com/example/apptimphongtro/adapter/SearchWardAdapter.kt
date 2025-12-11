package com.example.apptimphongtro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.diffcallback.DiffCallBackSearchWard
import com.example.apptimphongtro.model.Ward
import com.google.android.material.checkbox.MaterialCheckBox

class SearchWardAdapter: ListAdapter<Ward,SearchWardAdapter.SearchWardHolder>(DiffCallBackSearchWard()) {

    inner class SearchWardHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val txtWard= itemView.findViewById<TextView>(R.id.txtWard)
        val checkbox: MaterialCheckBox = itemView.findViewById(R.id.checkbox)
        fun bin(ward: Ward){
            txtWard.text=ward.wardName

            val isChecked= ward.isCheck
            itemView.isSelected= isChecked
            txtWard.isSelected= isChecked
            checkbox.isChecked=isChecked
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchWardHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_itemsearch_ward,parent,false)
        return SearchWardHolder(view)
    }

    override fun onBindViewHolder(holder: SearchWardHolder, position: Int) {
      val item= getItem(position)
        holder.bin(item)

        holder.checkbox.isClickable= false
        holder.checkbox.isFocusable = false

        holder.itemView.setOnClickListener {
          toggleItem(position)
        }

    }

    private fun toggleItem(position: Int) {
        val currentList= currentList.toMutableList()
        val item= currentList[position]

        item.isCheck= !item.isCheck
        submitList(currentList){
            notifyDataSetChanged()
        }
    }
    fun getSelectedWard(): List<Ward>{
        return currentList.filter { it.isCheck  }
    }
}