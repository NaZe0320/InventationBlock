package com.oneandonly.inventationblock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.datasource.model.data.RecipeElement
import com.oneandonly.inventationblock.datasource.model.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(private val repo: RecipeRepository): ViewModel() {

    private val _recipeList = MutableLiveData<ArrayList<RecipeElement>>()
    val recipeList: LiveData<ArrayList<RecipeElement>> get() = _recipeList

    fun getRecipeList() {
/*        try {
            viewModelScope.launch {
                val recipeListItem: ArrayList<Recipe> = ArrayList()
                val response = repo.getRecipeList()

                Log.d("Response Test", "${response.body()?.response}")

                if (response.body()?.response?.size != 0) {
                    for (i in 0 until response.body()?.response?.size!!) {
                        response.body()?.response?.get(i)?.let {
                            recipeListItem.add(it.element?:RecipeElement()
                            )
                        }
                    }
                } else {
                    Log.d("Response Test","Recipe List Error")
                }

                _recipeList.value = recipeListItem

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }*/
    }

    fun setRecipeList(name: String, leastSell: String?, element: ArrayList<RecipeElement> ) {
        try {
            viewModelScope.launch {
                val response = repo.setRecipeList(tokens, name, leastSell.toString(), element)
                when (response.code()) {
                    200 -> {
                        Log.d("setRecipe","400 ${response.message()}")
                    }
                    400 -> {
                        Log.d("setRecipe","400 ${response.errorBody()?.string()}")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}