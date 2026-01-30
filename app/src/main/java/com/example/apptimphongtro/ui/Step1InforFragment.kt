package com.example.apptimphongtro.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.AmenityAdapter
import com.example.apptimphongtro.databinding.FragmentStep1InforBinding
import com.example.apptimphongtro.model.Amenity

class Step1InforFragment : Fragment() {
    private var _binding: FragmentStep1InforBinding?=null
    private val binding get()= _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       _binding= FragmentStep1InforBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAmenity()
    }

    private fun showAmenity() {

      val list= listOf(
            Amenity("1", "Wifi", R.drawable.icon_wifi),
          Amenity("2", "Có máy lạnh", R.drawable.icon_maylanh),
          Amenity("3", "Thang máy", R.drawable.icon_thangmay),
          Amenity("4", "Có gác", R.drawable.icon_stair),
          Amenity("5", "Giờ giấc tự do", R.drawable.icon_giogiactudo),
          Amenity("6", "Nuôi thú cưng", R.drawable.icon_pets),
          Amenity("7", "Có ban công", R.drawable.icon_bancong),
          Amenity("8", "Miễn phí gửi xe", R.drawable.icon_xemay),
          Amenity("9", "Có tủ lạnh", R.drawable.icon_tulanh)
      )
        val adapter= AmenityAdapter(list)
        binding.rvAmenity.adapter= adapter
        binding.rvAmenity.layoutManager= GridLayoutManager(context, 3)
    }


}