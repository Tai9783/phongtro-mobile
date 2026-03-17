package com.example.apptimphongtro.ui

import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.apptimphongtro.R
import com.example.apptimphongtro.data.api.CloudinaryUploadService
import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.data.api.RetrofitClient.cloudinaryUploadService
import com.example.apptimphongtro.data.repository.CloudinaryRepository
import com.example.apptimphongtro.databinding.FragmentStep3ImageBinding
import com.example.apptimphongtro.model.CloudinarySignatureResponse
import com.example.apptimphongtro.viewmodel.AddPostViewModel
import com.example.apptimphongtro.viewmodel.CloudinaryViewModel
import com.example.apptimphongtro.viewmodel.factory.CloudinaryViewModelFactory
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody


class Step3ImageFragment : Fragment() {
    private var _binding: FragmentStep3ImageBinding?=null
    private val binding get()= _binding!!
    private lateinit var addPostViewModel: AddPostViewModel
    private lateinit var cloudinaryViewModel: CloudinaryViewModel
    private lateinit var cloudinaryRepository: CloudinaryRepository
    private lateinit var cloudinaryViewModelFactory: CloudinaryViewModelFactory
    private lateinit var currentCloudinary: CloudinarySignatureResponse

    //Khai báo chọn bộ ảnh
    private var pickMedia= registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)){ uris->
        val currentCount= binding.layoutImageContainer.childCount
        val maxCount=10
        val limitPhoto= maxCount- currentCount
        if (limitPhoto<uris.size)
            Toast.makeText(requireContext(),"Chỉ lấy thêm $limitPhoto ảnh vì đã vượt số lượng cho phép",Toast.LENGTH_SHORT).show()

        if(uris.isNotEmpty()){
            val limitedUris= uris.take(limitPhoto)
            limitedUris.forEach {uri-> // chỉ lấy số lượng còn thiếu cho đủ 10 ảnh
                addNewImageToLayout(uri)
            }
            addPostViewModel.addImage(limitedUris)
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
        cloudinaryViewModel.clodinary.observe(viewLifecycleOwner){cloudinary->
            Log.d("STEP3","CHU KY LA $cloudinary")
            currentCloudinary= cloudinary
        }

    }

    private fun addControll() {
        addPostViewModel= ViewModelProvider(requireActivity())[AddPostViewModel::class.java]
        val apiService= RetrofitClient.cloudinaryApiService
        cloudinaryRepository= CloudinaryRepository(apiService)
        cloudinaryViewModelFactory= CloudinaryViewModelFactory(cloudinaryRepository)
        cloudinaryViewModel= ViewModelProvider(requireActivity(),cloudinaryViewModelFactory)[CloudinaryViewModel::class.java]
        cloudinaryViewModel.getCloudinarySignature()

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
           refreshImage()
            addPostViewModel.removeImage(uri)
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
    //convert uri to MultipartBody
    private fun uriToMultipart(context: Context,uri: Uri ): MultipartBody.Part{
        val inputStream= context.contentResolver.openInputStream(uri)
        val bitmap= android.graphics.BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
        val outputStream= java.io.ByteArrayOutputStream()
        // Nén ảnh
        bitmap.compress(
            android.graphics.Bitmap.CompressFormat.JPEG,
            70,
            outputStream
        )
        val compressByte= outputStream.toByteArray()
        val requestFile= compressByte.toRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file","image.jpg", requestFile)
    }
    private fun String.toPlain(): RequestBody {
        return this.toRequestBody("text/plain".toMediaType())
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
            else{
                val uploadUrls= mutableListOf<String>()
                val listUri= addPostViewModel.allImage.value
                Log.d("List uri nhan duoc la: ","$listUri")
                    val cloudinary= currentCloudinary
                        viewLifecycleOwner.lifecycleScope.launch {
                        if(listUri!=null){
                            for(uri in listUri){
                                val part= context?.let { uriToMultipart(it,uri) }
                                Log.d("Part cua tung anh la","$part")
                                if (part != null) {
                                    val response = cloudinaryUploadService.uploadImage(
                                        cloudName = cloudinary.cloudName,
                                        file = part,
                                        apiKey = cloudinary.apiKey.toPlain(),
                                        signature = cloudinary.signature.toPlain(),
                                        timestamp = cloudinary.timestamp.toString().toPlain()
                                    )
                                    uploadUrls.add(response.secure_url)
                                }
                            }
                        }


                        Log.d("STEP3","DS CAC LINK URL LA $uploadUrls")
                }


            }
        }
        binding.btnQuaylai.setOnClickListener {
            val parent= parentFragment as? ImplementAddPostFragment
            parent?.preStep()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }


}