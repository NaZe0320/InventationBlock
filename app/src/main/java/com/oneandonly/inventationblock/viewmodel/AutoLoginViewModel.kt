package com.oneandonly.inventationblock.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.datasource.Setting
import com.oneandonly.inventationblock.datasource.setting.AutoLoginSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AutoLoginViewModel:ViewModel() {

    private var _isAutoLogin: Boolean = false
    val isAutoLogin get() = _isAutoLogin

    private var autoLoginSetting: AutoLoginSetting
            = Setting.getInstance().getLoginDataStore()

    fun getAutoLogin() {
        viewModelScope.launch {
            _isAutoLogin = autoLoginSetting.isAutoLogin.first()
        }
    }//값 보여주기(출력)

    fun updateAutoLogin(isAutoLogin: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            autoLoginSetting.saveToDataStore(isAutoLogin)
        }
    } //값 업데이트(입력)
}