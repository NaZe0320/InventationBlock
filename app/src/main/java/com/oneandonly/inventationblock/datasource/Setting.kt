package com.oneandonly.inventationblock.datasource

import android.app.Application
import com.oneandonly.inventationblock.datasource.setting.AutoLoginSetting
import com.oneandonly.inventationblock.datasource.setting.UpdateTimeSetting

class Setting: Application() {
    private lateinit var loginDataStore: AutoLoginSetting
    private lateinit var updateTimeDataStore: UpdateTimeSetting

    companion object {
        private lateinit var setting: Setting
        fun getInstance() : Setting = setting
    }

    override fun onCreate() {
        super.onCreate()
        setting = this
        loginDataStore = AutoLoginSetting(this)
        updateTimeDataStore = UpdateTimeSetting(this)
    }

    fun getLoginDataStore(): AutoLoginSetting = loginDataStore
    fun getUpdateTimeDataStore(): UpdateTimeSetting = updateTimeDataStore
}