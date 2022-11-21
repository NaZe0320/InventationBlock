package com.oneandonly.inventationblock.datasource.model.retrofit

import com.oneandonly.inventationblock.datasource.model.data.StockModel
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface StockAPI {
    @GET("/stock/{path}")
    suspend fun getList(
        @Header("Token-key") token:String?,
        @Path("path") path: String,
        @Query("orderBy") orderBy: Int): Response<StockModel>
    //토큰을 전송하고, 해당 정보 받아오기
}