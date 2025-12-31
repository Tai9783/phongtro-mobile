package com.example.apptimphongtro.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.apptimphongtro.R
import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.data.repository.SearchRepository
import com.example.apptimphongtro.viewmodel.SearchViewModel
import com.example.apptimphongtro.viewmodel.factory.SearchViewModelFactory
import com.google.android.material.chip.Chip


class SearchFragment : Fragment() {
    private lateinit var txtCity: TextView
    private lateinit var txtWard: TextView
    private lateinit var selectedCity: String
    private lateinit var repository: SearchRepository
    private lateinit var searchViewModelFactory: SearchViewModelFactory
    private lateinit var viewModel: SearchViewModel
    private lateinit var chip1: Chip
    private lateinit var chip2: Chip
    private lateinit var chip3: Chip
    private lateinit var chip4: Chip
    private lateinit var chip5: Chip
    private lateinit var chip6: Chip
    private lateinit var chip7: Chip
    private lateinit var chip8: Chip
    private lateinit var chip9: Chip
    private lateinit var chip10: Chip
    private lateinit var chip11: Chip
    private lateinit var chip12: Chip
    private lateinit var chip13: Chip
    private lateinit var chip14: Chip
    private lateinit var chip15: Chip
    private lateinit var chip16: Chip
    private lateinit var chip17: Chip
    private lateinit var chip18: Chip
    private lateinit var listPrice: List<Chip>
    private lateinit var listAmenity: List<Chip>
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
            bottomSheet.show(parentFragmentManager,"WardBottomSheet")
        }
        listPrice.forEach {chip->
            chip.setOnClickListener {
                handlePriceSelected()
                chip.isSelected= chip.isChecked
            }
        }
        listAmenity.forEach {chip->
            chip.setOnClickListener {
                handleAmenitySelected()
                chip.isSelected= chip.isChecked
            }
        }

        viewModel.selectedPrice.observe(viewLifecycleOwner){listPrice->
            listPrice.also {
                Log.d("SearchFragmentnnn","ds chonj $it")
            }
        }
        viewModel.selectedAmenity.observe(viewLifecycleOwner){listAmenity->
            listAmenity.also {
                Log.d("SearchFragmentnnn","ds chonj Amenity $it")
            }
        }


    }

    private fun handleAmenitySelected() {
        val listAmenitySelected= mutableSetOf<String>()
        listAmenity.forEach {
            if(it.isChecked){
                listAmenitySelected.add(it.tag.toString().trim())
            }

        }
        viewModel.updateSelectedAmenity(listAmenitySelected)
    }


    private fun handlePriceSelected() {
        val listIdPrice= mutableSetOf<String>()
        listPrice.forEach {
            if(it.isChecked){
               listIdPrice.add(it.tag.toString().trim())
            }

        }
        viewModel.updateSelectedPrice(listIdPrice)


    }


    private fun addControll(view: View) {
        txtCity = view.findViewById(R.id.txtCity)
        txtWard= view.findViewById(R.id.txtWard)
        chip1=view.findViewById(R.id.chip1)
        chip2=view.findViewById(R.id.chip2)
        chip3=view.findViewById(R.id.chip3)
        chip4=view.findViewById(R.id.chip4)
        chip5=view.findViewById(R.id.chip5)
        chip6=view.findViewById(R.id.chip6)
        chip7=view.findViewById(R.id.chip7)
        chip8=view.findViewById(R.id.chip8)
        chip9=view.findViewById(R.id.chip9)
        chip10=view.findViewById(R.id.chip10)
        chip11=view.findViewById(R.id.chip11)
        chip12=view.findViewById(R.id.chip12)
        chip13=view.findViewById(R.id.chip13)
        chip14=view.findViewById(R.id.chip14)
        chip15=view.findViewById(R.id.chip15)
        chip16=view.findViewById(R.id.chip16)
        chip17=view.findViewById(R.id.chip17)
        chip18=view.findViewById(R.id.chip18)
        listPrice= mutableListOf(chip1,chip2,chip3,chip4,chip5,chip6,chip7,chip8,chip9)
        listAmenity= mutableListOf(chip10,chip11,chip12,chip13,chip14,chip15,chip16,chip17,chip18)

        val apiServer= RetrofitClient.searchApiService
        repository= SearchRepository(apiServer)
        searchViewModelFactory= SearchViewModelFactory(repository)
        viewModel= ViewModelProvider(requireActivity(),searchViewModelFactory)[SearchViewModel::class.java]

    }



}