package com.example.apptimphongtro.feature.addpost.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

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