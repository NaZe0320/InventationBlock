package com.oneandonly.inventationblock.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.oneandonly.inventationblock.datasource.model.data.LoginResult
import com.oneandonly.inventationblock.datasource.model.repository.LoginRepository
import com.oneandonly.inventationblock.datasource.model.retrofit.API
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(private val repository: LoginRepository):ViewModel() {

    private val TAG = "Login_ViewModel"
    var result:Boolean = false

    fun postLogin(param: HashMap<String, String>) {

        repository.postLogin(param).enqueue(object: Callback<LoginResult> {
            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                Log.d(TAG,response.toString())
                Log.d(TAG,response.message().toString())
                Log.d(TAG, response.body()?.message.toString().contains("성공").toString())

                //TODO(response 로 값 변경)
                result = response.body()?.message.toString().contains("성공")
            }
            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                Log.d(TAG,t.message.toString())
                Log.d(TAG,"fail")
            }
        })
    }

}