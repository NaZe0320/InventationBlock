package com.oneandonly.inventationblock.datasource.model.retrofit

import com.google.gson.JsonArray
import com.oneandonly.inventationblock.datasource.model.data.RecipeModel
import com.oneandonly.inventationblock.datasource.model.data.RecipeModel2
import retrofit2.Response
import retrofit2.http.*

interface RecipeAPI {

    @FormUrlEncoded
    @POST("/recipe")
    suspend fun postRecipe(
        @Header("Token-key") token: String?,
        @FieldMap params: Map<String, @JvmSuppressWildcards Any>
    ) : Response<RecipeModel>

    @FormUrlEncoded
    @PATCH("/recipe")
    suspend fun patchRecipe(
        @Header("Token-key") token: String?,
        @FieldMap params: Map<String, @JvmSuppressWildcards Any>
    ) : Response<RecipeModel>

    @GET("/recipe/{path}")
    suspend fun getRecipe(
        @Header("Token-key") token: String?,
        @Path("path") path: String?
    ): Response<RecipeModel>

    @GET("/recipe/information/{rid}")
    suspend fun getRecipeInformation(
        @Header("Token-key") token: String?,
        @Path("rid") rid: Int?
    ): Response<RecipeModel2>


}