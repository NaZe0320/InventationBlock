package com.oneandonly.inventationblock.datasource.model.retrofit

import com.google.gson.JsonArray
import com.oneandonly.inventationblock.datasource.model.data.RecipeModel
import retrofit2.Response
import retrofit2.http.*

interface RecipeAPI {

    @FormUrlEncoded
    @POST("/recipe/{path}")
    suspend fun postRecipe(
        @Header("Token-key") token: String?,
        @Path("path") path: String?,
        @FieldMap params: Map<String, @JvmSuppressWildcards Any>
    ) : Response<RecipeModel>


    @GET("/recipe/{path}")
    suspend fun getRecipe(
        @Header("Token-key") token: String?,
        @Path("path") path: String?
    ): Response<RecipeModel>
}