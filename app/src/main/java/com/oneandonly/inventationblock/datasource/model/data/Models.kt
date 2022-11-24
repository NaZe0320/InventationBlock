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
    var expired: Long
)

data class History(
    var date: String,
    var content: String,
    var amount: String
)

