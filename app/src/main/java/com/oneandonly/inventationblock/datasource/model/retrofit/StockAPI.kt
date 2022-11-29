package com.oneandonly.inventationblock.datasource.model.retrofit

import com.oneandonly.inventationblock.datasource.model.data.StockModel
import retrofit2.Response
import retrofit2.http.*

interface StockAPI {
    @GET("/stock/list")
    suspend fun getList(
        @Header("Token-key") token: String?,
        @Query("orderBy") orderBy: Int?
    ): Response<StockModel>
    //토큰을 전송하고, 해당 정보 받아오기

    @GET("/stock/search")
    suspend fun getSearchList(
        @Header("Token-key") token: String?,
        @Query("search") search: String?
    ): Response<StockModel>

    @GET("/stock/history/{sid}")
    suspend fun getHistoryList(
        @Header("Token-key") token: String?,
        @Path("sid") sid: Int?
    ): Response<StockModel>

    @FormUrlEncoded
    @PATCH("/stock/{path}")
    suspend fun setStock(
        @Header("Token-key") token: String?,
        @Path("path") path: String?,
        @FieldMap param: HashMap<String, Any>
    ): Response<StockModel>

}