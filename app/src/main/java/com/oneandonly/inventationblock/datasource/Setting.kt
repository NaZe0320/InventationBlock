package com.oneandonly.inventationblock.datasource

import android.app.Application
import androidx.datastore.core.DataStore
import com.oneandonly.inventationblock.datasource.setting.LoginSetting
import com.oneandonly.inventationblock.datasource.setting.TokenSetting
import com.oneandonly.inventationblock.datasource.setting.UpdateTimeSetting

class Setting: Application() {
    private lateinit var loginDataStore: LoginSetting
    private lateinit var updateTimeDataStore: UpdateTimeSetting
    private lateinit var tokenDataStore: TokenSetting

    companion object {
        private lateinit var setting: Setting
        fun getInstance() : Setting = setting
    }

    override fun onCreate() {
        super.onCreate()
        setting = this
        loginDataStore = LoginSetting(this)
        updateTimeDataStore = UpdateTimeSetting(this)
        tokenDataStore = TokenSetting(this)
    }

    fun getLoginDataStore(): LoginSetting = loginDataStore
    fun getUpdateTimeDataStore(): UpdateTimeSetting = updateTimeDataStore
    fun getTokenDataStore(): TokenSetting = tokenDataStore
}
