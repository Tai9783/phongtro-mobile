package com.example.apptimphongtro.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.ResultSearchAdapter
import com.example.apptimphongtro.util.InitSearchViewModel
import com.example.apptimphongtro.util.PriceRange
import com.example.apptimphongtro.util.parsePriceLabel
import com.example.apptimphongtro.viewmodel.SearchViewModel



class ResultSearchFragment : Fragment() {
    private lateinit var txtNameCity: TextView
    private lateinit var lienket: View
    private lateinit var lienket2: View
    private lateinit var txtNameWard: TextView
    private lateinit var txtPrice: TextView
    private lateinit var txtAmenity: TextView
    private lateinit var lvRoom: RecyclerView
    private lateinit var resultSearchAdapter: ResultSearchAdapter
    private lateinit var txtResultQuantity: TextView
    //khởi tạo searchViewModel
    private val searchViewModel: SearchViewModel  by activityViewModels {
        InitSearchViewModel.factory
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControll(view)
        loadData()
    }
    private var lastKey: String? = null// key để chặn spam gọi api khi observe thay đổi
    private fun loadData() {
        var nameCity :String
        searchViewModel.filterState.observe(viewLifecycleOwner){state->
            nameCity=state.city
            txtNameCity.text=nameCity
            val wardNames = state.wards.map { it.wardName }
            if (wardNames.isEmpty()) {
                lienket.visibility = View.GONE
                txtNameWard.visibility = View.GONE
            } else {
                lienket.visibility = View.VISIBLE
                txtNameWard.visibility = View.VISIBLE
                txtNameWard.text = wardNames.joinToString(", ")
            }
            val listPrice: List<PriceRange> = state.prices.mapNotNull { parsePriceLabel(it) }
            if (state.prices.isEmpty()) {
                lienket2.visibility = View.GONE
                txtPrice.visibility = View.GONE
            } else {
                lienket2.visibility = View.VISIBLE
                txtPrice.visibility = View.VISIBLE
                txtPrice.text = state.prices.joinToString(", ")

            }
            val amenities = state.aminities.toList()
            if (amenities.isEmpty()){
                lienket2.visibility=View.GONE
                txtAmenity.visibility=View.GONE
            }else{
                txtAmenity.visibility=View.VISIBLE
                txtAmenity.text=amenities.joinToString(separator = ",")
            }
            val key= buildString {
                append(nameCity)
                append("w="); append(wardNames.joinToString(";"))
                append("p="); append(listPrice.joinToString(";"){"${it.min}-${it.max}"})
                append("a="); append(amenities.joinToString(";"))
            }
            if (key==lastKey) return@observe
            lastKey= key

            searchViewModel.fecthListRoomFilter(nameCity,wardNames,listPrice,amenities)
            searchViewModel.getListRoomFilter.observe(viewLifecycleOwner){listRoom->
                Log.d("kjshdf","hgasvd$listRoom")
                resultSearchAdapter.submitList(listRoom)
                txtResultQuantity.text= listRoom.size.toString()+" phòng trọ"
            }
            lvRoom.adapter= resultSearchAdapter
            lvRoom.layoutManager= LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            lvRoom.addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom=50
                }
            })
        }



    }

    private fun addControll(view: View) {
        txtNameCity= view.findViewById(R.id.txtNameCity)
        txtNameWard= view.findViewById(R.id.txtNameWard)
        txtPrice=view.findViewById(R.id.txtPrice)
        txtAmenity= view.findViewById(R.id.txtAmenity)
        lienket= view.findViewById(R.id.lienket)
        lienket2= view.findViewById(R.id.lienket2)
        lvRoom= view.findViewById(R.id.lvRoom)
        resultSearchAdapter= ResultSearchAdapter()
        txtResultQuantity= view.findViewById(R.id.txtResultQuantity)



    }

}