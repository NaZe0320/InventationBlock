package com.oneandonly.inventationblock.datasource.model.data


data class Menu(
    var rid: Int,
    var name: String,
    var count: Int?,
    var recipe: List<RecipeElement>?
)

data class Recipe(
    var name: String = "",
    var amount: String = "",
    var unit: String = "",
    var sid: Int? = null
)

data class Stock(
    var name:String,
    var stockCurrent: Int,
    var stockSafe: Int,
    var fixed: Boolean,
    var unit: String,
    var expired: Long,
    var sid: Int
)

data class History(
    var date: String,
    var content: String,
    var amount: String,
    var pm: Boolean = true,
)

data class Search(
    var sid: Int,
    var name: String,
    var type: String,
    var unit: String? = ""
)

