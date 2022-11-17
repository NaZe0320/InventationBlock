package com.oneandonly.inventationblock.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.afterUpdate
import com.oneandonly.inventationblock.databinding.ActivityMainBinding
import com.oneandonly.inventationblock.databinding.NavHeaderMainBinding
import com.oneandonly.inventationblock.datasource.model.data.State
import com.oneandonly.inventationblock.datasource.model.repository.UserRepository
import com.oneandonly.inventationblock.viewmodel.AutoLoginViewModel
import com.oneandonly.inventationblock.viewmodel.TokenViewModel
import com.oneandonly.inventationblock.viewmodel.UserViewModel
import com.oneandonly.inventationblock.viewmodel.factory.UserFactory
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this@MainActivity,R.layout.activity_main)
        binding.lifecycleOwner = this@MainActivity

        setViewModel()

        Log.d("Token Main",tokens.toString())

        val navBind: NavHeaderMainBinding = NavHeaderMainBinding.bind(binding.mainNavView.getHeaderView(0))
        navBind.user = userViewModel //실시간 변경이 안됨 //TODO(수정 필요)
        binding.user = userViewModel
        binding.mainToolBar.user = userViewModel

        //UI
        uiSetting()

    }

    override fun onStart() {
        super.onStart()
        userViewModel.getInformation()
    }

    private fun observeViewModel() {
        userViewModel.user.observe(this@MainActivity, Observer { user ->
            user?.let {

            }
        })
    }

    private fun setViewModel() {
        val repository = UserRepository()
        val viewModelFactory = UserFactory(repository)

        userViewModel = ViewModelProvider(this@MainActivity,viewModelFactory)[UserViewModel::class.java]
    }

    private fun onClickLogout() {
        val autoLoginViewModel = AutoLoginViewModel()
        val tokenViewModel = TokenViewModel()

        tokenViewModel.updateToken("null")

        autoLoginViewModel.updateAutoLogin(false)
        moveToLogin()
    }

    private fun moveToLogin() {
        Log.d("Main_Activity","moveToLogin")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun uiSetting() {
        drawerSetting()
        toolBarSetting()
    }

    private fun drawerSetting() {
        binding.mainDrawer.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)  //열 때는 드로우 잠김, 닫을 때는 드로우 가능
        binding.mainNavView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_myPage -> {
                    afterUpdate()
                }
                R.id.menu_logout -> {
                    onClickLogout()
                }
                R.id.menu_stockSetting -> {
                    afterUpdate()
                }
                R.id.menu_stockReport -> {
                    afterUpdate()
                }
            }
            binding.mainDrawer.closeDrawers()
            return@setNavigationItemSelectedListener false
        }
    }

    private fun toolBarSetting() {
        binding.mainToolBar.toolBarDrawerBtn.setOnClickListener {
            binding.mainDrawer.openDrawer(GravityCompat.START)
        }

        binding.mainToolBar.toolBarAlarmBtn.setOnClickListener {
            afterUpdate()
            //TODO(아직 결정된 바 없음)
        }

        binding.mainToolBar.toolBarMyPageBtn.setOnClickListener {
            afterUpdate()
            //TODO(마이페이지로 연결)
        }

        binding.mainToolBar.toolBarTitle.setOnClickListener {
            //TODO(리사이클러뷰 리스트 초기화)
        }
    }

    private fun stockListSetting() {

    }
}