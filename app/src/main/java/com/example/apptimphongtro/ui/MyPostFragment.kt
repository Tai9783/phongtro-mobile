package com.example.apptimphongtro.ui

import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptimphongtro.adapter.MyPostAdapter
import com.example.apptimphongtro.data.local.SharedPrefManager
import com.example.apptimphongtro.databinding.FragmentMyPostBinding
import com.example.apptimphongtro.util.InitMyPostViewModel
import com.example.apptimphongtro.util.InitUserViewModel
import com.example.apptimphongtro.viewmodel.MyPostViewModel
import com.example.apptimphongtro.viewmodel.UserViewModel

class MyPostFragment : Fragment() {
    private lateinit var _binding: FragmentMyPostBinding
    private lateinit var sharedPrefManager: SharedPrefManager
    private val myPostAdapter = MyPostAdapter()
    private val myPostViewModel: MyPostViewModel by viewModels{
        InitMyPostViewModel.factory
    }
    private val userViewModel: UserViewModel by viewModels{
        InitUserViewModel.factory
    }
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControll()
        addEvent()
    }

    private fun addControll() {
        sharedPrefManager= SharedPrefManager(this.requireContext())

        binding.rcvMyPost.adapter= myPostAdapter
        binding.rcvMyPost.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun addEvent() {
        binding.iconExit.setOnClickListener {
            findNavController().popBackStack()
        }

        val userId= sharedPrefManager.getUser()
        if(userId!=null){
            userViewModel.fetchUser(userId)
            userViewModel.user.observe(viewLifecycleOwner){inforUser->
                if(inforUser!=null){
                    myPostViewModel.getMyPost(inforUser.userId)
                }
            }
        }
        myPostViewModel.listMyPost.observe(viewLifecycleOwner){listPost->
            myPostAdapter.submitList(listPost)

            val isEmpty= listPost.isNullOrEmpty()
            binding.layoutEmptyMyPost.visibility= if(isEmpty) View.VISIBLE else View.GONE
            binding.rcvMyPost.visibility= if(isEmpty) View.GONE else View.VISIBLE
        }
    }

}