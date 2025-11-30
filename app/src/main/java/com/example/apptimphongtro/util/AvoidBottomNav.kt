package com.example.apptimphongtro.util

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

class AvoidBottomNav(private val bottomNav: BottomNavigationView) {
    fun applyPadding(view: View){
        view.post{
            view.setPadding(
                view.paddingLeft,
                view.paddingTop,
                view.paddingRight,
                bottomNav.height
            )
        }
    }

}