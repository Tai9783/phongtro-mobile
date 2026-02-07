package com.example.apptimphongtro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.apptimphongtro.databinding.FragmentConfirmMapBinding
import com.example.apptimphongtro.BuildConfig as AppBuildConfig

import com.trackasia.android.Trackasia
import com.trackasia.android.maps.TrackasiaMap
import com.trackasia.android.maps.Style
import com.trackasia.android.camera.CameraUpdateFactory
import com.trackasia.android.geometry.LatLng
import com.trackasia.android.annotations.Marker
import com.trackasia.android.annotations.MarkerOptions


class ConfirmMapFragment : Fragment() {
    private var _binding: FragmentConfirmMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: TrackasiaMap
    private var currentLatLng: LatLng? = null
    private var marker: Marker? = null

    private val styleUrl by lazy {
        "https://maps.track-asia.com/styles/v2/streets.json?key=${AppBuildConfig.TRACK_ASIA_KEY}"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // TrackAsia init (bản demo họ dùng getInstance(context) không truyền key) :contentReference[oaicite:1]{index=1}
        Trackasia.getInstance(requireContext())
        _binding = FragmentConfirmMapBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync { m ->
            map = m
            map.setStyle(Style.Builder().fromUri(styleUrl)) {
                setupMap()
            }
        }
    }

    private fun setupMap() {
        val lat = arguments?.getString("LAT")?.toDouble() ?: 10.8231
        val lng = arguments?.getString("LNG")?.toDouble() ?: 106.6297
        currentLatLng = LatLng(lat, lng)

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng!!, 15.0))

        marker = map.addMarker(
            MarkerOptions()
                .position(currentLatLng!!)
                .title("Vị trí phòng trọ")
        )

        map.addOnMapClickListener { point ->
            marker?.position = point
            currentLatLng = point
            true
        }

        binding.btnContinue.setOnClickListener {
            // currentLatLng?.let { saveToDatabase(it.latitude, it.longitude) }
            // 1. (Tùy chọn) Gửi dữ liệu về trước khi đóng
            val locationData= 1
            findNavController().previousBackStackEntry?.savedStateHandle?.set("key_location", locationData)

            // 2. Lệnh để quay lại màn hình trước đó
            findNavController().popBackStack()
        }
    }

    //Quản lý vòng đời của MapView
    override fun onStart() { super.onStart(); binding.map.onStart() }
    override fun onResume() { super.onResume(); binding.map.onResume() }
    override fun onPause() { binding.map.onPause(); super.onPause() }
    override fun onStop() { binding.map.onStop(); super.onStop() }
    override fun onLowMemory() { super.onLowMemory(); binding.map.onLowMemory() }
    override fun onDestroyView() { binding.map.onDestroy(); _binding = null; super.onDestroyView() }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.map.onSaveInstanceState(outState)
    }
}
