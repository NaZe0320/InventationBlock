package com.oneandonly.inventationblock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.datasource.model.data.Stock
import com.oneandonly.inventationblock.datasource.model.data.StockModel
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import com.oneandonly.inventationblock.ui.adapter.StockAdapter
import kotlinx.coroutines.launch
import retrofit2.Response

class StockViewModel(private val repo: StockRepository) : ViewModel() {

    private val TAG = "Stock_ViewModel"

    private val _stockList = MutableLiveData<ArrayList<Stock>>()
    val stockList: LiveData<ArrayList<Stock>> get() = _stockList


    private fun setStockList(stockItems: ArrayList<Stock>) {
        _stockList.value = stockItems
    }

    fun getList() {
        try {
            viewModelScope.launch {
                val response = repo.getList(tokens,"list", 0)

                Log.d("Response Test", "${response.body()?.response?.get(0)}")
                postToStock(response)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun postToStock(response: Response<StockModel>) {
        val stockListItem: ArrayList<Stock> = ArrayList()

        for (i in 0 until response.body()?.response?.size!!) {
            response.body()?.response?.get(i).let {
                stockListItem.add(
                    Stock(
                        it?.name ?: "0",
                        it?.amount ?: 0,
                        it?.safeStandard ?: 0,
                        (it?.pinned ?: 0) != 0,
                        it?.unit ?: "",
                        0
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