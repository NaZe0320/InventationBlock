package com.oneandonly.inventationblock.datasource.model.repository

import com.oneandonly.inventationblock.datasource.model.data.LoginResult
import com.oneandonly.inventationblock.datasource.model.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Response

class LoginRepository {

    suspend fun postLogin(params: HashMap<String, String>): Response<LoginResult> {
        return RetrofitBuilder.api.postLogin(params)
    }
}