package com.oneandonly.inventationblock.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.datasource.Setting
import com.oneandonly.inventationblock.datasource.setting.UpdateTimeSetting
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class UpdateViewModel:ViewModel() {

    private val TAG = "UpdateTime_ViewModel"

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("yyyy.MM.dd.E HH:mm:ss")

    private var _updateTime: String = "2022.01.01.토 00:00:00"
    val updateTime get() = _updateTime

    private var updateTimeSetting: UpdateTimeSetting
        = Setting.getInstance().getUpdateTimeDataStore()

    fun getTime() {
        viewModelScope.launch {
            _updateTime = updateTimeSetting.updateTime.first()
        }
    } //업데이트 시간 받아오기

    //업데이트 시간 업데이트는 모델끼리 (서버에서 데이터를 받아올 때마다 업데이트 시키면 됨)

}

