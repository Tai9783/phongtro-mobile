package com.example.apptimphongtro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.apptimphongtro.R
import com.example.apptimphongtro.model.SortType
import com.example.apptimphongtro.util.InitSearchViewModel
import com.example.apptimphongtro.viewmodel.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSortFragment : BottomSheetDialogFragment() {
    private lateinit var sortByNearest: ConstraintLayout
    private lateinit var sortByPriceAsc: ConstraintLayout
    private lateinit var sortByPriceDesc: ConstraintLayout
    private lateinit var sortByNewest: ConstraintLayout
    private lateinit var listItemSort: List<SortItem>
    private lateinit var iconCheck1: ImageView
    private lateinit var iconCheck2: ImageView
    private lateinit var iconCheck3: ImageView
    private lateinit var iconCheck4: ImageView
    private val searchViewModel: SearchViewModel by activityViewModels {
        InitSearchViewModel.factory
    }

    private data class SortItem(
        val type: SortType,
        val container: ConstraintLayout,
        val check: ImageView
    )



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControll(view)
        addEvent()

    }
    override fun onStart() {
        super.onStart()
        dialog?.let{dlg->
            val bottomSheet= dlg.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                it.layoutParams?.height= (resources.displayMetrics.heightPixels*0.6).toInt()
                it.background=null
            }

        }
    }


    private fun addEvent() {
        searchViewModel.selectedSort.observe(viewLifecycleOwner){item->
            if (!item.isNullOrEmpty()) {
                listItemSort.forEach { itemSort ->
                    if (itemSort.type.toString() == item)
                        updateStatus(itemSort)
                }
            }
        }

        listItemSort.forEach {item->
            item.container.setOnClickListener {
                select(item)

            }
        }

    }
    private fun updateStatus(item: SortItem){
        listItemSort.forEach { itemSort->
            val isCheck= itemSort== item
            itemSort.check.isVisible= isCheck //Hiển thị icon check
            itemSort.container.isSelected=isCheck //highlight item
        }
    }
   private fun select(itemClick : SortItem){
       searchViewModel.updateTypeSort(itemClick.type.toString())
       updateStatus(itemClick)
        // delay lại hành động dismiss để ng dùng có thể thấy được item highlight và biết chắc được mình chọn item nào
       itemClick.container.postDelayed({
           if(isAdded){ dismiss() }// isAdded cho biết fragment hiẹn tại này có được gắn chặt vơí activity nào hay không
                                    // nếu có thì mới dismiss tránh trường hợp fragment này bị hủy trc khi dismiss người dùng back
                                    // lúc này nêú ko ktra thì hệ thống cố dismiss 1 fragment chết và gây crash app
       },150)

    }

    private fun addControll(view: View) {
        sortByNearest= view.findViewById(R.id.sortByNearest)
        sortByPriceAsc= view.findViewById(R.id.sortByPriceAsc)
        sortByPriceDesc= view.findViewById(R.id.sortByPriceDesc)
        sortByNewest= view.findViewById(R.id.sortByNewest)
        iconCheck1= view.findViewById(R.id.iconCheck1)
        iconCheck2= view.findViewById(R.id.iconCheck2)
        iconCheck3= view.findViewById(R.id.iconCheck3)
        iconCheck4= view.findViewById(R.id.iconCheck4)
        listItemSort= listOf(SortItem(SortType.NEAREST,sortByNearest,iconCheck1),
            SortItem(SortType.PRICE_ASC,sortByPriceAsc,iconCheck2),
            SortItem(SortType.PRICE_DESC, sortByPriceDesc,iconCheck3),
            SortItem(SortType.NEWEST,sortByNewest,iconCheck4))
    }


}