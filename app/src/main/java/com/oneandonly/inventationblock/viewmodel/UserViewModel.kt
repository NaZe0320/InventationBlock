package com.oneandonly.inventationblock.viewmodel

import android.system.ErrnoException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.datasource.model.data.State
import com.oneandonly.inventationblock.datasource.model.data.USER
import com.oneandonly.inventationblock.datasource.model.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.IOException

class UserViewModel(private val repository: UserRepository):ViewModel() {

    private val TAG = "User_ViewModel"

    val state: MutableLiveData<State> = MutableLiveData()
    var reason: String? = null // 필요하다면 LiveData로 변경

    var user: MutableLiveData<USER> = MutableLiveData()

    init {
        Log.d(TAG,"User_ViewModel init")
    }

    fun postLogin(id:String, pw: String) {
        val param = HashMap<String, String>()

        param["id"] = id
        param["password"] = pw

        try {
            viewModelScope.launch {
                val response = repository.postUser(params = param, users = "login")
            }
        } catch (e : java.lang.Exception) {
            e.printStackTrace()
        }

    }

    fun postRegister(params: HashMap<String, String>) {
        try {
            viewModelScope.launch {
                val response = repository.postUser(params = params, users = "register")

                Log.d(TAG,"${response.errorBody()?.string()} \n ${response.body()?.response}")
                when (response.code()) {
                    200 -> {
                        Log.d(TAG,"회원가입 성공 ${response.body()}")
                        state.value = State.Success
                    }
                    400 -> {
                        Log.d(TAG,"회원가입 실패 ${response.body()}")
                        reason = response.body()?.message.toString()
                        state.value = State.Fail
                    }
                    else -> {
                        Log.d(TAG,"회원가입 에러 ${response.errorBody()?.string()}")
                        state.value = State.Fail
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ErrnoException) {
            e.printStackTrace()
        }
    }

    fun getInformation() {
        try {
            viewModelScope.launch {
                val response = repository.getInformation(token = tokens)
                Log.d(TAG,"$tokens")
                when (response.code()) {
                    200 -> {
                        Log.d(TAG,"정보 받아오기 성공 ${response.body()} ${response.errorBody()?.string()}")
                        user.value = response.body()
                        state.value = State.Success //임시방편
                    }
                    401 -> {
                        Log.d(TAG,"정보 받아오기 실패1 ${response.body()}")
                        state.value = State.Fail
                    }
                    else -> {
                        Log.d(TAG,"정보 받아오기 실패2 ${response.errorBody()?.string()}")
                        state.value = State.Fail
                    }
                }

            }
        } catch (e : Exception) {
            Log.d(TAG,"${e.message}")
        }
    }

    private fun getData() {

    }
}