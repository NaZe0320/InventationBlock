package com.oneandonly.inventationblock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.datasource.model.data.Stock
import com.oneandonly.inventationblock.datasource.model.data.StockModel
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Calendar

class StockViewModel(private val repo: StockRepository) : ViewModel() {

    private val TAG = "Stock_ViewModel"

    private val _stockList = MutableLiveData<ArrayList<Stock>>()
    val stockList: LiveData<ArrayList<Stock>> get() = _stockList

    private val _error = MutableLiveData<String>()
    val errorList: LiveData<String> get() = _error


    private fun setStockList(stockItems: ArrayList<Stock>) {
        _stockList.value = stockItems
    }

    fun getStockList(orderBy: Int) {
        try {
            viewModelScope.launch {
                val response = repo.getList(tokens, orderBy)

                Log.d("Response Test", "${response.body()?.response?.get(0)}")
                postToStock(response) //데이터 변환
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
                    200 -> postToStock(response)
                    400 -> {
                        _error.value = response.message()
                    }
                }
                Log.d("Search_Test","${response.body()}")
                postToStock(response)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun postToStock(response: Response<StockModel>) {
        val stockListItem: ArrayList<Stock> = ArrayList()

        val today = Calendar.getInstance()
        Log.d("Stock_Adapter","오늘 날짜 ${today.time}")

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
                        (today.time.time - (it.addDate?.time ?:today.time.time))/(24 * 60 * 60 * 1000)+2
                    )
                )
            }
        }
        Log.d("Response Test", "$stockListItem")

        setStockList(stockListItem)
    }

    private fun getToStock() {

    }

}