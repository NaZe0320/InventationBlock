package com.oneandonly.inventationblock.datasource.model.data

data class USER(
    var result: String?,
    var message: String?,
    var response: Responses?
)

data class Responses(
    var token: String?
)

data class Information(
    var information: Information
)


enum class LoginState {
    Loading(),
    Success(),
    Fail(),
    Null()
}

enum class State {
    Loading(),
    Success(),
    Fail()
}