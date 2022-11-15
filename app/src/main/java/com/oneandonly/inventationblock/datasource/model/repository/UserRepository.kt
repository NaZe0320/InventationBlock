package com.oneandonly.inventationblock.datasource.model.repository

import com.oneandonly.inventationblock.datasource.model.data.USER
import com.oneandonly.inventationblock.datasource.model.retrofit.RetrofitBuilder
import retrofit2.Response

class UserRepository {

    suspend fun postUser(params: HashMap<String, String>, users: String) : Response<USER> {
        return RetrofitBuilder.api.postUser(params = params, users = users)
    }

    suspend fun getInformation(token: String?) : Response<USER> {
        return RetrofitBuilder.api.getInformation(token)
    }

}