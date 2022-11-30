package com.oneandonly.inventationblock.datasource.model.data


data class Menu(
    var name: String,
    var count: Int,
    var recipe: List<Recipe>?
)

data class Recipe(
    var stockName: String,
    var stockAmount: Int
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

