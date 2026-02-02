package com.example.apptimphongtro.ui

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.AddPostAddressWardAdapter
import com.example.apptimphongtro.adapter.AddPostAdressCityAdapter
import com.example.apptimphongtro.adapter.OnClickItem
import com.example.apptimphongtro.databinding.FragmentBottomAddressBinding
import com.example.apptimphongtro.model.CityRoomCount
import com.example.apptimphongtro.model.Ward
import com.example.apptimphongtro.viewmodel.AddPostAdressViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment



class BottomAddressFragment : BottomSheetDialogFragment() {
   private var _binding: FragmentBottomAddressBinding?=null
    private val binding get()=_binding!!
    private lateinit var listCity: List<CityRoomCount>
    private lateinit var addPostAdressCityAdapter: AddPostAdressCityAdapter
    private lateinit var addPostAddressWardAdapter: AddPostAddressWardAdapter
    private lateinit var addPostAdressViewModel:AddPostAdressViewModel
    private lateinit var keyContent: String
    private lateinit var listWard: List<Ward>
    private var restored = false

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
         keyContent= arguments?.getString("keyy")?:"" //key để phân biệt user chọn City hay Ward

        //khởi tạo các thành phần để hiển thị và giữ trangj thái của các item trong dsCity
            addPostAdressViewModel =
                ViewModelProvider(requireActivity())[AddPostAdressViewModel::class.java]
            listCity = listOf(
                CityRoomCount(1, "Tp Hồ Chí Minh"),
                CityRoomCount(2, "Hà Nội"),
                CityRoomCount(3, "Đà Nẵng")
            )
            addPostAdressViewModel.initCityList(listCity)
            addPostAdressCityAdapter = AddPostAdressCityAdapter(object : OnClickItem {
                override fun onclick(idCity: Int) {
                    addPostAdressViewModel.updateItemClick(idCity)
                    binding.rvAddress.postDelayed({ dismiss() }, 450)
                }
            })

        addPostAddressWardAdapter= AddPostAddressWardAdapter(object: AddPostAddressWardAdapter.OnClickItemWard {
            override fun onclick(wardName: String) {
                addPostAdressViewModel.updateItemWardClick(wardName)
                binding.rvAddress.postDelayed({dismiss()},450)
            }

        })

        addPostAdressViewModel.selectedCity.observe(viewLifecycleOwner){cityName->
            if (cityName!=null) {
                listWard = context?.let { getWardLocal(it, cityName.idCity) }!! // lấy ds Ward theo city
                addPostAdressViewModel.initWartList(listWard)//khởi tạo list ward và lưu ds vào livedata
            }
        }


    }

    private fun addEvent() {
        if (keyContent=="fragmentProvice") {
            binding.txtTitle.text= getString(R.string.searchChoice_City)
            binding.rvAddress.adapter = addPostAdressCityAdapter
            binding.rvAddress.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addPostAdressViewModel.allCity.observe(viewLifecycleOwner) { newList ->
                addPostAdressCityAdapter.submitList(newList)
            }
            binding.rvAddress.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = 35
                }
            })
        }
        if (keyContent=="fragmentWard"){
            binding.txtTitle.text= getString(R.string.searchBottomFragment_WardTitle)
            binding.rvAddress.adapter= addPostAddressWardAdapter
            binding.rvAddress.layoutManager= LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addPostAdressViewModel.allWard.observe(viewLifecycleOwner){dsWard->
                addPostAddressWardAdapter.submitList(dsWard){
                    if (!restored && keyContent == "fragmentWard") {
                        restored = true
                        binding.rvAddress.post {
                            val lm = binding.rvAddress.layoutManager as LinearLayoutManager
                            lm.scrollToPositionWithOffset(
                                addPostAdressViewModel.wardScrollPos,
                                addPostAdressViewModel.wardScrollOffset
                            )
                        }
                    }
                }
            }
            binding.rvAddress.addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom= 35
                }
            })
        }

    }

    private fun getWardLocal(context: Context, cityId: Int): List<Ward>{
        val resId= when(cityId){
            1->R.array.wards_hcm
            2->R.array.wards_hanoi
            3->R.array.wards_danang
            else->null
        }?: return emptyList()

        val list =context.resources.getStringArray(resId).toList()
        return list.map {nameWard-> Ward(wardName = nameWard) }
    }

    override fun onDestroyView() {
        val lm = binding.rvAddress.layoutManager as? LinearLayoutManager
        if (lm != null && keyContent == "fragmentWard") {
            val firstPos = lm.findFirstVisibleItemPosition()
            if (firstPos != RecyclerView.NO_POSITION) {
                val top = binding.rvAddress.getChildAt(0)?.top ?: 0
                addPostAdressViewModel.wardScrollPos = firstPos
                addPostAdressViewModel.wardScrollOffset = top
            }
        }

        super.onDestroyView()
        _binding=null
    }

}