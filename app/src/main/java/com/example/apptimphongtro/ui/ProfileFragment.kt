package com.example.apptimphongtro.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apptimphongtro.R
import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.data.local.SharedPrefManager
import com.example.apptimphongtro.data.repository.UserRepository
import com.example.apptimphongtro.databinding.FragmentProfileBinding
import com.example.apptimphongtro.model.User
import com.example.apptimphongtro.viewmodel.UserViewModel
import com.example.apptimphongtro.viewmodel.factory.UserViewModelFactory


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding?=null
    private val binding get()= _binding!!
    private lateinit var userRepository: UserRepository
    private lateinit var userViewModelFactory: UserViewModelFactory
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPrefManager: SharedPrefManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControll(view)
        checkAutoLogin()
        addEnvent()
    }

    private fun checkAutoLogin() {
        val userId= sharedPrefManager.getUser()
        if(userId!=null){
            userViewModel.fetchUser(userId)
        }
        else{
            hideAndShowViews()
        }
    }


    private fun addEnvent() {
       binding.layoutLoggedOut.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_LoginFragment)
        }
        binding.btnDangXuat.setOnClickListener {
            userViewModel.clearUser()
            sharedPrefManager.clear()
        }
        userViewModel.user.observe(viewLifecycleOwner){newUser->
            if (newUser!=null){

                binding.btnDangXuat.visibility= View.VISIBLE
                binding.layoutLoggedIn.root.visibility= View.VISIBLE
                binding.txtTaiKhoan.visibility= View.VISIBLE
                binding.layoutTaiKhoanUser.root.visibility= View.VISIBLE

                //Ẩn các thanh mặc định khi user chưa đăng nhập
                binding.layoutLoggedOut.root.visibility= View.GONE
                binding.layoutInformation.visibility= View.GONE

                //Gán thông tin user khi đăng nhập thành công
                val fullName= newUser.fullName
                binding.layoutLoggedIn.txtNameUser.text= fullName
                binding.layoutLoggedIn.txtPhoneNumber.text= newUser.phone
                binding.layoutLoggedIn.bgAvatar.text= getTenBackground(fullName)

                if(newUser.role == "landlord"){ // nếu là user là chủ trọ
                    binding.layoutLoggedIn.txtRole.visibility=View.VISIBLE // bật chú thích
                    binding.layoutQuanLyTin.root.visibility=View.VISIBLE // hiển thị layout quản lý phòng trọ
                    binding.layoutTaiKhoanUser.layoutDangtin.visibility=View.GONE // ẩn phần đăng ký trở thành chủ trọ
                }
            }
            else{
                hideAndShowViews()
            }

        }
    }
    private fun hideAndShowViews(){
        binding.layoutQuanLyTin.root.visibility = View.GONE
        binding.btnDangXuat.visibility= View.GONE
        binding.layoutTaiKhoanUser.root.visibility= View.GONE
        binding.layoutLoggedIn.root.visibility= View.GONE
        binding.layoutLoggedOut.root.visibility= View.VISIBLE
        binding.layoutInformation.visibility= View.VISIBLE
        binding.txtTaiKhoan.visibility= View.GONE

    }
    private fun getTenBackground(fullName: String):String{
        if (fullName.isBlank()) return ""

        // Tách chuỗi thành danh sách các từ, loại bỏ khoảng trắng thừa
        val words = fullName.trim().split("\\s+".toRegex())

        return if (words.size >= 2) {
            // Lấy chữ cái đầu của từ áp chót (tên lót) và từ cuối (tên chính)
            val middleInitial = words[words.size - 2].first().uppercase()
            val lastInitial = words.last().first().uppercase()
            "$middleInitial$lastInitial"
        } else {
            // Nếu chỉ có 1 từ (chỉ có tên), lấy chữ cái đầu của từ đó
            words.first().first().uppercase()
        }
    }



    private fun addControll(view: View) {
        sharedPrefManager= SharedPrefManager(requireActivity())
        val api= RetrofitClient.userApiService
        userRepository= UserRepository(api)
        userViewModelFactory= UserViewModelFactory(userRepository)
        userViewModel= ViewModelProvider(requireActivity(),userViewModelFactory)[UserViewModel::class.java]
    }

    //giải phóng bộ nhớ
    override fun onDestroyView() {
        super.onDestroyView()
        _binding= null
    }


}