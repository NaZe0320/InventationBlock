package com.oneandonly.inventationblock.datasource.model.repository

import com.oneandonly.inventationblock.datasource.model.data.StockModel
import com.oneandonly.inventationblock.datasource.model.retrofit.RetrofitBuilder
import retrofit2.Response

class StockRepository {

    suspend fun getList(token: String?, path: String, orderBy: Int) : Response<StockModel> {
        return RetrofitBuilder.stockAPI.getList(token, path, orderBy)
    }

    //TODO(수정 필요)
}