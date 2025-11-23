package com.example.apptimphongtro.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class FormatMoney {
    fun formatMoney(price: Double):String{
        val symbols= DecimalFormatSymbols().apply {
        groupingSeparator='.'
        }
        val format=DecimalFormat("#,###",symbols)
        return format.format(price)+"đ"
    }
}