package com.example.apptimphongtro.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.apptimphongtro.R
import com.example.apptimphongtro.databinding.FragmentStep3ImageBinding
import com.google.android.material.imageview.ShapeableImageView


class Step3ImageFragment : Fragment() {
    private var _binding: FragmentStep3ImageBinding?=null
    private val binding get()= _binding!!
    //Khai báo chọn bộ ảnh
    private var pickMedia= registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)){ uris->
        val currentCount= binding.layoutImageContainer.childCount
        val maxCount=10
        val limitPhoto= maxCount- currentCount
        if (limitPhoto<uris.size)
            Toast.makeText(requireContext(),"Chỉ lấy thêm $limitPhoto ảnh vì đã vượt số lượng cho phép",Toast.LENGTH_SHORT).show()

        if(uris.isNotEmpty()){
            uris.take(limitPhoto).forEach {uri-> // chỉ lấy số lượng còn thiếu cho đủ 10 ảnh
                addNewImageToLayout(uri)
            }
        }
        else{
            Log.d("Photo","Người dùng không chọn ảnh nào!!")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentStep3ImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControll()
        addEvent()
        refreshImage()
    }

    private fun addControll() {
    }

    private fun addNewImageToLayout(uri: Uri) {
        val inflater= LayoutInflater.from(requireContext())
        val itemView= inflater.inflate(R.layout.layout_item_image,binding.layoutImageContainer,false)

        val imgRoom= itemView.findViewById<ShapeableImageView>(R.id.imgRoom)
        val icClose= itemView.findViewById<ImageView>(R.id.icClose)

        Glide.with(this).load(uri).into(imgRoom)
        binding.layoutImageContainer.addView(itemView)

        refreshImage()
        icClose.setOnClickListener {
            binding.layoutImageContainer.removeView(itemView)
           refreshImage(    )
        }
    }

    private fun refreshImage() {
        val countRoom= binding.layoutImageContainer.childCount// đếm số ảnh hiện tại trong layout
        binding.txtCountRoom.text= getString(R.string.step3Image_txtCountImage,countRoom)
        if (countRoom< 3){
           //Nếu dưới 3 ảnh thì ẩn không cho nhấn tiếp tục
            binding.btnContinue.alpha= 0.5f
        }else{
            binding.btnContinue.alpha= 1.0f
        }
        for(i in 0 until binding.layoutImageContainer.childCount){
            val itemView= binding.layoutImageContainer.getChildAt(i)// lấy tấm ảnh kế bên của tấm ảnh bìa ban đầu(nếu đã có hành động xóa)
            val txtAnhBia= itemView.findViewById<TextView>(R.id.txtAnhBia)// sau khi lấy được tấm ảnh đó thì dựa vào đó và lấy cái txtAnhBia của ảnh đó để visibilty
            txtAnhBia.visibility= if (i==0) View.VISIBLE else View.GONE
        }

    }

    private fun addEvent() {
        binding.ctAddImage.setOnClickListener {
            val currentCount= binding.layoutImageContainer.childCount
            val maxCount=10
            if (currentCount>=maxCount){
                Toast.makeText(requireContext(),"Đã đủ 10 ảnh, bạn không thể chọn thêm!",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //Nếu ít hơn giới hạn thì cho phép mở bộ sưu tập
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnContinue.setOnClickListener {
            val countRoom= binding.layoutImageContainer.childCount
            if (countRoom<3)
                Toast.makeText(requireContext(),"Vui lòng tải ít nhất 3 ảnh",Toast.LENGTH_SHORT).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }


}