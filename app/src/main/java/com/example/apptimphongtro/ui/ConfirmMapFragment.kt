package com.example.apptimphongtro.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apptimphongtro.R
import com.example.apptimphongtro.adapter.AddPostAdapter
import com.example.apptimphongtro.databinding.FragmentAddPostBinding
import com.example.apptimphongtro.databinding.FragmentConfirmMapBinding
import com.example.apptimphongtro.databinding.FragmentProfileBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class ConfirmMapFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentConfirmMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private var currentLatLng: LatLng? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConfirmMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Khởi tạo MapFragment
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Lấy dữ liệu từ argument
        val lat = arguments?.getString("LAT")?.toDouble() ?: 0.0
        val lng = arguments?.getString("LNG")?.toDouble() ?: 0.0
        val address = arguments?.getString("ADDRESS")
        currentLatLng = LatLng(lat, lng)

        // Cắm ghim cho phép kéo thả
        mMap.addMarker(
            MarkerOptions()
            .position(currentLatLng!!)
            .title("Vị trí phòng trọ")
            .draggable(true))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng!!, 17f))

        // Lắng nghe sự kiện kéo ghim để cập nhật tọa độ mới
        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {
                // Khóa ViewPager2 lại để không bị nhảy trang khi đang kéo ghim
                // Lưu ý: Bạn cần truy cập vào ViewPager2 ở Fragment cha hoặc Activity
                (parentFragment?.parentFragment as? AddPostFragment)?.binding?.viewPager?.isUserInputEnabled = false
            }

            override fun onMarkerDragEnd(marker: Marker) {
                currentLatLng = marker.position
                // Mở khóa lại sau khi kéo xong
                (parentFragment?.parentFragment as? AddPostFragment)?.binding?.viewPager?.isUserInputEnabled = true
            }

            override fun onMarkerDrag(p0: Marker) {}
        })

       /* binding.btnConfirm.setOnClickListener {
            // Đây là lúc bạn lưu latitude, longitude và address vào Database
            saveToDatabase(currentLatLng!!.latitude, currentLatLng!!.longitude)
        }*/
    }
}