package com.example.apptimphongtro.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apptimphongtro.R
import com.example.apptimphongtro.data.api.RetrofitClient
import com.example.apptimphongtro.data.local.SharedPrefManager
import com.example.apptimphongtro.data.repository.UserRepository
import com.example.apptimphongtro.util.InitUserViewModel
import com.example.apptimphongtro.viewmodel.UserViewModel
import com.example.apptimphongtro.viewmodel.factory.UserViewModelFactory


class LoginFragment : Fragment() {
    private lateinit var edtSdtorEmail : EditText
    private lateinit var edtPass : EditText
    private lateinit var btnApDung : AppCompatButton

    private val userViewModel: UserViewModel by activityViewModels {
        InitUserViewModel.factory
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControll(view)
        addEvent()
        userViewModel.user.observe(viewLifecycleOwner){newUser->
            if(newUser!=null) {
                Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                SharedPrefManager(requireActivity()).saveUser(newUser.userId)
                findNavController().popBackStack()
            }
        }
    }


    private fun addEvent() {
        btnApDung.setOnClickListener {
            val sdtOrEmail= edtSdtorEmail.text.toString()
            val pass= edtPass.text.toString()
            if(sdtOrEmail.isEmpty() || pass.isEmpty()){
                Toast.makeText(context,"Vui lòng điền đủ các thông tin",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            userViewModel.updateUser(sdtOrEmail,pass)

        }
    }

    private fun addControll(view: View) {
        edtSdtorEmail= view.findViewById(R.id.edtContent1)
        edtPass= view.findViewById(R.id.edtPass)
        btnApDung= view.findViewById(R.id.btnApDung)
    }

}