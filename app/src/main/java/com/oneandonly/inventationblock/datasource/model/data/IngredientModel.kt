package com.oneandonly.inventationblock.datasource.model.data


data class Ingredient(
    var name: String,
    var unit: String?,
    var amount: Int?,
    var safe_amount: Int?,
    var expired: Int,
    var fixed: Boolean = false
)
