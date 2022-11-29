package com.oneandonly.inventationblock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityMenuAddBinding
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import com.oneandonly.inventationblock.dateToString
import com.oneandonly.inventationblock.viewmodel.StockViewModel
import com.oneandonly.inventationblock.viewmodel.factory.StockFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

class MenuAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuAddBinding

    private lateinit var stockViewModel: StockViewModel

    private var mCurrentTime = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu_add)

        val date = dateToString(mCurrentTime.time)

        setViewModel()

        uiSetting(date)
    }

    private fun setViewModel() {
        val stockRepo = StockRepository()

        val stockViewModelFactory = StockFactory(stockRepo)
        stockViewModel = ViewModelProvider(this@MenuAddActivity, stockViewModelFactory)[StockViewModel::class.java]
    }

    private fun uiSetting(date: String) {

        binding.menuToolBar.toolBarTitle.text = "충원 재고 직접 입력하기"
        binding.dateEdit.text = date

        binding.menuToolBar.toolBarBackBtn.setOnClickListener {
            onBackPressed()
        }

        binding.dateEdit.setOnClickListener {
            Log.d("MenuAdd","Date : $date")
        }

        binding.addStock.setOnClickListener {
            val format = SimpleDateFormat("yyyy-MM-dd")
            stockViewModel.addAmount(
                sid = 1,
                amount = Integer.parseInt(binding.amountEdit.text.toString()),
                buyDate = format.format(mCurrentTime.time),
                reason = "직접 등록"
            )
        } //TODO(검색 기능 만들기, 날짜 기능 만들기)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}