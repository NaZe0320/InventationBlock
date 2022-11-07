package com.oneandonly.inventationblock.datasource.model.data

import com.google.gson.annotations.SerializedName

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

enum class LoginState {
    Loading(),
    Success(),
    Fail()
}

data class InformationResult(
    var information: Any
)