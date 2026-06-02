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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.apptimphongtro.R
import com.example.apptimphongtro.common.RoomPostUiState
import com.example.apptimphongtro.common.RoomUIState
import com.example.apptimphongtro.data.api.CloudinaryUploadService
import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.data.api.RetrofitClient.cloudinaryUploadService
import com.example.apptimphongtro.data.repository.CloudinaryRepository
import com.example.apptimphongtro.data.repository.RoomPostRepository
import com.example.apptimphongtro.data.repository.RoomRepository
import com.example.apptimphongtro.databinding.FragmentStep3ImageBinding
import com.example.apptimphongtro.model.dto.CloudinarySignatureResponse
import com.example.apptimphongtro.util.InitUserViewModel
import com.example.apptimphongtro.viewmodel.AddPostViewModel
import com.example.apptimphongtro.viewmodel.CloudinaryViewModel
import com.example.apptimphongtro.viewmodel.RoomPostViewModel
import com.example.apptimphongtro.viewmodel.RoomViewModel
import com.example.apptimphongtro.viewmodel.UserViewModel
import com.example.apptimphongtro.viewmodel.factory.CloudinaryViewModelFactory
import com.example.apptimphongtro.viewmodel.factory.RoomPostViewModelFactory
import com.example.apptimphongtro.viewmodel.factory.RoomViewModelFactory
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
    private lateinit var roomViewModel: RoomViewModel
    private lateinit var roomViewModelFactory: ViewModelProvider.Factory
    private lateinit var roomRepository: RoomRepository
    private  var loadingDialog: LoadingDialog?=null
    private lateinit var roomPostRepository: RoomPostRepository
    private lateinit var roomPostViewModel: RoomPostViewModel
    private lateinit var roomPostViewModelFactory: ViewModelProvider.Factory
    private val userViewModel: UserViewModel by activityViewModels {
        InitUserViewModel.factory
    }
    private lateinit var landlordId : String

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
            currentCloudinary= cloudinary
        }

        roomViewModel.createRoomId.observe(viewLifecycleOwner){idRoom->
            if(idRoom!=null)
                Log.d("STEP3 BUOC CUOI","ĐĂNG BÀI THÀNH CÔNG VÀ ID LÀ $idRoom")
        }

    }

    private fun addControll() {
        addPostViewModel= ViewModelProvider(requireActivity())[AddPostViewModel::class.java]
        val apiService= RetrofitClient.cloudinaryApiService
        cloudinaryRepository= CloudinaryRepository(apiService)
        cloudinaryViewModelFactory= CloudinaryViewModelFactory(cloudinaryRepository)
        cloudinaryViewModel= ViewModelProvider(requireActivity(),cloudinaryViewModelFactory)[CloudinaryViewModel::class.java]
        cloudinaryViewModel.getCloudinarySignature()
        val roomApi = RetrofitClient.roomApiService
        roomRepository= RoomRepository(roomApi)
        roomViewModelFactory= RoomViewModelFactory(roomRepository)
        roomViewModel= ViewModelProvider(this,roomViewModelFactory)[RoomViewModel::class.java]

        roomPostRepository= RoomPostRepository(RetrofitClient.roomPostApiService)
        roomPostViewModelFactory= RoomPostViewModelFactory(roomPostRepository)
        roomPostViewModel= ViewModelProvider(this,roomPostViewModelFactory)[RoomPostViewModel::class.java]

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
                if(loadingDialog==null)
                    loadingDialog= LoadingDialog()
                loadingDialog?.show(parentFragmentManager,"loading")

                val uploadUrls= mutableListOf<String>()
                val listUri= addPostViewModel.allImage.value
                    val cloudinary= currentCloudinary
                        viewLifecycleOwner.lifecycleScope.launch {
                            try{
                                if(listUri!=null){
                                    for(uri in listUri){
                                        val part= context?.let { uriToMultipart(it,uri) }
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
                                    landlordId= userViewModel.user.value?.userId.toString()
                                    addPostViewModel.updateStep3(landlordId,uploadUrls)

                                    val listImage= addPostViewModel.addPost.value
                                    val roomResquest= addPostViewModel.addPost.value
                                    if(roomResquest!=null && listImage!=null){
                                     //   roomViewModel.insertOrPostRoom(roomResquest)
                                        roomViewModel.saveRoom(roomResquest)
                                    }
                                }
                            }catch (e: Exception){
                                loadingDialog?.dismiss()
                                loadingDialog=null

                                val dialog= StatusDialog.newInstance(
                                    isSuccess = false,
                                    message = "Không có kết nối mạng. Vui lòng kiểm tra lại")
                                dialog.show(parentFragmentManager,"error_net")
                            }
                }
            }
        }
        roomViewModel.uiState.observe(viewLifecycleOwner){state->
            when(state){
                is RoomUIState.Idle->{
                  //  hideLoading()
                }
                is RoomUIState.Loading->{
                    if (loadingDialog == null) {
                        loadingDialog = LoadingDialog()
                    }
                    if (loadingDialog?.isVisible==false)
                            loadingDialog?.show(parentFragmentManager,"loading")
                }
                is RoomUIState.Success->{
                    loadingDialog?.dismiss()
                    loadingDialog=null
                    val dialog= StatusDialog.newInstance(
                        isSuccess = true,
                        message = "Phòng đã lưu hệ thống thành công")

                    dialog.onPrimaryClick={
                        val roomId = state.room.roomId
                        roomPostViewModel.saveRoomPost(roomId)
                        //reset lại RoomUIState
                        roomViewModel.reSetState()
                    }
                    dialog.onSecondaryClick={
                        dialog.dismiss()
                    }
                    dialog.show(parentFragmentManager,"success_dialog")
                }
                is RoomUIState.Error->{
                    loadingDialog?.dismiss()
                    loadingDialog=null

                    val dialog= StatusDialog.newInstance(
                        isSuccess = false,
                        message = state.message)
                    dialog.show(parentFragmentManager,"error_dialog")

                    dialog.onPrimaryClick={

                    }
                    dialog.onSecondaryClick={
                        dialog.dismiss()
                    }
                }
            }
        }
        roomPostViewModel.uiStateRoomPost.observe(viewLifecycleOwner){state ->
            when(state){
                is RoomPostUiState.Idle->{}
                is RoomPostUiState.Loading->{
                    if (loadingDialog == null) {
                        loadingDialog = LoadingDialog()
                    }
                    if (loadingDialog?.isVisible == false) {
                        loadingDialog?.show(parentFragmentManager, "loading")
                    }

                }
                is RoomPostUiState.Success->{
                    roomPostViewModel.resetStatePostRoom()
                    viewLifecycleOwner.lifecycleScope.launch {
                        kotlinx.coroutines.delay(700)
                        loadingDialog?.dismiss()
                        loadingDialog=null
                        val dialog= StatusDialog.newInstance(
                            isSuccess = true,
                            message = "Vui lòng chờ xét duyệt!"
                        )
                        dialog.onPrimaryClick={

                        }
                        dialog.onSecondaryClick={

                        }
                        dialog.show(parentFragmentManager,"success_dialog")
                    }





                }
                is RoomPostUiState.Error->{
                    roomPostViewModel.resetStatePostRoom()
                    viewLifecycleOwner.lifecycleScope.launch {
                        kotlinx.coroutines.delay(700)
                        loadingDialog?.dismiss()
                        loadingDialog=null
                        val dialog= StatusDialog.newInstance(
                            isSuccess = false,
                            message = "Lỗi đăng bài"
                        )
                        dialog.onPrimaryClick={

                        }
                        dialog.onSecondaryClick={

                        }
                        dialog.show(parentFragmentManager,"error_dialog")
                    }

                    Log.e("STEP3 BUOC CUOI","Lỗi đăng bài: ${state.message}")

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