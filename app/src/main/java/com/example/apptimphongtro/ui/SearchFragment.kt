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
import androidx.navigation.fragment.findNavController
import com.example.apptimphongtro.R
import com.example.apptimphongtro.model.Ward


class SearchFragment : Fragment() {
    private lateinit var txtCity: TextView
    private lateinit var txtWard: TextView
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
        txtCity.setOnClickListener {
            val bottomSheet= BottomSearchCityyFragment()
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
    }

    private fun addControll(view: View) {
        txtCity = view.findViewById(R.id.txtCity)
        txtWard= view.findViewById(R.id.txtWard)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("key"){ _, bundle ->
            val selectStringCityName= bundle.getString("cityName")
            if (selectStringCityName!=null){
                    txtCity.text= selectStringCityName
            }
        }

        setFragmentResultListener("KeyWard"){ _, bundle ->
                val selectedWard: List<Ward>?= bundle.getParcelableArrayList("listWard")
            if (selectedWard != null) {
                Log.d("Saerch","Da nhan duwoj listWard $selectedWard")
                val listWard= selectedWard.map { it.wardName }.joinToString(separator = ", ")
                txtWard.text = listWard
            }
        }
    }


}