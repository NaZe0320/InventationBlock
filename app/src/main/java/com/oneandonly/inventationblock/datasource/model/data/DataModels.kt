package com.oneandonly.inventationblock.datasource.model.data

import com.google.gson.annotations.SerializedName

data class Ingredient(
    var name: String,
    var unit: String?,
    var amount: Int?,
    var safe_amount: Int?,
    var expired: Int,
    var fixed: Boolean = false
)

data class LoginModel(
    var id: String,
    var pw: String
)

data class LoginResult(
    var path: String?,
    var result: String?,
    var message: String?,
    var response: Any?,
    var token: String?,
    var id: String?,
    var pw: String?
)

data class InformationResult(
    var information: Any
)