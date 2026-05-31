package com.example.apptimphongtro.ui

import androidx.fragment.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.apptimphongtro.R


class StatusDialog : DialogFragment() {
    private var isSuccess: Boolean= true
    private var message: String? = null
    var onPrimaryClick: (() -> Unit)? = null
    var onSecondaryClick: (() -> Unit)? = null

    companion object{
        private const val ARG_IS_SUCCESS= "is_success"
        private const val ARG_MESSAGE="message"
        fun newInstance(isSuccess: Boolean, message: String? = null): StatusDialog {
            val fragment= StatusDialog()
            val args= Bundle()
            args.putBoolean(ARG_IS_SUCCESS, isSuccess)
            args.putString(ARG_MESSAGE, message)
            fragment.arguments= args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isSuccess= it.getBoolean(ARG_IS_SUCCESS)
            message= it.getString(ARG_MESSAGE)
        }
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // Làm cho cái "cửa sổ" chứa Dialog trở nên trong suốt
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutIcon= view.findViewById<FrameLayout>(R.id.layoutIcon)
        val igStatus= view.findViewById<ImageView>(R.id.igStatus)
        val txtResult= view.findViewById<TextView>(R.id.txtResult)
        val txtDecription= view.findViewById<TextView>(R.id.txtdecription)
        val btnPrimary= view.findViewById<Button>(R.id.btnPrimary)
        val btnSecond= view.findViewById<Button>(R.id.btnSecond)

        if(isSuccess){
            setupSuccessUI(layoutIcon, igStatus, txtResult, txtDecription, btnPrimary, btnSecond)
        }
        else{
            setupFailureUI(layoutIcon, igStatus, txtResult, txtDecription, btnPrimary, btnSecond)
        }
        btnSecond.setOnClickListener {
            onSecondaryClick?.invoke()
            dismiss()
        }
        btnPrimary.setOnClickListener {
            onPrimaryClick?.invoke()
            dismiss()
        }
    }

    private fun setupFailureUI(
        layoutIcon: FrameLayout?,
        igStatus: ImageView?,
        txtResult: TextView?,
        txtDecription: TextView?,
        btnPrimary: Button?,
        btnSecond: Button?
    ) {
        val colorDo= Color.parseColor("#FF4A4A")
        val colorDoNhat= Color.parseColor("#33FF4A4A")
        val layerDrawable= layoutIcon?.background as LayerDrawable
        layerDrawable.findDrawableByLayerId(R.id.outerCircle).setTint(colorDo)
        layerDrawable.findDrawableByLayerId(R.id.innerCircle).setTint(colorDoNhat)
        igStatus?.setImageResource(R.drawable.close)
        txtResult?.text= getString(R.string.dialogInformation_title2)
        txtDecription?.text = message ?: "Có lỗi xảy ra khi lưu thông tin phòng. Vui lòng thử lại."
        btnPrimary?.text=getString(R.string.dialogInformation_btnPrimary2)
        btnSecond?.text=getString(R.string.dialogInformation_btnSecond2)
    }

    private fun setupSuccessUI(
        layoutIcon: FrameLayout?,
        igStatus: ImageView?,
        txtResult: TextView?,
        txtDecription: TextView?,
        btnPrimary: Button?,
        btnSecond: Button?
    ) {
        val colorXanh= Color.parseColor("#074EFF")
        val colorXanhNhat= Color.parseColor("#33074EFF")
        val layerDrawable= layoutIcon?.background as LayerDrawable
        layerDrawable.findDrawableByLayerId(R.id.outerCircle).setTint(colorXanhNhat)
        layerDrawable.findDrawableByLayerId(R.id.innerCircle).setTint(colorXanh)
        igStatus?.setImageResource(R.drawable.icon_check_24px)
        txtResult?.text= message.toString()
        txtDecription?.text= getString(R.string.dialogInformation_decription)
        btnPrimary?.text=getString(R.string.dialogInformation_btnPrimary)
        btnSecond?.text=getString(R.string.dialogInformation_btnSecond)
    }


}
