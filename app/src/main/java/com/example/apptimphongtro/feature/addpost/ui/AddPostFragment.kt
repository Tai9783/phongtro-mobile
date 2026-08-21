package com.example.apptimphongtro.feature.addpost.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.apptimphongtro.R
import com.example.apptimphongtro.databinding.FragmentAddPostBinding
import com.example.apptimphongtro.databinding.FragmentProfileBinding

class AddPostFragment : Fragment() {
    private var _binding: FragmentAddPostBinding? =null
    val binding get()= _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       binding.btnStart.setOnClickListener {
           findNavController().navigate(R.id.action_addPost_to_implementAddPostFragment)
       }
    }


}