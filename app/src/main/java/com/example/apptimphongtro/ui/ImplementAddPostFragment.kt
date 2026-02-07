package com.example.apptimphongtro.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.AddPostAdapter
import com.example.apptimphongtro.databinding.FragmentImplementAddPostBinding


class ImplementAddPostFragment : Fragment() {
    private var _binding: FragmentImplementAddPostBinding?=null
    val binding get()= _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentImplementAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addEvent()
        val addPostAdapter= AddPostAdapter(this)
        binding.viewPager.adapter= addPostAdapter

    }

    private fun addEvent() {
        binding.viewPager.isUserInputEnabled=false

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateStatusView(position)
            }
        })

    }
    fun nextStep(){
        if(binding.viewPager.currentItem<2)
            binding.viewPager.currentItem +=1
    }
    fun preStep(){
        if(binding.viewPager.currentItem>0)
            binding.viewPager.currentItem -=1
    }

    private fun updateStatusView(pos: Int){
        val colorBacground= Color.WHITE
        val inactiveColor= Color.parseColor("#80FFFFFF")
        binding.view1.setBackgroundColor(colorBacground)
        binding.view2.setBackgroundColor(if(pos>=1) colorBacground else inactiveColor)
        binding.view3.setBackgroundColor(if (pos>=2) colorBacground else inactiveColor)
    }

}