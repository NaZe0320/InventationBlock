package com.oneandonly.inventationblock.datasource.setting

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException



class AutoLoginSetting(private val context: Context) {

    companion object {
        private val AUTO_LOGIN_KEY = booleanPreferencesKey("IS_AUTO_LOGIN")
    }

    private val TAG = "Login_Repository"

    private val Context.dataStore by preferencesDataStore("AUTO_LOGIN")
    private var isSucceed: Boolean = false

    suspend fun saveToDataStore(isChecked: Boolean) {
        context.dataStore.edit {
            it[AUTO_LOGIN_KEY] = isChecked
        }
    } //값 쓰기

    val isAutoLogin: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if(exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            return@map it[AUTO_LOGIN_KEY]?:false
        }
    // 값 읽기





}