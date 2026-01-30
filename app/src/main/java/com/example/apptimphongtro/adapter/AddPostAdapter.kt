package com.example.apptimphongtro.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.apptimphongtro.ui.AddPostFragment
import com.example.apptimphongtro.ui.Step1InforFragment
import com.example.apptimphongtro.ui.Step2AddressFragment
import com.example.apptimphongtro.ui.Step3ImageFragment

class AddPostAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return  when (position){
            0-> Step1InforFragment()
            1-> Step2AddressFragment()
             else-> Step3ImageFragment()
        }
    }
}