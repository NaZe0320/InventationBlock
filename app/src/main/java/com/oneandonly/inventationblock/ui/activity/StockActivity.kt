package com.oneandonly.inventationblock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ItemStockBinding

class StockActivity : AppCompatActivity() {

    private lateinit var binding: ItemStockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@StockActivity, R.layout.activity_stock)
        binding.lifecycleOwner = this@StockActivity

        setViewModel()
        uiSetting()

    }

    private fun setViewModel() {

    }

    private fun uiSetting() {

    }

    private fun historyList() {

    }

    override fun onBackPressed() {
        finish()
    }
}