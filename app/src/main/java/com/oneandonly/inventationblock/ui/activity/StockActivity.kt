package com.oneandonly.inventationblock.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.oneandonly.inventationblock.R
import com.oneandonly.inventationblock.databinding.ActivityStockBinding
import com.oneandonly.inventationblock.datasource.model.data.History
import com.oneandonly.inventationblock.datasource.model.data.Menu
import com.oneandonly.inventationblock.datasource.model.data.Stock
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import com.oneandonly.inventationblock.ui.adapter.HistoryAdapter
import com.oneandonly.inventationblock.ui.adapter.MenuAdapter
import com.oneandonly.inventationblock.ui.dialog.SafeAmountDialog
import com.oneandonly.inventationblock.viewmodel.StockViewModel
import com.oneandonly.inventationblock.viewmodel.factory.StockFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.properties.Delegates

class StockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockBinding

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var menuAdapter: MenuAdapter

    private lateinit var stockViewModel: StockViewModel

    private var name:String? = "None"
    private var stockCurrent: Int? = 0
    private var stockSafe: Int? = 0
    private var fixed : Boolean = false
    private var unit: String? = ""
    private var sid: Int?= 0

    private val df = DecimalFormat("#,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@StockActivity, R.layout.activity_stock)
        binding.lifecycleOwner = this@StockActivity

        name = intent.getStringExtra("name")?:"Null"
        stockCurrent = intent.getIntExtra("stockCurrent",0)
        stockSafe = intent.getIntExtra("stockSafe",0)
        fixed = intent.getBooleanExtra("fixed",false)
        unit = intent.getStringExtra("unit")?:"g"
        sid = intent.getIntExtra("sid",0)

        setViewModel()
        uiSetting()

        historyListObserver()
    }

    private fun setViewModel() {
        val stockRepo = StockRepository()

        val stockViewModelFactory = StockFactory(stockRepo)
        stockViewModel = ViewModelProvider(this@StockActivity, stockViewModelFactory)[StockViewModel::class.java]
    }

    private fun uiSetting() {
        toolbarSetting()
        textSetting()
        btnSetting()
        historyListSetting(stockViewModel)

    }

    private fun toolbarSetting() {
        binding.stockToolBar.toolBarTitle.text = name
        binding.stockToolBar.toolBarStockPin.isSelected = fixed
        binding.stockToolBar.toolBarStockPin.setOnClickListener {
            binding.stockToolBar.toolBarStockPin.isSelected = !binding.stockToolBar.toolBarStockPin.isSelected
            CoroutineScope(Dispatchers.Main).launch {
                stockViewModel.setToggle(sid?:0)
            }
        }
    }
    private fun textSetting() {
        binding.stockAmount.text = "${df.format(stockCurrent)} $unit"
        binding.stockSafeAmount.text = "${df.format(stockSafe)} $unit"
    }

    private fun btnSetting() {
        binding.stockToolBar.toolBarBackBtn.setOnClickListener {
            onBackPressed()
        }
        binding.stockChangeBtn.setOnClickListener {
            dialogSetting()
        }
    }

    private fun dialogSetting() {
        val dialog = SafeAmountDialog(this@StockActivity)
        dialog.show(stockSafe?:0,unit?:"")
        dialog.onClickCheckBtn(object : SafeAmountDialog.CheckBtnClickListener {

            override fun onClick(amount: Int) {
                CoroutineScope(Dispatchers.Main).launch {
                    stockViewModel.setSafeAmount(sid?:0,amount)
                    stockSafe = amount
                    Log.d("SafeAmountDialog","$amount")
                    binding.stockSafeAmount.text = "${df.format(stockSafe)} $unit"
                }
            }
        })
    }

    private fun historyListSetting(stockViewModel: StockViewModel) {
        binding.historyList.layoutManager = LinearLayoutManager(this)
        historyAdapter = HistoryAdapter(stockViewModel.historyList) //이 코드로 변경

        binding.historyList.adapter = historyAdapter
        CoroutineScope(Dispatchers.Main).launch {
            stockViewModel.getHistoryList(sid?:0,unit?:"g")
            Log.d("History","sid $sid")
        }
    }

    private fun historyListObserver() {
        val observer: Observer<ArrayList<History>> = Observer {
            val adapter = HistoryAdapter(stockViewModel.historyList)
            binding.historyList.adapter = adapter
        }
        stockViewModel.historyList.observe(this,observer)
    }

    private fun menuListSetting() {

    }

    override fun onBackPressed() {
        finish()
    }
}