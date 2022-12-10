package com.oneandonly.inventationblock.datasource.model.repository

import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.datasource.model.data.RecipeElement
import com.oneandonly.inventationblock.datasource.model.data.RecipeModel
import com.oneandonly.inventationblock.datasource.model.retrofit.RetrofitBuilder
import retrofit2.Response

class RecipeRepository {

    suspend fun setRecipeList(token: String?, name: String, leastSell: String, elements: ArrayList<RecipeElement>): Response<RecipeModel> {
        val params = HashMap<String, Any>()
        params["name"] = name
        params["leastSell"] = leastSell
        params["elements"] = elements
        return RetrofitBuilder.recipeAPI.postRecipe(tokens,"create",params)
    }

    suspend fun getRecipeList(): Response<RecipeModel> {
        return RetrofitBuilder.recipeAPI.getRecipe(tokens, "list")
    }
}