package com.oneandonly.inventationblock.datasource.model.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.InstanceCreator
import com.oneandonly.inventationblock.Constants.Companion.BASE_URL
import com.oneandonly.inventationblock.datasource.model.data.LoginResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: API by lazy {
        retrofit.create(API::class.java)
    }
}