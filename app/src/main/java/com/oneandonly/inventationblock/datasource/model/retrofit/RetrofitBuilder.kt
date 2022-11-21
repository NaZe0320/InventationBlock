package com.oneandonly.inventationblock.datasource.model.retrofit

import com.oneandonly.inventationblock.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userAPI: UserAPI by lazy {
        retrofit.create(UserAPI::class.java)
    }

    val stockAPI: StockAPI by lazy {
        retrofit.create(StockAPI::class.java)
    }
}