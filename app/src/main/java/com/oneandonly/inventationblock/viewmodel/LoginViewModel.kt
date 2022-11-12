package com.oneandonly.inventationblock.viewmodel

import android.system.ErrnoException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.datasource.model.data.LoginState
import com.oneandonly.inventationblock.datasource.model.repository.LoginRepository
import kotlinx.coroutines.*


class LoginViewModel(private val repository: LoginRepository):ViewModel() {

    private val TAG = "Login_ViewModel"

    val loginResult: MutableLiveData<LoginState> = MutableLiveData()
    val token: MutableLiveData<String> = MutableLiveData()

    fun postLogin(id: String, pw: String) {
        val param = HashMap<String, String>()

        param["id"] = id
        param["password"] = pw

        try {
            viewModelScope.launch {
                val response = repository.postLogin(param)
                loginResult.value = LoginState.Loading //로그인 중

                when (response.code()) {
                    200 -> {
                        token.value = response.body()?.response?.token.toString()
                        loginResult.value = LoginState.Success //성공처리
                        Log.d(TAG,"Login Success ${response.body()} / ${token.value}")
                    }
                    400 -> {
                        loginResult.value = LoginState.Fail
                        Log.d(TAG,"Login Fail1 ${response.body()}")
                    }
                    else -> {
                        loginResult.value = LoginState.Fail
                        Log.d(TAG,"Login Error ${response.errorBody()?.string()}")
                    }
                }
            }
        } catch (e: ErrnoException) {
            e.printStackTrace()
        }

    }


    override fun onCleared() {
        Log.d("LoginViewModel Test","onCleared")
        super.onCleared()
    }
}