package com.oneandonly.inventationblock.datasource.model.repository

import com.oneandonly.inventationblock.datasource.model.data.StockModel
import com.oneandonly.inventationblock.datasource.model.retrofit.RetrofitBuilder
import retrofit2.Response

class StockRepository {

    suspend fun getList(token: String?, orderBy: Int) : Response<StockModel> {
        return RetrofitBuilder.stockAPI.getList(token,orderBy)
    }

    suspend fun getSearchList(token: String?,search: String) : Response<StockModel> {
        return RetrofitBuilder.stockAPI.getSearchList(token, search)
    }

    //TODO(수정 필요)
}