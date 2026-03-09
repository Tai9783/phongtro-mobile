package com.example.apptimphongtro.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.AddPostAdapter
import com.example.apptimphongtro.databinding.FragmentImplementAddPostBinding
import com.example.apptimphongtro.viewmodel.AddPostViewModel


class   ImplementAddPostFragment : Fragment() {
    private lateinit var addPostViewModel: AddPostViewModel
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

        addControll()
        addEvent()

    }

    private fun addControll() {
        addPostViewModel= ViewModelProvider(requireActivity())[AddPostViewModel::class.java]
        val addPostAdapter= AddPostAdapter(this)
        binding.viewPager.adapter= addPostAdapter

        addPostViewModel.currentStep.observe(viewLifecycleOwner){step->
            if (binding.viewPager.currentItem!=step){
                binding.viewPager.setCurrentItem(step,false)
            }
        }
    }


    private fun addEvent() {
        binding.viewPager.isUserInputEnabled=false

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateStatusView(position)
                addPostViewModel.setCurrentStep(position)//lưu lại vị trí trang môi khi đổi trang
            }
        })

    }
    fun nextStep(){
        val next=  binding.viewPager.currentItem +1
        if(next<3){
            binding.viewPager.post { // trường hợp khi màn hình map quay lại màn hình bước 2 thì lúc này ImplmentAddpostFragment bắt đầu dựng lại
                                     // và hệ thống đang bận nên lúc này để post là thêm chỗ yêu cầu này vào hàng đợi, khi hệ thống xử lý mọi
                                     // thứ xong thì sẽ làm tiếp yêu cầu này để tránh crash app khi hệ thống thực hiẹn các yêu cầu ko kịp đặc biệt trong viewpagse2
                addPostViewModel.setCurrentStep(next)
                binding.viewPager.currentItem= next
            }
        }
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