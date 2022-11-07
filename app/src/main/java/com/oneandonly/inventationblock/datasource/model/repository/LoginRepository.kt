package com.oneandonly.inventationblock.datasource.model.repository

import androidx.datastore.preferences.protobuf.Api
import com.oneandonly.inventationblock.datasource.model.data.JSON
import com.oneandonly.inventationblock.datasource.model.retrofit.RetrofitBuilder
import retrofit2.Call
import retrofit2.Response

class LoginRepository {

    suspend fun postLogin(params: HashMap<String, String>): Response<JSON> {
        return RetrofitBuilder.api.postLogin(params)
    }
}