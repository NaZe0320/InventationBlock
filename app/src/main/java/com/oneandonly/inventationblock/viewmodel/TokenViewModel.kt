package com.oneandonly.inventationblock.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.datasource.Setting
import com.oneandonly.inventationblock.datasource.setting.TokenSetting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TokenViewModel:ViewModel() {

    private var tokenSetting: TokenSetting
        = Setting.getInstance().getTokenDataStore()

    fun getToken() {
        viewModelScope.launch {
            tokens = tokenSetting.token.first()
        }
    } //토큰 전역 변수에 올리기

    fun updateToken(token:String) {
        viewModelScope.launch {
            tokenSetting.savaToken(token)
            getToken()
        }
    } //업데이트 된 토큰 저장 후 전역 변수에 올리기

}