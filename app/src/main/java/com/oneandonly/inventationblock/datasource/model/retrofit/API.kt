package com.oneandonly.inventationblock.datasource.model.retrofit

import com.oneandonly.inventationblock.datasource.model.data.JSON
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface API {

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun postLogin(
        @FieldMap params: HashMap<String, String>): Response<JSON>
    //Login 정보 서버로 전달
/*
    @GET("/user/information")
    fun getInformation(
        @Header("Token-key") token:String?): InformationResult
    //토큰을 전송하고, 해당 정보 받아오기
*/

}