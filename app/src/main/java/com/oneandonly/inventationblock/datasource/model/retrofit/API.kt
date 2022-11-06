package com.oneandonly.inventationblock.datasource.model.retrofit

import com.oneandonly.inventationblock.datasource.model.data.InformationResult
import com.oneandonly.inventationblock.datasource.model.data.LoginResult
import retrofit2.Call
import retrofit2.http.*

interface API {

    @FormUrlEncoded
    @POST("/user/login")
    fun postLogin(
        @FieldMap params: HashMap<String, String>): Call<LoginResult>
    //Login 정보 서버로 전달

    @GET("/user/information")
    fun getInformation(
        @Header("Token-key") token:String?): Call<InformationResult>
    //토큰을 전송하고, 해당 정보 받아오기

}