package com.oneandonly.inventationblock.datasource.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.oneandonly.inventationblock.datasource.model.LoginModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Auto_Login")

class LoginRepository(private val context: Context) {

    private val TAG = "Login_Repository"

    private var isSucceed: Boolean = false

    companion object {
        private val AUTO_LOGIN_KEY = booleanPreferencesKey("IS_AUTO_LOGIN")
    }

    init {
        Log.d(TAG,"생성 확인")
    }

    private val Context.dataStore by preferencesDataStore("AUTO_LOGIN")

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

    fun loginCheck(login: LoginModel): Boolean {
        Log.d(TAG,"LoginCheck")
        //TODO(서버에 login 전달 후에 리턴 값 받기)
        isSucceed = true

        return isSucceed
    }





}