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

    private val _recipeList = MutableLiveData<ArrayList<Recipe>>()
    val recipeList: LiveData<ArrayList<Recipe>> get() = _recipeList

    fun getRecipeList() {
        try {
            viewModelScope.launch {
                val response = repo.getRecipeList()

                Log.d("Response Test", "${response.body()?.response}")

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}