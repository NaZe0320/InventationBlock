package com.oneandonly.inventationblock.datasource.model.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.datasource.model.data.RecipeElement
import com.oneandonly.inventationblock.datasource.model.data.RecipeModel
import com.oneandonly.inventationblock.datasource.model.retrofit.RetrofitBuilder
import org.json.JSONException
import retrofit2.Response


class RecipeRepository {

    suspend fun setRecipeList(name: String, leastSell: Int, elements: ArrayList<RecipeElement>): Response<RecipeModel> {
        var gson = GsonBuilder().create()
        val params = HashMap<String, Any>()
        try {
            val jsonData =
                Gson().toJsonTree(elements, object : TypeToken<ArrayList<RecipeElement>>() {}.type)
            val arrayData = jsonData.asJsonArray
            params["name"] = name
            params["leastSell"] = leastSell
            params["elements"] = arrayData
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return RetrofitBuilder.recipeAPI.postRecipe(tokens,"create",params)
    }


    suspend fun getRecipeList(): Response<RecipeModel> {
        return RetrofitBuilder.recipeAPI.getRecipe(tokens, "list")
    }

    suspend fun getRecipeInformation(rid : Int): Response<RecipeModel> {
        return RetrofitBuilder.recipeAPI.getRecipeInformation(tokens,rid)
    }
}