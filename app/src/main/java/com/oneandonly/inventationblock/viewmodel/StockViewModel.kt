package com.oneandonly.inventationblock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.datasource.model.data.History
import com.oneandonly.inventationblock.datasource.model.data.Stock
import com.oneandonly.inventationblock.datasource.model.data.StockModel
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import com.oneandonly.inventationblock.dateToString
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.DecimalFormat
import java.util.Calendar

class StockViewModel(private val repo: StockRepository) : ViewModel() {

    private val TAG = "Stock_ViewModel"

    //StockList
    private val _stockList = MutableLiveData<ArrayList<Stock>>()
    val stockList: LiveData<ArrayList<Stock>> get() = _stockList

    //HistoryList
    private val _historyList = MutableLiveData<ArrayList<History>>()
    val historyList: LiveData<ArrayList<History>> get() = _historyList

    private val _error = MutableLiveData<String>()
    val errorList: LiveData<String> get() = _error


    private fun setStockList(stockItems: ArrayList<Stock>) {
        _stockList.value = stockItems
    }

    private fun setHistoryList(historyItems: ArrayList<History>) {
        _historyList.value = historyItems
    }

    fun getStockList(orderBy: Int) {
        try {
            viewModelScope.launch {
                val response = repo.getList(tokens, orderBy)

                Log.d("Response Test", "${response.body()?.response?.get(0)}")
                responseToStock(response) //데이터 변환
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSearchList(search: String) {
        try {
            viewModelScope.launch {
                val response = repo.getSearchList(tokens, search)

                when (response.code()) {
                    200 -> responseToStock(response)
                    400 -> {
                        _error.value = response.message()
                    }
                }
                Log.d("Search_Test","${response.body()}")
                responseToStock(response)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getHistoryList(sid: Int, unit: String) {
        try {
            viewModelScope.launch {
                val response = repo.getHistoryList(tokens,sid)
                when (response.code()) {
                    200 -> {
                        responseToHistory(response, unit)
                        Log.d("History","200 ${response.body()?.message}")
                    }
                    400 -> {
                        Log.d("History","400 ${response.body()?.message}")
                    }
                    401 -> {
                        Log.d("History","401 ${response.body()?.message}")
                    }
                    else -> {
                        Log.d("History","4 ${response.errorBody()?.string()}")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun responseToStock(response: Response<StockModel>) {
        val stockListItem: ArrayList<Stock> = ArrayList()

        val today = Calendar.getInstance()
        Log.d("Stock_Adapter","오늘 날짜 ${today.time}")

        if (response.body()?.response?.size != null) {
            for (i in 0 until response.body()?.response?.size!!) {
                response.body()?.response?.get(i).let {
                    it!!
                    stockListItem.add(
                        Stock(
                            it.name ?: "0",
                            it.amount ?: 0,
                            it.safeStandard ?: 0,
                            (it.pinned ?: 0) != 0,
                            it.unit ?: "",
                            (today.time.time - (it.addDate?.time ?:today.time.time))/(24 * 60 * 60 * 1000)+2,
                            it.sid ?: 0
                        )
                    )
                }
            }
        } else {
            Log.d("Response Test","Error")
        }
        setStockList(stockListItem)
    }

    private fun responseToHistory(response: Response<StockModel>, unit: String) {
        val historyListItem: ArrayList<History> = ArrayList()

        if (response.body()?.response?.size != null) {
            for (i in 0 until response.body()?.response?.size!!) {
                response.body()?.response?.get(i)?.let {
                    historyListItem.add(
                        History(
                            date = dateToString(it.date?:Calendar.getInstance().time),
                            content = it.reason ?: "",
                            amount = (DecimalFormat("+#,###;-#,###").format(it.amount?:0).toString() + unit),
                            pm = (it.amount ?: 0) >= 0
                        )
                    )
                }
            }
            Log.d("History","$historyListItem")
            setHistoryList(historyListItem)
        } else {
            Log.d("Response Test","Error")
        }

    }


}