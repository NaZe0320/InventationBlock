package com.oneandonly.inventationblock.datasource.repository

import com.oneandonly.inventationblock.datasource.model.LoginModel

class LoginRepository {

    private var _isSucceed = false //로그인 성공 여부
    private var _isAuto = false //자동 로그인 여부
    val isSucceed get() = _isSucceed
    val isAuto get() = _isAuto

    fun loginCheck(login: LoginModel) {
        //TODO(서버에 login 전달 후에 )
        _isSucceed = true
    }

    fun autoCheck() {
        //TODO(DataStore 에서 자동로그인 여부 가져오기)
        _isAuto = false
    }
}