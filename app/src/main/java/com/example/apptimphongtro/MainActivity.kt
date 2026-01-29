    package com.example.apptimphongtro

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.apptimphongtro.data.local.SharedPrefManager
import com.example.apptimphongtro.util.InitUserViewModel
import com.example.apptimphongtro.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

    class   MainActivity : AppCompatActivity() {
        private lateinit var navHostFragment: NavHostFragment
        private lateinit var bottomNav:BottomNavigationView
        private lateinit var sharedPrefManager: SharedPrefManager
        private val userViewModel: UserViewModel by viewModels{
            InitUserViewModel.factory
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,0)
            insets
        }
        addControll()
        addEvent()
    }

        private fun addEvent() {
            checkLoginAuto()
        }

        private fun checkLoginAuto() {
            val userId= sharedPrefManager.getUser()
            if(userId!=null){
                userViewModel.fetchUser(userId)
            }
            val navMenu= bottomNav.menu
            val addPostItem= navMenu.findItem(R.id.addPostFrgament)
            addPostItem.isVisible= false
            userViewModel.user.observe(this){inforUser->
                //hiện icon đăng tin nếu tài khoản là chủ trọ
                addPostItem.isVisible = inforUser!=null && inforUser.role=="landlord"
            }
        }

        private fun addControll() {
            sharedPrefManager= SharedPrefManager(this)


            navHostFragment= supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController= navHostFragment.navController

            bottomNav = findViewById(R.id.bottomNav)
            bottomNav.setupWithNavController(navController)

            //setup BottomNav chỉ được hiển thị lên khi ở các màn hình này
            val showBottomNav= setOf(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.favotiteFragment,
                R.id.profileFragment,
                R.id.addPostFrgament
            )

            navController.addOnDestinationChangedListener{_,destination,_->
                bottomNav.visibility= if (destination.id in showBottomNav) View.VISIBLE else View.GONE
            }

        }

    }