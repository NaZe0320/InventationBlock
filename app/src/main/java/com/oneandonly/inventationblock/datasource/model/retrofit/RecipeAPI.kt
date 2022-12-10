package com.oneandonly.inventationblock.datasource.model.retrofit

import com.oneandonly.inventationblock.datasource.model.data.Recipe
import com.oneandonly.inventationblock.datasource.model.data.RecipeModel
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeAPI {

    @POST("/recipe/{path}")
    suspend fun postRecipe(
        @Header("Token-key") token: String?,
        @Path("path") path: String?,
        @FieldMap param: HashMap<String, Any>
    ) : Response<RecipeModel>

    @GET("/recipe/{path}")
    suspend fun getRecipe(
        @Header("Token-key") token: String?,
        @Path("path") path: String?
    ): Response<RecipeModel>
}