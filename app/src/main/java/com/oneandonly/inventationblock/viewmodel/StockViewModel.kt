package com.oneandonly.inventationblock.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StockViewModel: ViewModel() {

    var name: MutableLiveData<String> = MutableLiveData()
    var stock: MutableLiveData<Int> = MutableLiveData()
    var unit: MutableLiveData<String> = MutableLiveData()
    var fixed: MutableLiveData<Boolean> = MutableLiveData()
    var expiration: MutableLiveData<Int> = MutableLiveData()
}