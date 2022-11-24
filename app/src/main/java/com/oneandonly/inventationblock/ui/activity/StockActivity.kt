package com.oneandonly.inventationblock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityStockBinding
import com.oneandonly.inventationblock.datasource.model.data.History
import com.oneandonly.inventationblock.datasource.model.data.Stock
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import com.oneandonly.inventationblock.ui.adapter.HistoryAdapter
import com.oneandonly.inventationblock.ui.adapter.MenuAdapter
import com.oneandonly.inventationblock.viewmodel.StockViewModel
import com.oneandonly.inventationblock.viewmodel.factory.StockFactory

class StockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockBinding

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var menuAdapter: MenuAdapter

    private lateinit var stockViewModel: StockViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@StockActivity, R.layout.activity_stock)
        binding.lifecycleOwner = this@StockActivity

        setViewModel()
        uiSetting()

    }

    private fun setViewModel() {
        val stockRepo = StockRepository()

        val stockViewModelFactory = StockFactory(stockRepo)
        stockViewModel = ViewModelProvider(this@StockActivity, stockViewModelFactory)[StockViewModel::class.java]
    }

    private fun uiSetting() {

    }

    private fun btnSetting() {

    }

    private fun historyListSetting() {
        val historyList = MutableLiveData<ArrayList<History>>() // 임시 코드
        binding.historyList.layoutManager = LinearLayoutManager(this)
        historyAdapter = HistoryAdapter(historyList)
        //historyAdapter = HistoryAdapter(stockViewModel.) //이 코드로 변경

        binding.historyList.adapter = historyAdapter
    }

    private fun menuListSetting() {

    }

    override fun onBackPressed() {
        finish()
    }
}