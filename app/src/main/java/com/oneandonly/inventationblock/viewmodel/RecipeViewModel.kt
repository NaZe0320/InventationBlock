package com.oneandonly.inventationblock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.datasource.model.data.Recipe
import com.oneandonly.inventationblock.datasource.model.data.RecipeElement
import com.oneandonly.inventationblock.datasource.model.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(private val repo: RecipeRepository): ViewModel() {

    private val _recipeList = MutableLiveData<ArrayList<RecipeElement>>()
    val recipeList: LiveData<ArrayList<RecipeElement>> get() = _recipeList

    private val _menuList = MutableLiveData<ArrayList<String>>()
    val menuList: LiveData<ArrayList<String>> get() = _menuList

    fun getRecipeList() {

    }

    fun setRecipeList(name: String, leastSell: Int, element: ArrayList<RecipeElement> ) {
        try {
            viewModelScope.launch {
                val response = repo.setRecipeList(name, leastSell, element)
                when (response.code()) {
                    200 -> {
                        Log.d("setRecipe","${response.code()} ${response.message()} ${response.body()?.message}")                    }
                    400 -> {
                        Log.d("setRecipe","${response.code()} ${response.errorBody()?.string()}")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}