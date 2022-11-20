package com.oneandonly.inventationblock.datasource.model.data

data class Stock(
    var name:String,
    var stockCurrent: Int,
    var stockSafe: Int,
    var fixed: Boolean,
    var unit: String,
    var expired: Int
)
