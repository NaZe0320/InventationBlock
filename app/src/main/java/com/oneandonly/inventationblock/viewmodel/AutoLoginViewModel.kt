package com.oneandonly.inventationblock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.datasource.Setting
import com.oneandonly.inventationblock.datasource.setting.LoginSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AutoLoginViewModel:ViewModel() {

    private var _isAutoLogin: Boolean = false
    val isAutoLogin get() = _isAutoLogin

    private var loginSetting: LoginSetting
            = Setting.getInstance().getLoginDataStore()

    fun getAutoLogin() {
        viewModelScope.launch {
            _isAutoLogin = loginSetting.isAutoLogin.first()
        }
    }//값 보여주기(출력)

    fun updateAutoLogin(isAutoLogin: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            loginSetting.saveToDataStore(isAutoLogin)
        }
    } //값 업데이트(입력)
}