package com.oneandonly.inventationblock.viewmodel

import android.system.ErrnoException
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.datasource.model.data.State
import com.oneandonly.inventationblock.datasource.model.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate

class UserViewModel(private val repository: UserRepository):ViewModel() {

    private val TAG = "User_ViewModel"

    val state: MutableLiveData<State> = MutableLiveData()

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
                state.value = State.Loading

                Log.d(TAG,"${response.errorBody()?.string()} \n ${response.body()?.response}")
                when (response.code()) {
                    200 -> {
                        Log.d(TAG,"회원가입 성공 ${response.body()}")
                        state.value = State.Success
                    }
                    400 -> {
                        Log.d(TAG,"회원가입 실패 ${response.body()}")
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
}