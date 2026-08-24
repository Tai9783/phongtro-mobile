package com.example.apptimphongtro.feature.search.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.feature.search.viewmodel.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class   BottomSearchWardFragment : BottomSheetDialogFragment() {
    private lateinit var rvWard: RecyclerView
    private lateinit var searchWardAdapter: SearchWardAdapter
    private val searchViewModel: SearchViewModel  by viewModels()
    private lateinit var imgThoat: ImageView
    private lateinit var btnXacNhan: AppCompatButton

    override fun onStart() {
        super.onStart()
        dialog?.let {dlg->
            val bottomSheet= dlg.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height=(resources.displayMetrics.heightPixels*0.7).toInt()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_search_ward, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addControll(view)
        addEvent()
    }

    private fun addEvent() {
        searchViewModel.listWard.observe(viewLifecycleOwner){listWard->
            searchWardAdapter.submitList(listWard)
        }
        rvWard.adapter= searchWardAdapter
        rvWard.layoutManager= LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        rvWard.addItemDecoration(object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom=40
            }
        })

        imgThoat.setOnClickListener {
            dismiss()
        }

        btnXacNhan.setOnClickListener {
            val selectedWards= searchWardAdapter.getSelectedWard()
            searchViewModel.updateSelectedWard(selectedWards)
            dismiss()
        }
    }


    private fun addControll(view: View) {
        //Lấy dữ tên city bên Search truyền qua
        val cityName= arguments?.getString("city")?:""

       rvWard= view.findViewById(R.id.rvWard)
        imgThoat= view.findViewById(R.id.imgThoat)
        btnXacNhan= view.findViewById(R.id.btnXacNhan)

        searchWardAdapter= SearchWardAdapter()
       searchViewModel.fechListWardByCity(cityName)

    }

}