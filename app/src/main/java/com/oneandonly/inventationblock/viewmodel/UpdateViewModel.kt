package com.oneandonly.inventationblock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UpdateViewModel:ViewModel() {

    companion object {

        private lateinit var _updateTime: MutableLiveData<Int>
        val updateTime: LiveData<Int>
            get() = _updateTime
    }

    init {
        _updateTime.value = 0
        Log.d("UpdateViewModel","initial")
    }

    fun update() {
        _updateTime.value = _updateTime.value?.plus(1)
    }
}

