package com.oneandonly.inventationblock.datasource.setting

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class TokenSetting(private val context: Context) {

    companion object {
        private val TOKEN = stringPreferencesKey("TOKEN")
    }

    private val TAG = "TokenSetting"

    private val Context.dataStore by preferencesDataStore("TOKEN")

    suspend fun savaToken(token: String) {
        context.dataStore.edit {
            it[TOKEN] = token
        }
    }

    val token: Flow<String> = context.dataStore.data
        .catch { ex ->
            if (ex is IOException) {
                emit(emptyPreferences())
            } else {
                throw ex
            }
        }.map {
            return@map it[TOKEN] ?: ""
        }
}



