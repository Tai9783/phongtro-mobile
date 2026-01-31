package com.example.apptimphongtro.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.adapter.AddPostAdressCityAdapter
import com.example.apptimphongtro.adapter.OnClickItem
import com.example.apptimphongtro.databinding.FragmentBottomAddressBinding
import com.example.apptimphongtro.model.CityRoomCount
import com.example.apptimphongtro.viewmodel.AddPostAdressViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment



class BottomAddressFragment : BottomSheetDialogFragment() {
   private var _binding: FragmentBottomAddressBinding?=null
    private val binding get()=_binding!!
    private lateinit var listCity: List<CityRoomCount>
    private lateinit var addPostAdressCityAdapter: AddPostAdressCityAdapter
    private lateinit var addPostAdressViewModel:AddPostAdressViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentBottomAddressBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        dialog?.let{dlg->
            val bottomSheet=dlg.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height=(resources.displayMetrics.heightPixels*0.7).toInt()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControll()
        addEvent()

    }

    private fun addControll() {
        addPostAdressViewModel= ViewModelProvider(requireActivity())[AddPostAdressViewModel::class.java]
        listCity= listOf(
            CityRoomCount(1,"Tp Hồ Chí Minh"),
            CityRoomCount(2,"Hà Nội"),
            CityRoomCount(3,"Đà Nẵng"))
        addPostAdressViewModel.initCityList(listCity)
        addPostAdressCityAdapter= AddPostAdressCityAdapter(object : OnClickItem{
            override fun onclick(idCity: Int) {
                addPostAdressViewModel.updateItemClick(idCity)
                binding.rvCity.postDelayed({dismiss()},450)
            }
        })
    }

    private fun addEvent() {
        binding.rvCity.adapter= addPostAdressCityAdapter
        binding.rvCity.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        addPostAdressViewModel.allCity.observe(viewLifecycleOwner){newList->
                addPostAdressCityAdapter.submitList(newList)
        }

        binding.rvCity.addItemDecoration(object: RecyclerView.ItemDecoration(){
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom=35
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}