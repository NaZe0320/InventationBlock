package com.oneandonly.inventationblock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MenuActivity, R.layout.activity_menu)
        binding.lifecycleOwner = this@MenuActivity

        uiSetting()
    }

    private fun setViewModel() {

    }

    private fun uiSetting() {
        toolbarSetting()
    }

    private fun toolbarSetting() {
        binding.menuToolBar.toolBarBackBtn.setOnClickListener {
            onBackPressed()
        }
        binding.menuToolBar.toolBarTitle.text = "메뉴 별 재고 설정"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
