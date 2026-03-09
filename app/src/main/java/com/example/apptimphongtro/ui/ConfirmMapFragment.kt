package com.example.apptimphongtro.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.apptimphongtro.databinding.FragmentConfirmMapBinding
import com.example.apptimphongtro.viewmodel.AddPostViewModel
import com.example.apptimphongtro.BuildConfig as AppBuildConfig

import com.trackasia.android.Trackasia
import com.trackasia.android.maps.TrackasiaMap
import com.trackasia.android.maps.Style
import com.trackasia.android.camera.CameraUpdateFactory
import com.trackasia.android.geometry.LatLng
import com.trackasia.android.annotations.Marker
import com.trackasia.android.annotations.MarkerOptions
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import kotlin.math.ln


class ConfirmMapFragment : Fragment() {
    private var _binding: FragmentConfirmMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: TrackasiaMap
    private var currentLatLng: LatLng? = null
    private var marker: Marker? = null
    private val client= OkHttpClient()
    private lateinit var addPostViewModel: AddPostViewModel

    private val styleUrl by lazy {
        "https://maps.track-asia.com/styles/v2/streets.json?key=${AppBuildConfig.TRACK_ASIA_KEY}"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
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
        addPostViewModel= ViewModelProvider(requireActivity())[AddPostViewModel::class.java]

    }

    private fun setupMap() {
        val lat = arguments?.getString("LAT")?.toDouble() ?: 10.8231
        val lng = arguments?.getString("LNG")?.toDouble() ?: 106.6297
        val originalAddress = arguments?.getString("ADDRESS") ?: "Đang xác định..."
        currentLatLng = LatLng(lat, lng)
        binding.txtAddress.text = originalAddress
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng!!, 15.0))
        getAddressFromTrackAsisia(lat,lng)

        marker = map.addMarker(
            MarkerOptions()
                .position(currentLatLng!!)
                .title("Vị trí phòng trọ")
        )

        map.addOnMapClickListener { point ->
            marker?.position = point
            currentLatLng = point
            getAddressFromTrackAsisia(point.latitude, point.longitude)
            true
        }

        binding.btnContinue.setOnClickListener {
            val lag= currentLatLng?.latitude
            val lng= currentLatLng?.longitude
            val address= binding.txtAddress.text.toString()
            if (lag!=null && lng!=null && address.isNotEmpty() && address !="Đang xác định..."){
                addPostViewModel.updateStep2Address(address,lag,lng)
            }
            findNavController().popBackStack()
        }
    }
    private fun getAddressFromTrackAsisia(lat: Double, lng: Double){
        //Xử lý hủy các request đang chạy nhằm để tránh tốn dữ liệu do chạy ngầm và chậm máy
        //dispatcher: Bộ điều phối nơi quản lý tất cả các cuộc gọi mạng đang chờ hoặc đang chạy
        //queuedCalls(): Đây là danh sách các yêu cầu đang nằm trong "hàng đợi", chưa kịp gửi đi
        //runningCalls(): Đây là danh sách các yêu cầu đang thực sự "chạy" trên mạng và đợi phản hồi từ server.
        client.dispatcher.queuedCalls().forEach { if (it.request().tag() == "GET_ADDRESS") it.cancel() }
        client.dispatcher.runningCalls().forEach { if (it.request().tag() == "GET_ADDRESS") it.cancel() }
        val url = "https://maps.track-asia.com/api/v2/geocode/json?latlng=$lat%2C$lng&key=${AppBuildConfig.TRACK_ASIA_KEY}&new_admin=true&size=1"

        val request= Request.Builder()
            .url(url)
            .tag("GET_ADDRESS") // đặt tag để tự quản    lý
            .build()
        // Bắt đầu gọi API
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (e.message == "Canceled") return // Bỏ qua nếu là lỗi do mình chủ động hủy
                // Xử lý khi mất mạng hoặc lỗi server
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
               val jsonData= response.body?.string()
                if(jsonData!=null) {
                    val jsonObject = JSONObject(jsonData)
                    val status= jsonObject.optString("status")
                    if(status=="OK"){
                        val resultArray= jsonObject.getJSONArray("results")
                        if(resultArray.length()>0){
                            val result= resultArray.getJSONObject(0)
                            val fullAddress= result.optString("formatted_address")
                            activity?.runOnUiThread {
                                binding.txtAddress.text=fullAddress
                            }
                        }
                    }
                }
            }
        })
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
