package com.oneandonly.inventationblock.datasource.model.repository

import com.oneandonly.inventationblock.datasource.model.data.InformationResult
import com.oneandonly.inventationblock.datasource.model.retrofit.RetrofitBuilder
import retrofit2.Call

class Repository {

    suspend fun getInformation(token: String?): InformationResult {
        return RetrofitBuilder.api.getInformation(token)
    }
}