package com.oneandonly.inventationblock.datasource.model.repository

import android.util.Log
import com.oneandonly.inventationblock.datasource.model.data.StockModel
import com.oneandonly.inventationblock.datasource.model.retrofit.RetrofitBuilder
import retrofit2.Response

class StockRepository {

    suspend fun getList(token: String?, orderBy: Int?) : Response<StockModel> {
        return RetrofitBuilder.stockAPI.getList(token,orderBy)
    }

    suspend fun getSearchList(token: String?,search: String?) : Response<StockModel> {
        return RetrofitBuilder.stockAPI.getSearchList(token, search)
    }

    suspend fun getHistoryList(token: String?, sid: Int?): Response<StockModel> {
        return RetrofitBuilder.stockAPI.getHistoryList(token,sid)
    }

    suspend fun setTogglePin(token: String?,sid: Int?): Response<StockModel> {
        val params = HashMap<String, Int>()
        params["sid"] = sid?:0
        return RetrofitBuilder.stockAPI.setStock(token,"togglePin",params)
    }

    suspend fun setSafeAmount(token: String?, sid: Int?, amount: Int?) : Response<StockModel> {
        val params = HashMap<String, Int>()
        params["sid"] = sid?:0
        params["amount"] = amount?:0
        return RetrofitBuilder.stockAPI.setStock(token,"setSafeStandard",params)
    }

    suspend fun addAmount(token: String?, sid: Int?, amount: Int?) : Response<StockModel> {
        val params = HashMap<String, Int>()
        params["sid"] = sid?:0
        params["amount"] = amount?:0
        return RetrofitBuilder.stockAPI.setStock(token,"setSafeStandard",params)
    }


    //TODO(수정 필요)
}