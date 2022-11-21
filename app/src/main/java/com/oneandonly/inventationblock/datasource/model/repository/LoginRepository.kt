package com.oneandonly.inventationblock.datasource.model.repository

import com.oneandonly.inventationblock.datasource.model.data.USER
import com.oneandonly.inventationblock.datasource.model.retrofit.RetrofitBuilder
import retrofit2.Response

class LoginRepository {

    suspend fun postLogin(params: HashMap<String, String>): Response<USER> {
        return RetrofitBuilder.userAPI.postLogin(params)
    }
}