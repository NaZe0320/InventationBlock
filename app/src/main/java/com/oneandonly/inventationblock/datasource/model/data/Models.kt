package com.oneandonly.inventationblock.datasource.model.data

import com.google.gson.annotations.SerializedName

data class JSON(
    var result: String?,
    var message: String?,
    var response: Responses?
)

data class Responses(
    var token: String?
)





enum class LoginState {
    Loading(),
    Success(),
    Fail()
}
