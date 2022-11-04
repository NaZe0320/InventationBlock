package com.oneandonly.inventationblock.datasource

import android.app.Application
import com.oneandonly.inventationblock.datasource.repository.LoginRepository

class Setting: Application() {
    private lateinit var loginDataStore: LoginRepository

    companion object {
        private lateinit var setting: Setting
        fun getInstance() : Setting = setting
    }

    override fun onCreate() {
        super.onCreate()
        setting = this
        loginDataStore = LoginRepository(this)

    }

    fun getLoginDataStore(): LoginRepository = loginDataStore
}