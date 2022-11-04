package com.oneandonly.inventationblock.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.datasource.Setting
import com.oneandonly.inventationblock.datasource.model.LoginModel
import com.oneandonly.inventationblock.datasource.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class LoginViewModel(context: Context):ViewModel() {

    private val TAG = "Login_ViewModel"

    var isAutoLogin: Boolean = false
    private var loginRepository: LoginRepository = Setting.getInstance().getLoginDataStore()

    fun getAutoLogin() {
        viewModelScope.launch {
            isAutoLogin = loginRepository.isAutoLogin.first()
        }
    }

    fun updateAutoLogin(isAutoLogin: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepository.saveToDataStore(isAutoLogin)
        }
    }

    fun loginCheck(loginModel: LoginModel) : Boolean {
        Log.d(TAG,"loginCheck $loginModel")
        return loginRepository.loginCheck(login = loginModel)
    }


}