package com.oneandonly.inventationblock.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.oneandonly.inventationblock.datasource.model.LoginModel
import com.oneandonly.inventationblock.datasource.repository.LoginRepository
import com.oneandonly.inventationblock.ui.activity.MainActivity
import kotlin.reflect.KClass

class LoginViewModel:ViewModel() {
    private val _isSucceed = false
    val isSucceed get() = _isSucceed

    private val loginRepository = LoginRepository()

    fun loginCheck(login: LoginModel):Boolean {
        loginRepository.loginCheck(login = login)
        return loginRepository.isSucceed
    }

    fun autoCheck():Boolean {
        return loginRepository.isAuto
    }

}