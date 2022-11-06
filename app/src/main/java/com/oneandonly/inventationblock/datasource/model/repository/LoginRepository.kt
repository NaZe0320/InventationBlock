package com.oneandonly.inventationblock.datasource.model.repository

import com.oneandonly.inventationblock.datasource.model.data.LoginResult
import com.oneandonly.inventationblock.datasource.model.retrofit.RetrofitBuilder
import retrofit2.Call

class LoginRepository {

    fun postLogin(params: HashMap<String, String>): Call<LoginResult> {
        return RetrofitBuilder.api.postLogin(params)
    }
}