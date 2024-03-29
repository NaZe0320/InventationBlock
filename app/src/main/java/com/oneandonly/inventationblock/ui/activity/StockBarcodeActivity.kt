package com.oneandonly.inventationblock.ui.activity

import android.R.style.*
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityStockAddBinding
import com.oneandonly.inventationblock.datasource.model.data.Search
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import com.oneandonly.inventationblock.dateToString
import com.oneandonly.inventationblock.ui.activity.MainActivity.Companion.stockList
import com.oneandonly.inventationblock.ui.adapter.SearchDropDownAdapter
import com.oneandonly.inventationblock.viewmodel.StockViewModel
import com.oneandonly.inventationblock.viewmodel.factory.StockFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StockBarcodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockAddBinding

    private lateinit var stockViewModel: StockViewModel

    private var mCurrentTime = Calendar.getInstance()

    private var stockSelected = false
    private var sid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stock_add)

        val date = dateToString(mCurrentTime.time)

        setViewModel()
        uiSetting(date)
    }

    private fun setViewModel() {
        val stockRepo = StockRepository()

        val stockViewModelFactory = StockFactory(stockRepo)
        stockViewModel = ViewModelProvider(this@StockBarcodeActivity, stockViewModelFactory)[StockViewModel::class.java]
    }

    private fun uiSetting(date: String) {

        binding.menuToolBar.toolBarTitle.text = "충원재고 인식 결과 확인"
        binding.dateEdit.text = date

        binding.stockSearchEdit.setText("양파")
        binding.stockSearchBtn.visibility = View.GONE

        binding.amountEdit.setText("40")
        binding.unitSpinner.text = "개"

        binding.addBtnStock.isEnabled = true

        binding.menuToolBar.toolBarBackBtn.setOnClickListener {
            onBackPressed()
        }

        binding.dateEdit.setOnClickListener {
            Log.d("MenuAdd","Date : $date")
        }

        binding.addBtnStock.setOnClickListener {
            val format = SimpleDateFormat("yyyy-MM-dd")
            stockViewModel.addAmount(
                sid = 106,
                amount = Integer.parseInt(binding.amountEdit.text.toString()),
                buyDate = format.format(mCurrentTime.time),
                reason = "바코드 등록"
            )
            showDialog()
        }

        setSearchEdit()
        setDatePicker()
    }

    private fun showDialog() {
        AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
            .setTitle("충원 재고 등록")
            .setMessage("양파(이)가 등록이 완료되었습니다")
            .setNegativeButton("홈으로 돌아가기") { p0, p1 ->
                Log.d("dialog", "positive $p0 $p1")
                val intent = Intent(this@StockBarcodeActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            .create()
            .show()
    }

    private fun setSearchEdit() {
        val adapter = SearchDropDownAdapter(this, R.layout.item_dropdown2, stockList)
        binding.stockSearchEdit.setAdapter(adapter)
        binding.stockSearchEdit.threshold = 1

        binding.stockSearchEdit.setOnItemClickListener { adapterView, view, i, l ->
            val selected = adapterView.adapter.getItem(i) as Search
            binding.stockSearchEdit.setText(selected.name)
            binding.unitSpinner.text = selected.unit
            sid = selected.id
            Log.d("stockSearchEdit","${selected.unit}")

            stockSelectedChange()
        }

        binding.stockSearchBtn.setOnClickListener {
            if (stockSelected) {
                stockSelectedChange()
            }
        }
    }

    private fun setDatePicker() {

        val mCurrentTime = Calendar.getInstance()
        val year = mCurrentTime.get(Calendar.YEAR)
        val month = mCurrentTime.get(Calendar.MONTH)
        val day = mCurrentTime.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, R.style.DatePickerStyle, object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                val calendar = Calendar.getInstance()

                calendar.set(year,month,dayOfMonth)

                val date = calendar.time
                val dateFormat = SimpleDateFormat("EE", Locale.getDefault())
                val dayName: String = dateFormat.format(date)

                binding.dateEdit.text = "$year.${month+1}.$dayOfMonth.$dayName"
            }
        }, year, month, day)
        binding.dateEdit.setOnClickListener {
            datePicker.show()
        }

    }//TODO(날짜 기능 만들기)

    private fun clear() {
        binding.amountEdit.text.clear()
        binding.stockSearchEdit.text.clear()
        stockSelectedChange()
        binding.amountEdit.clearFocus()
        sid = 0
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