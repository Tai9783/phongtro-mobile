package com.example.apptimphongtro.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apptimphongtro.R
import com.example.apptimphongtro.databinding.FragmentStep2AddressBinding
import com.example.apptimphongtro.viewmodel.AddPostAdressViewModel


class Step2AddressFragment : Fragment() {
    private var _binding: FragmentStep2AddressBinding?=null
    private val binding get()=_binding!!
    private lateinit var addPostAdressViewModel: AddPostAdressViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentStep2AddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControll()
        addEvent()

    }

    private fun addControll() {
        addPostAdressViewModel= ViewModelProvider(requireActivity())[AddPostAdressViewModel::class.java]
    }

    private fun addEvent() {
        binding.txtProvince.setOnClickListener {
            val bottomSheet = BottomAddressFragment()
            bottomSheet.show(parentFragmentManager, "listCity")
        }
       addPostAdressViewModel.selectedCity.observe(viewLifecycleOwner){city->
           if (city!=null)
                binding.txtProvince.text=city.city
       }
    }
}