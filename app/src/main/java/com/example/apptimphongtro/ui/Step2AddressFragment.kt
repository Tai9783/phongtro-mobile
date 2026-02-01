package com.example.apptimphongtro.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            val key= "fragmentProvice"
            val bottomSheet = BottomAddressFragment().apply {
               arguments= Bundle().apply {
                   putString("keyy",key)
               }
            }
            bottomSheet.show(parentFragmentManager, "listCity")
        }
       addPostAdressViewModel.selectedCity.observe(viewLifecycleOwner){city->
           if (city!=null)
               binding.txtProvince.text = city.city
       }
        addPostAdressViewModel.selectedWard.observe(viewLifecycleOwner){ward->
            if(ward!=null){
                binding.txtWard.text= ward.wardName
            }
            else
                binding.txtWard.text= getString(R.string.step2Address_txtWard)
        }
        binding.txtWard.setOnClickListener {
            val selectCity= addPostAdressViewModel.selectedCity.value
            if (selectCity==null){
                Toast.makeText(requireContext(), "Vui lòng chọn Tỉnh/Thành phố trước", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val key= "fragmentWard"
            val bottomSheet = BottomAddressFragment().apply {
                arguments= Bundle().apply {
                    putString("keyy",key)
                }
            }
            bottomSheet.show(parentFragmentManager, "listWard")
        }
    }
}