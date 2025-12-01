package com.example.apptimphongtro.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptimphongtro.MainActivity
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.RoomAdapter
import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.data.repository.RoomRepository
import com.example.apptimphongtro.viewmodel.RoomViewModel
import com.example.apptimphongtro.viewmodel.factory.RoomViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var roomAdapter: RoomAdapter
    private lateinit var roomViewModel: RoomViewModel
    private lateinit var rvPhong: RecyclerView
    private lateinit var repository: RoomRepository
    private lateinit var roomViewModelFactory: RoomViewModelFactory
    private lateinit var filterPrice: TextView
    private lateinit var filterLocal: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            v.setPadding(0, 0, 0, 0)
            insets
        }


        addControll(view)
        getApiFilterLocationAndPrice()
        addEnvent()

    }

    private fun addEnvent() {
        roomViewModel.roomByPrice.observe(viewLifecycleOwner) { newList ->
            if (newList != null) {
                roomAdapter.submitList(newList)
            }
        }
        rvPhong.adapter = roomAdapter
        rvPhong.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvPhong.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = 40
            }
        })

        filterPrice.setOnClickListener {
            showPriceMenu(filterPrice)
        }
        filterLocal.setOnClickListener {
            showLocalMenu(filterLocal)
        }
    }

    private fun formatStringToMoney(money: String): List<Double> {
        val list = mutableListOf<Double>()
        val moneyy = money.trim()
        if (moneyy == "$0–2tr") {
            list.add(0.0)
            list.add(2000000.0)
        }
        if (moneyy == "$2–5tr") {
            list.add(2000000.0)
            list.add(5000000.0)
        }
        if (moneyy == "$5–8tr") {
            list.add(5000000.0)
            list.add(8000000.0)
        }
        if (moneyy == "Trên 8tr") {
            list.add(8000000.0)
            list.add(100000000.0)
        }
        if (moneyy == "Tất cả") {
            list.add(0.0)
            list.add(100000000.0)
        }

        return list

    }

    private fun getApiFilterLocationAndPrice() {
        val money = filterPrice.text.toString()
        val list = formatStringToMoney(money)
        roomViewModel.filterByPriceAndCity(list[0], list[1], filterLocal.text.toString())
    }

    private fun showLocalMenu(filterLocal: TextView) {
        val popupMenu = PopupMenu(context, filterLocal)
        popupMenu.menuInflater.inflate(R.menu.local_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.local_all -> {
                    filterLocal.text = menuItem.title.toString()
                    getApiFilterLocationAndPrice()
                    true
                }

                R.id.local_hanoi -> {
                    filterLocal.text = menuItem.title.toString()
                    getApiFilterLocationAndPrice()
                    true
                }

                R.id.local_danang -> {
                    filterLocal.text = menuItem.title.toString()
                    getApiFilterLocationAndPrice()
                    true
                }

                R.id.local_hcm -> {
                    filterLocal.text = menuItem.title.toString()
                    getApiFilterLocationAndPrice()
                    true
                }

                else -> false

            }

        }
        popupMenu.show()

    }


    private fun showPriceMenu(filterPrice: TextView) {
        val popupMenu = PopupMenu(context, filterPrice)
        popupMenu.menuInflater.inflate(R.menu.price_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.price_all -> {
                    filterPrice.text = menuItem.title
                    getApiFilterLocationAndPrice()
                    true
                }

                R.id.price_less_than_2 -> {
                    filterPrice.text = menuItem.title
                    getApiFilterLocationAndPrice()
                    true
                }

                R.id.price_2_to_5 -> {
                    filterPrice.text = menuItem.title
                    getApiFilterLocationAndPrice()
                    true
                }

                R.id.price_5_to_8 -> {
                    filterPrice.text = menuItem.title
                    getApiFilterLocationAndPrice()
                    true
                }

                R.id.price_more_than_8 -> {
                    filterPrice.text = menuItem.title
                    getApiFilterLocationAndPrice()

                    true
                }

                else -> false
            }

        }
        popupMenu.show()

    }

    private fun addControll(view: View?) {
        if (view != null) {
            rvPhong = view.findViewById(R.id.rvPhongTro)
            filterPrice = view.findViewById(R.id.filter_Price)
            filterLocal = view.findViewById(R.id.filter_Local)
        }
        roomAdapter = RoomAdapter()
        val apiServer = RetrofitClient.apiService
        repository = RoomRepository(apiServer)
        roomViewModelFactory = RoomViewModelFactory(repository)
        roomViewModel = ViewModelProvider(this, roomViewModelFactory)[RoomViewModel::class.java]
        roomViewModel.fetchPhongNoiBat()

    }


}