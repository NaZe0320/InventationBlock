package com.oneandonly.inventationblock.datasource.model.data

data class USER(
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

enum class State {
    Loading(),
    Success(),
    Fail()
}