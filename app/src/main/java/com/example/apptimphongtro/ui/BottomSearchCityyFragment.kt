package com.example.apptimphongtro.ui

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.SearchCityAdapter
import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.data.repository.SearchRepository
import com.example.apptimphongtro.viewmodel.SearchViewModel
import com.google.android.material.R as MaterialR
import com.example.apptimphongtro.viewmodel.factory.SearchViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSearchCityyFragment : BottomSheetDialogFragment() {
  private lateinit var rvCity: RecyclerView
  private lateinit var searchRepository: SearchRepository
  private lateinit var searchCityAdapter: SearchCityAdapter
  private lateinit var searchViewModel: SearchViewModel
  private lateinit var searchViewModelFactory: SearchViewModelFactory
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

        searchViewModel.selectedCity.observe(viewLifecycleOwner){city->
            searchCityAdapter.updateSelectCity(city)
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

        val searchApi= RetrofitClient.searchApiService
        searchRepository= SearchRepository(searchApi)
        searchViewModelFactory= SearchViewModelFactory(searchRepository)
        searchViewModel= ViewModelProvider(requireActivity(),searchViewModelFactory)[SearchViewModel::class.java]
        searchViewModel.fetchListCityRoomCount()
    }


}