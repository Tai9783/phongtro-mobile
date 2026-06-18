package com.example.apptimphongtro.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apptimphongtro.R
import com.example.apptimphongtro.databinding.FragmentMyPostBinding

class MyPostFragment : Fragment() {
    private lateinit var _binding: FragmentMyPostBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPostBinding.inflate(inflater, container, false)
        return binding.root
    }

}