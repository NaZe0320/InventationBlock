package com.oneandonly.inventationblock.ui.activity

import android.content.Intent
import android.net.wifi.WifiManager.LocalOnlyHotspotCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.afterUpdate
import com.oneandonly.inventationblock.databinding.ActivityMainBinding
import com.oneandonly.inventationblock.databinding.NavHeaderMainBinding
import com.oneandonly.inventationblock.datasource.model.data.Stock
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import com.oneandonly.inventationblock.datasource.model.repository.UserRepository
import com.oneandonly.inventationblock.ui.adapter.StockAdapter
import com.oneandonly.inventationblock.viewmodel.AutoLoginViewModel
import com.oneandonly.inventationblock.viewmodel.StockViewModel
import com.oneandonly.inventationblock.viewmodel.TokenViewModel
import com.oneandonly.inventationblock.viewmodel.UserViewModel
import com.oneandonly.inventationblock.viewmodel.factory.StockFactory
import com.oneandonly.inventationblock.viewmodel.factory.UserFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var userViewModel: UserViewModel
    private lateinit var stockViewModel: StockViewModel

    private val stockList = MutableLiveData<ArrayList<Stock>>()
    private lateinit var stockAdapter: StockAdapter

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

        //Observer
        stockListObserver()

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
        val stockRepo = StockRepository()

        val viewModelFactory = UserFactory(repository)
        userViewModel = ViewModelProvider(this@MainActivity,viewModelFactory)[UserViewModel::class.java]

        val stockViewModelFactory = StockFactory(stockRepo)
        stockViewModel = ViewModelProvider(this@MainActivity,stockViewModelFactory)[StockViewModel::class.java]

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
        stockListSetting(stockViewModel)
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

    private fun stockListSetting(stockViewModel: StockViewModel) {
        binding.stockList.layoutManager = LinearLayoutManager(this)
        Log.d("Main_Activity","1")
        stockAdapter = StockAdapter(stockViewModel.stockList)
        Log.d("Main_Activity","2")
        binding.stockList.adapter = stockAdapter
        Log.d("Main_Activity","3")
        CoroutineScope(Dispatchers.Main).launch {
            stockViewModel.getList()
        }

    }

    private fun stockListObserver() {
        val stockObserver: Observer<ArrayList<Stock>> = Observer {
                stockList.value = it
                val adapter = StockAdapter(stockList)
                binding.stockList.adapter = adapter
                Log.d("Main_Activity","4")
        }

        stockViewModel.stockList.observe(this,stockObserver)
    }
}