package com.oneandonly.inventationblock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.datasource.model.data.*
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import com.oneandonly.inventationblock.dateToString
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class StockViewModel(private val repo: StockRepository) : ViewModel() {

    companion object {
        val searchStockList: MutableLiveData<ArrayList<Search>> = MutableLiveData<kotlin.collections.ArrayList<Search>>()
    }

    private val TAG = "Stock_ViewModel"

    //StockList
    private val _stockList = MutableLiveData<ArrayList<Stock>>()
    val stockList: LiveData<ArrayList<Stock>> get() = _stockList

    //HistoryList
    private val _historyList = MutableLiveData<ArrayList<History>>()
    val historyList: LiveData<ArrayList<History>> get() = _historyList

    private val _error = MutableLiveData<String>()
    val errorList: LiveData<String> get() = _error

    val ing : MutableLiveData<State> = MutableLiveData()

    private fun setSearchList(response: Response<StockModel>) {
        val searchListItem: ArrayList<Search> = ArrayList()

        if (response.body()?.response?.size != null) {
            for (i in 0 until response.body()?.response?.size!!) {
                response.body()?.response?.get(i).let {
                    it!!
                    searchListItem.add(
                        Search(
                            it.name ?: "0",
                            "stock",
                            it.unit
                        )
                    )
                }
            }
        } else {
            Log.d("Response Test","Search List Error")
        }

        searchStockList.value = searchListItem
    }

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
                setSearchList(response) // 검색 리스트 생성
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

    fun setToggle(sid: Int) {
        Log.d("setToggle","실행 $sid")
        try {
            viewModelScope.launch {
                val response = repo.setTogglePin(tokens,sid)
                when (response.code()) {
                    200 -> {
                        ing.value = State.Success
                        Log.d("setToggle","200 ${response.message()}")
                    }
                    400 -> {
                        ing.value = State.Fail
                        Log.d("setToggle","400 ${response.errorBody()?.string()}")
                    }
                    401 -> {
                        ing.value = State.Fail
                        Log.d("setToggle","401 ${response.message()}")
                    }
                    else -> {
                        Log.d("setToggle","error")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setSafeAmount(sid: Int, amount: Int) {
        try {
            viewModelScope.launch {
                val response = repo.setSafeAmount(tokens,sid,amount)
                when (response.code()) {
                    200 -> {
                        Log.d("setSafeAmount","200 ${response.body()?.message}")
                    }
                    400 -> {
                        Log.d("setSafeAmount","400 ${response.body()?.message}")
                    }
                    401 -> {
                        Log.d("setSafeAmount","400 ${response.body()?.message}")
                    }
                    else -> {
                        Log.d("setSafeAmount","error")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addAmount(sid: Int, amount: Int, buyDate: String, reason: String) {
        try {
            viewModelScope.launch {
                val response = repo.addAmount(tokens,sid,amount,buyDate,reason)
                when (response.code()) {
                    200 -> {
                        Log.d("addAmount","200 ${response.body()?.message}")
                    }
                    400 -> {
                        Log.d("addAmount","400 ${response.errorBody()?.string()}")
                    }
                    401 -> {
                        Log.d("addAmount","400 ${response.body()?.message}")
                    }
                }
            }
        } catch (e :Exception) {
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