package com.example.apptimphongtro.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addControll(view)
        addEnvent()
    }
    private fun addEnvent() {
        roomViewModel._phongNoiBat.observe(viewLifecycleOwner){newList->
            Log.d("Home","ddang laays duw lieu ${newList[0].title}")
            if (newList!=null){
                roomAdapter.submitList(newList)
                Log.d("Home","Lay thanh cong")
            }
        }
        rvPhong.adapter= roomAdapter
        rvPhong.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
    }

    private fun addControll(view: View?) {
        if (view != null) {
            rvPhong= view.findViewById(R.id.rvPhongTro)
        }
        roomAdapter= RoomAdapter()
        val apiServer= RetrofitClient.apiService
        repository= RoomRepository(apiServer)
        roomViewModelFactory= RoomViewModelFactory(repository)
        roomViewModel= ViewModelProvider(this,roomViewModelFactory)[RoomViewModel::class.java]
        roomViewModel.fetchPhongNoiBat()
    }


}