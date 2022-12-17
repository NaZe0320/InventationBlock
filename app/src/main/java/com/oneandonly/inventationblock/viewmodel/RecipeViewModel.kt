package com.oneandonly.inventationblock.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneandonly.inventationblock.datasource.model.data.*
import com.oneandonly.inventationblock.datasource.model.repository.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeViewModel(private val repo: RecipeRepository): ViewModel() {

    companion object {
        val searchMenuList: MutableLiveData<ArrayList<Search>> = MutableLiveData<ArrayList<Search>>()
        var searchMenu: ArrayList<Search> = ArrayList()
    }

    private val _recipeList = MutableLiveData<ArrayList<RecipeElement>>()
    val recipeList: LiveData<ArrayList<RecipeElement>> get() = _recipeList

    private val _menuList = MutableLiveData<ArrayList<Menu>>()
    val menuList: LiveData<ArrayList<Menu>> get() = _menuList

    private val _menu = MutableLiveData<Menu>()
    val menu: LiveData<Menu> get() = _menu

    val loading : MutableLiveData<State> = MutableLiveData()
    val enroll : MutableLiveData<State> = MutableLiveData()

    init {
        getRecipeList()
    }

    fun getRecipeList() {
        try {
            viewModelScope.launch {
                val response = repo.getRecipeList()
                val menuItem: ArrayList<Menu> = ArrayList()

                when (response.code()) {
                    200 -> {
                        Log.d("getRecipe","${response.code()} / ${response.message()} / ${response.body()?.response}")
                        if (response.body()?.response?.size != null) {
                            for (i in 0 until response.body()?.response?.size!!) {
                                response.body()?.response?.get(i).let {
                                    it!!
                                    menuItem.add(Menu(it.rid?:0,it.name.toString(),it.leastSell,it.elements))
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
                setSearchList(response)
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
                        enroll.value = State.Success
                        Log.d("Recipe Enroll","${response.code()} ${response.message()} ${response.body()?.message}")
                    }
                    400 -> {
                        enroll.value = State.Fail
                        Log.d("Recipe Enroll","${response.code()} ${response.errorBody()?.string()}")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setRecipeList(rid: Int, name: String, leastSell: Int, element: ArrayList<RecipeElement> ) {
        try {
            viewModelScope.launch {
                val response = repo.modifyRecipeList(rid, name, leastSell, element)
                when (response.code()) {
                    200 -> {
                        enroll.value = State.Success
                        Log.d("Recipe Enroll","${response.code()} ${response.message()} ${response.body()?.message}")
                    }
                    400 -> {
                        enroll.value = State.Fail
                        Log.d("Recipe Enroll","${response.code()} ${response.errorBody()?.string()}")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getRecipeInfo(rid: Int) {
        try {
            viewModelScope.launch {
                val response = repo.getRecipeInformation(rid)
                when (response.code()) {
                    200 -> {
                        Log.d("getRecipeInfo","${response.code()} ${response.message()}")
                        val recipeInfo : ArrayList<RecipeElement> = ArrayList()
                        response.body()?.response?.elements?.let {
                            for (i in it.indices) {
                                recipeInfo.add(it[i])
                            }
                        }
                        Log.d("getRecipeInfo","$recipeInfo")
                        _recipeList.value = recipeInfo


                        _menu.value = Menu(response.body()?.response?.rid?:0,
                                        response.body()?.response?.name?:"",
                                            response.body()?.response?.leastSell,
                                    null)

                        loading.value = State.Success
                        loading.value = State.Null
                    }
                    400 -> {
                        loading.value = State.Fail
                        Log.d("getRecipeInformation","${response.code()} ${response.message()} ${response.body()?.message}")
                        loading.value = State.Null
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getRecipeInfoReturn(rid: Int): ArrayList<RecipeElement>? {
        val recipeInfo : ArrayList<RecipeElement> = ArrayList()
        try {
            viewModelScope.launch {
                val response = repo.getRecipeInformation(rid)
                when (response.code()) {
                    200 -> {
                        Log.d("setRecipeInfoReturn","${response.code()} ${response.message()}")

                        response.body()?.response?.elements?.let {
                            for (i in it.indices) {
                                recipeInfo.add(it[i])
                            }
                        }
                        Log.d("menuSearchTest","getRecipeInfoReturn $recipeInfo")
                        _recipeList.value = recipeInfo

                        loading.value = State.Success
                        loading.value = State.Null
                    }
                    400 -> {
                        loading.value = State.Fail
                        Log.d("setRecipeInformation","${response.code()} ${response.message()} ${response.body()?.message}")
                        loading.value = State.Null
                    }
                }
                delay(1000L)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return recipeInfo
    }

    private fun setSearchList(response: Response<RecipeModel>) {
        val searchListItem: ArrayList<Search> = ArrayList()

        if (response.body()?.response?.size != null) {
            for (i in 0 until response.body()?.response?.size!!) {
                response.body()?.response?.get(i).let {
                    it!!
                    searchListItem.add(
                        Search(
                            it.rid?:0,
                            it.name ?: "0",
                            "menu",
                            null
                        )
                    )
                }
            }
        } else {
            Log.d("Response Test","Search List Error")
        }

        RecipeViewModel.searchMenuList.value = searchListItem
        RecipeViewModel.searchMenu = searchListItem
    }

}