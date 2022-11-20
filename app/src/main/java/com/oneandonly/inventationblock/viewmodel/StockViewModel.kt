package com.oneandonly.inventationblock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.oneandonly.inventationblock.datasource.model.data.Stock
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository
import com.oneandonly.inventationblock.ui.adapter.StockAdapter

class StockViewModel(repo: StockRepository) : ViewModel() {

    private val TAG = "Stock_ViewModel"

    private val _stockList = MutableLiveData<ArrayList<Stock>>()
    val stockList: LiveData<ArrayList<Stock>> get() = _stockList

    init {
        val stockListItem: ArrayList<Stock> = ArrayList()
        stockListItem.add(Stock("돼지고기",100, 40,true,"g",5))
        stockListItem.add(Stock("?",4, 2,false,"kg",3))
        stockListItem.add(Stock("양파",3, 5,false,"개",1))
        stockListItem.add(Stock("?",60, 40,false,"L",2))

        _stockList.value = stockListItem

    }

    fun setStockList(stockItems: ArrayList<Stock>) {
        _stockList.value = stockItems
    }


}