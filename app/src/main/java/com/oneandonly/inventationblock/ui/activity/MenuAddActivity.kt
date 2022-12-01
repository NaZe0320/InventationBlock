package com.oneandonly.inventationblock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityMenuAddBinding
import com.oneandonly.inventationblock.datasource.model.data.Search
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import com.oneandonly.inventationblock.dateToString
import com.oneandonly.inventationblock.ui.activity.MainActivity.Companion.searchList
import com.oneandonly.inventationblock.ui.adapter.SearchDropDownAdapter
import com.oneandonly.inventationblock.viewmodel.StockViewModel
import com.oneandonly.inventationblock.viewmodel.factory.StockFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

class MenuAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuAddBinding

    private lateinit var stockViewModel: StockViewModel

    private var mCurrentTime = Calendar.getInstance()

    private var stockSelected = false
    private var sid = 0

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

        binding.addBtnStock.setOnClickListener {
            val format = SimpleDateFormat("yyyy-MM-dd")
            stockViewModel.addAmount(
                sid = sid,
                amount = Integer.parseInt(binding.amountEdit.text.toString()),
                buyDate = format.format(mCurrentTime.time),
                reason = "직접 등록"
            )
        } //TODO(날짜 기능 만들기)

        val adapter = SearchDropDownAdapter(this, R.layout.item_dropdown2, searchList)
        binding.stockSearchEdit.setAdapter(adapter)
        binding.stockSearchEdit.threshold = 1

        binding.stockSearchEdit.setOnItemClickListener { adapterView, view, i, l ->
            val selected = adapterView.adapter.getItem(i) as Search
            binding.stockSearchEdit.setText(selected.name)
            binding.unitSpinner.text = selected.unit
            sid = selected.sid
            Log.d("stockSearchEdit","${selected.unit}")

            stockSelectedChange()
        }

        binding.stockSearchBtn.setOnClickListener {
            if (stockSelected) {
                stockSelectedChange()
            }
        }
    }

    private fun stockSelectedChange() {
        stockSelected = !stockSelected
        binding.addBtnStock.isEnabled = stockSelected
        binding.stockSearchEdit.isEnabled = !stockSelected
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}