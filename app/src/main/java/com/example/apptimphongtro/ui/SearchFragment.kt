package com.example.apptimphongtro.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apptimphongtro.R
import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.data.repository.RoomRepository
import com.example.apptimphongtro.data.repository.SearchRepository
import com.example.apptimphongtro.model.Ward
import com.example.apptimphongtro.viewmodel.SearchViewModel
import com.example.apptimphongtro.viewmodel.factory.SearchViewModelFactory


class SearchFragment : Fragment() {
    private lateinit var txtCity: TextView
    private lateinit var txtWard: TextView
    private lateinit var selectedCity: String
    private lateinit var repository: SearchRepository
    private lateinit var searchViewModelFactory: SearchViewModelFactory
    private lateinit var viewModel: SearchViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControll(view)
        addEvent()
    }

    private fun addEvent() {
        viewModel.selectedCityName.observe(viewLifecycleOwner){cityName->
            cityName?.let {
                if(it.isNotEmpty()) {
                    txtCity.text = it
                    txtWard.text=getString(R.string.search_Ward)
                }
            }
        }
        viewModel.selectedWard.observe(viewLifecycleOwner){listWard->
            listWard.let { it ->
                if(it.isNotEmpty())
                    txtWard.text= it.joinToString(separator = ", ") { it.wardName }
                else{
                    txtWard.text=getString(R.string.search_Ward)
                }
            }

        }

        txtCity.setOnClickListener {
            val bottomSheet= BottomSearchCityyFragment().apply {
                selectedCity= viewModel.selectedCityName.value!!
                arguments= Bundle().apply {
                    putString("citySelected",selectedCity)
                }
            }
            fragmentManager?.let { bottomSheet.show(it,bottomSheet.tag) }

        }

        txtWard.setOnClickListener {
            val cityName= txtCity.text.toString()
            val bottomSheet= BottomSearchWardFragment().apply {
                arguments= Bundle().apply {
                    putString("city",cityName)

                }
            }
            viewModel.selectedWard.observe(viewLifecycleOwner){lis->
                Log.e("SearchFragment","$lis");
            }
            bottomSheet.show(parentFragmentManager,"WardBottomSheet")
        }
    }


    private fun addControll(view: View) {
        txtCity = view.findViewById(R.id.txtCity)
        txtWard= view.findViewById(R.id.txtWard)

        val apiServer= RetrofitClient.searchApiService
        repository= SearchRepository(apiServer)
        searchViewModelFactory= SearchViewModelFactory(repository)
        viewModel= ViewModelProvider(requireActivity(),searchViewModelFactory)[SearchViewModel::class.java]

    }



}