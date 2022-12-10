package com.oneandonly.inventationblock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.Constants.tokens
import com.oneandonly.inventationblock.datasource.model.data.Recipe
import com.oneandonly.inventationblock.datasource.model.data.RecipeElement
import com.oneandonly.inventationblock.datasource.model.data.Search
import com.oneandonly.inventationblock.datasource.model.data.State
import com.oneandonly.inventationblock.datasource.model.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(private val repo: RecipeRepository): ViewModel() {

    private val _recipeList = MutableLiveData<ArrayList<RecipeElement>>()
    val recipeList: LiveData<ArrayList<RecipeElement>> get() = _recipeList

    private val _menuList = MutableLiveData<ArrayList<String>>()
    val menuList: LiveData<ArrayList<String>> get() = _menuList

    val ing : MutableLiveData<State> = MutableLiveData()

    fun getRecipeList() {
        try {
            viewModelScope.launch {
                val response = repo.getRecipeList()
                val menuItem: ArrayList<String> = ArrayList()

                when (response.code()) {
                    200 -> {
                        Log.d("getRecipe","${response.code()} / ${response.message()} / ${response.body()?.response}")
                        if (response.body()?.response?.size != null) {
                            for (i in 0 until response.body()?.response?.size!!) {
                                response.body()?.response?.get(i).let {
                                    it!!
                                    menuItem.add(it.name.toString())
                                }

                            }
                        } else {
                            Log.d("getRecipe","${response.errorBody()}")
                        }
                        _menuList.value = menuItem
                    }
                    400 -> {
                        Log.d("getRecipe","${response.code()} / ${response.message()} / ${response.body()?.message}")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setRecipeList(name: String, leastSell: Int, element: ArrayList<RecipeElement> ) {
        try {
            viewModelScope.launch {
                val response = repo.setRecipeList(name, leastSell, element)
                when (response.code()) {
                    200 -> {
                        ing.value = State.Success
                        Log.d("setRecipe","${response.code()} ${response.message()} ${response.body()?.message}")                    }
                    400 -> {
                        ing.value = State.Fail
                        Log.d("setRecipe","${response.code()} ${response.errorBody()?.string()}")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}