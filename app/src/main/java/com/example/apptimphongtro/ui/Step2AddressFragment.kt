package com.example.apptimphongtro.ui

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.apptimphongtro.R
import com.example.apptimphongtro.databinding.FragmentStep2AddressBinding
import com.example.apptimphongtro.viewmodel.AddPostViewModel
import java.util.Locale


class Step2AddressFragment : Fragment() {
    private var _binding: FragmentStep2AddressBinding?=null
    private val binding get()=_binding!!
    private lateinit var addPostViewModel: AddPostViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding= FragmentStep2AddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addControll()
        addEvent()
        val navController= Navigation.findNavController(requireActivity(),R.id.nav_host_fragment)
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key_address")
            ?.observe(viewLifecycleOwner){address->
                // Khi Map trả về địa chỉ:

                // a. Điền địa chỉ vào EditText/TextView
               // binding.edtAddress.setText(address)

                // b. Tự động chuyển sang màn hình con 3
                // Ép kiểu parentFragment về Fragment tổng để gọi hàm chuyển trang
                if (address!=null)
                (parentFragment as? ImplementAddPostFragment)?.nextStep()

                // c. (Tùy chọn) Xóa dữ liệu sau khi dùng để tránh quay lại bị nhảy trang lần nữa
               // navController.currentBackStackEntry?.savedStateHandle?.remove<String>("key_address")
            }

    }

    private fun addControll() {
        addPostViewModel= ViewModelProvider(requireActivity())[AddPostViewModel::class.java]
    }

    private fun addEvent() {
        binding.txtProvince.setOnClickListener {
            val key= "fragmentProvice"
            val bottomSheet = BottomAddressFragment().apply {
               arguments= Bundle().apply {
                   putString("keyy",key)
               }
            }
            bottomSheet.show(parentFragmentManager, "listCity")
        }
        //hiển thị nameCity lên ô TextView
        addPostViewModel.selectedCity.observe(viewLifecycleOwner){city->
           if (city!=null)
               binding.txtProvince.text = city.city
       }
        //Hiển thị wardName lên ô TextView
        addPostViewModel.selectedWard.observe(viewLifecycleOwner){ward->
            if(ward!=null){
                binding.txtWard.text= ward.wardName
            }
            else
                binding.txtWard.text= getString(R.string.step2Address_txtWard)
        }
        binding.txtWard.setOnClickListener {
            val selectCity= addPostViewModel.selectedCity.value
            if (selectCity==null){
                Toast.makeText(requireContext(), "Vui lòng chọn Tỉnh/Thành phố trước", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val key= "fragmentWard"
            val bottomSheet = BottomAddressFragment().apply {
                arguments= Bundle().apply {
                    putString("keyy",key)
                }
            }
            bottomSheet.show(parentFragmentManager, "listWard")
        }

        binding.btnContinue.setOnClickListener {
            val cityName = addPostViewModel.selectedCity.value?.city
            val wardName = addPostViewModel.selectedWard.value?.wardName
            val address = binding.edtAddress.text.toString()

            if (cityName == null || wardName == null || address.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Thêm "Việt Nam" để Geocoder tìm chính xác hơn
            val addressFull = "$address, $wardName, $cityName, Việt Nam"
            val geocoder = Geocoder(requireContext(), Locale.getDefault())

            try {
                val addresses = geocoder.getFromLocationName(addressFull, 1)
                if (!addresses.isNullOrEmpty()) {
                    val location = addresses[0]
                    val bundle = Bundle().apply {
                        putString("LAT", location.latitude.toString())
                        putString("LNG", location.longitude.toString())
                        putString("ADDRESS", addressFull)
                    }

                    // Sử dụng Global Action để thoát khỏi ViewPager2
                    requireActivity().findNavController(R.id.nav_host_fragment)
                        .navigate(R.id.action_global_to_CofirmMapFragment, bundle)
                } else {
                    Toast.makeText(context, "Không tìm thấy địa chỉ này trên bản đồ", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("GEO_ERROR", e.message.toString())
            }
        }


        binding.btnQuaylai.setOnClickListener {
            val parent = parentFragment as? ImplementAddPostFragment
            parent?.preStep()
        }
    }
}