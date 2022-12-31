package com.oneandonly.inventationblock.datasource.setting

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UpdateTimeSetting(private val context: Context) {

    companion object {
        private val UPDATE_TIME = stringPreferencesKey("UPDATE_TIME")
    }

    private val TAG = "UpdateTime_Repository"
    private val Context.dataStore by preferencesDataStore("UPDATE_TIME")

    suspend fun saveToDataStore(time: String) {
        context.dataStore.edit {
            it[UPDATE_TIME] = time
        }
    } //TODO(시간 업데이트는 서버에서 받아올 때마다 해당 모듈에서 선언하는 걸로)

    val updateTime: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            return@map it[UPDATE_TIME]?:"0"
        }

}