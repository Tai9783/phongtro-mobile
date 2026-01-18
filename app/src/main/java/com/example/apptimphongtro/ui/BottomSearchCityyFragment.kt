package com.example.apptimphongtro.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.SearchCityAdapter
import com.example.apptimphongtro.util.InitSearchViewModel
import com.example.apptimphongtro.viewmodel.SearchViewModel
import com.google.android.material.R as MaterialR
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSearchCityyFragment : BottomSheetDialogFragment() {
  private lateinit var rvCity: RecyclerView
  private lateinit var searchCityAdapter: SearchCityAdapter
  //khởi tạo searchViewModel
  private val searchViewModel: SearchViewModel  by activityViewModels {
      InitSearchViewModel.factory
  }
  private lateinit var imgThoat: ImageView
  private lateinit var btnXacNhan: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_search_cityy, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.let{dlg->
            val bottomSheet=dlg.findViewById<View>(MaterialR.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height=(resources.displayMetrics.heightPixels*0.7).toInt()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addControll(view)
        addEvent()
    }

    private fun addEvent() {
        searchViewModel.listCityRoomCount.observe(viewLifecycleOwner){newListCity->
            if (newListCity!=null) {
                searchCityAdapter.submitList(newListCity)
            }
            rvCity.adapter= searchCityAdapter
            rvCity.layoutManager= LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            rvCity.addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom=40
                }
            })

        }
        imgThoat.setOnClickListener {
            dismiss()
        }


        btnXacNhan.setOnClickListener {
            val city= searchViewModel.selectedCity.value
            val nameCityOld= searchViewModel.selectedCityName.value
            if (city!=nameCityOld){
                searchViewModel.updateSelectedCityName(city)
                searchViewModel.clearSelectedWard()
            }
            dismiss()

        }
    }


    private fun addControll(view: View) {
        val citySelected= arguments?.getString("citySelected")?:""



        rvCity= view.findViewById(R.id.rvCity)
        imgThoat=view.findViewById(R.id.imgThoat)
        btnXacNhan= view.findViewById(R.id.btnXacNhan)

        searchCityAdapter= SearchCityAdapter(citySelected){city->
            searchViewModel.selectedCity.value=city
        }
        searchViewModel.fetchListCityRoomCount()
    }


}