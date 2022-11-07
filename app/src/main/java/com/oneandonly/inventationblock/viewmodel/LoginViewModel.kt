package com.oneandonly.inventationblock.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.datasource.model.data.LoginResult
import com.oneandonly.inventationblock.datasource.model.data.LoginState
import com.oneandonly.inventationblock.datasource.model.repository.LoginRepository
import com.oneandonly.inventationblock.datasource.model.retrofit.API
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(private val repository: LoginRepository):ViewModel() {

    private val TAG = "Login_ViewModel"

    val loginResult: MutableLiveData<LoginState> = MutableLiveData()
    val token: MutableLiveData<String> = MutableLiveData()

    fun postLogin(id: String, pw: String) {
        val param = HashMap<String, String>()

        param["id"] = id
        param["password"] = pw

        viewModelScope.launch {
            val response = repository.postLogin(param)
            Log.d(TAG,"response ${response.body()}")

            loginResult.value = LoginState.Loading //로그인 중

            if (response.isSuccessful) {
                if (response.body()?.message.toString().contains("성공")) {
                    token.value = response.message()
                    loginResult.value = LoginState.Success //성공처리
                } else {
                    loginResult.value = LoginState.Fail //실패
                }
            } else {
                Log.d(TAG,"ERROR")
            }
        }
    }

}