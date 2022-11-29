package com.oneandonly.inventationblock.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oneandonly.inventationblock.datasource.model.data.Search
import com.oneandonly.inventationblock.datasource.model.repository.StockRepository

class MenuViewModel() : ViewModel() {

    companion object {
        val searchMenuList: MutableLiveData<ArrayList<Search>> = MutableLiveData<ArrayList<Search>>()
    }
}