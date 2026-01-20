package com.example.apptimphongtro.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.ResultSearchAdapter
import com.example.apptimphongtro.util.InitSearchViewModel
import com.example.apptimphongtro.util.PriceRange
import com.example.apptimphongtro.util.parsePriceLabel
import com.example.apptimphongtro.viewmodel.SearchViewModel
import com.google.android.material.chip.Chip


class ResultSearchFragment : Fragment() {
    private lateinit var txtNameCity: TextView
    private lateinit var lienket: View
    private lateinit var lienket2: View
    private lateinit var txtNameWard: TextView
    private lateinit var txtPrice: TextView
    private lateinit var txtAmenity: TextView
    private lateinit var lvRoom: RecyclerView
    private lateinit var resultSearchAdapter: ResultSearchAdapter
    private lateinit var txtResultQuantity: TextView
    private lateinit var layoutInformation: ConstraintLayout
    private lateinit var progress: ProgressBar
    private lateinit var btnDieuChinh: AppCompatButton
    private lateinit var chipFilter: Chip
    private lateinit var chipSort: Chip
    //khởi tạo searchViewModel
    private val searchViewModel: SearchViewModel  by activityViewModels {
        InitSearchViewModel.factory
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_search, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControll(view)
        loadData()
        addEvent()

    }
    // Reset dữ liệu Sort(dữ liệu đã chọn ở BottomSortFragment) trong Livedate để người dùng chọn lại
    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving||activity?.isFinishing==true){
            searchViewModel.updateTypeSort("")
        }
        //isRemoving là biến gắn liền với fragment thể hiện sự status
        // nếu true là fragment đóng hoàn toàn còn false là chỉ tạm dừng để qua màn hình con của nó
        //isFinishing: là biến của activity chứa fragment đó nếu activity đóng lại thì trả về true, thì lúc này fragment này cũng bị déstroy và hủy dữ liệu
        // dấu ? giúp app tránh crash app nếu activity biến mất trc khi fragment dọn dẹp
    }


    private fun addEvent() {
        //back lại màn hình search khi tìm ko có kết quả
        btnDieuChinh.setOnClickListener {
           findNavController().popBackStack()
        }
        chipFilter.setOnClickListener {
            findNavController().popBackStack()
        }
        chipSort.setOnClickListener{
            val bottomSort= BottomSortFragment()
            bottomSort.show(parentFragmentManager,"BottomSortTag")
        }

        searchViewModel.selectedSort.observe(viewLifecycleOwner){type->
          if (!type.isNullOrEmpty()){
              val currentList= resultSearchAdapter.currentList
              if (currentList.isEmpty()) return@observe
              val sortList=  searchViewModel.getSortedList(currentList,type)
              resultSearchAdapter.submitList(sortList) // cập nhật lại cho adapter hiển thị lại
              lvRoom.scrollToPosition(0) //cuộn lên đầu để xem ds mới
          }
        }

    }

    private var lastKey: String? = null// key để chặn spam gọi api khi observe thay đổi
    private fun loadData() {
        var nameCity :String
        searchViewModel.filterState.observe(viewLifecycleOwner){state->
            nameCity=state.city
            txtNameCity.text=nameCity
            val wardNames = state.wards.map { it.wardName }
            if (wardNames.isEmpty()) {
                lienket.visibility = View.GONE
                txtNameWard.visibility = View.GONE
            } else {
                lienket.visibility = View.VISIBLE
                txtNameWard.visibility = View.VISIBLE
                txtNameWard.text = shortListWithDots(wardNames)
            }
            val listPrice: List<PriceRange> = state.prices.mapNotNull { parsePriceLabel(it) }
            if (state.prices.isEmpty()) {
                lienket2.visibility = View.GONE
                txtPrice.visibility = View.GONE
            } else {
                lienket2.visibility = View.VISIBLE
                txtPrice.visibility = View.VISIBLE
                txtPrice.text =  shortListWithDots(state.prices.toList())

            }
            val amenities = state.aminities.toList()
            if (amenities.isEmpty()){
                lienket2.visibility=View.GONE
                txtAmenity.visibility=View.GONE
            }else{
                txtAmenity.visibility=View.VISIBLE
                txtAmenity.text=shortListWithDots(amenities)
            }
            progress.visibility=View.VISIBLE
            val key= buildString {
                append(nameCity)
                append("w="); append(wardNames.joinToString(";"))
                append("p="); append(listPrice.joinToString(";"){"${it.min}-${it.max}"})
                append("a="); append(amenities.joinToString(";"))
            }
            //tránh spam gọi api trong observe
            if (key==lastKey) return@observe
            lastKey= key

            searchViewModel.fecthListRoomFilter(nameCity,wardNames,listPrice,amenities)
            searchViewModel.getListRoomFilter.observe(viewLifecycleOwner){listRoom->
                resultSearchAdapter.submitList(listRoom)
                val size=listRoom.size
                txtResultQuantity.text= size.toString()+" phòng trọ"
                if(size>0){
                    lvRoom.visibility= View.VISIBLE
                    layoutInformation.visibility= View.INVISIBLE
                }
                else {
                    lvRoom.visibility= View.INVISIBLE
                    layoutInformation.visibility= View.VISIBLE
                }
                progress.visibility=View.INVISIBLE
            }
            lvRoom.adapter= resultSearchAdapter
            lvRoom.layoutManager= LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            lvRoom.addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom=50
                }
            })
        }
    }
    private fun shortListWithDots(item: List<String>, maxCount: Int=2):String{
        val list= item.map { it.trim() }.filter { it.isNotBlank() }
        return when{
            list.isEmpty()->""
            list.size<=maxCount-> list.joinToString(", ")
            else -> list.take(maxCount).joinToString(", ")+" …"
        }
    }

    private fun addControll(view: View) {
        txtNameCity= view.findViewById(R.id.txtNameCity)
        txtNameWard= view.findViewById(R.id.txtNameWard)
        txtPrice=view.findViewById(R.id.txtPrice)
        txtAmenity= view.findViewById(R.id.txtAmenity)
        lienket= view.findViewById(R.id.lienket)
        lienket2= view.findViewById(R.id.lienket2)
        lvRoom= view.findViewById(R.id.lvRoom)
        layoutInformation= view.findViewById(R.id.layoutInformation)
        resultSearchAdapter= ResultSearchAdapter()
        txtResultQuantity= view.findViewById(R.id.txtResultQuantity)
        progress= view.findViewById(R.id.Progress)
        btnDieuChinh=view.findViewById(R.id.btnDieuChinh)
        chipFilter= view.findViewById(R.id.chipFilter)
        chipSort= view.findViewById(R.id.chipSortPrice)

    }

}