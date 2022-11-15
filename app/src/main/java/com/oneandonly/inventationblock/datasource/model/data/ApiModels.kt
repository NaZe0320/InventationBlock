package com.oneandonly.inventationblock.datasource.model.data

data class USER(
    var result: String?,
    var message: String?,
    var response: Responses?
)

data class Responses(
    var uid: String?,
    var id: String?,
    var name: String?,
    var email: String?,
    var businessName: String?,
    var businessNum: String?,
    var token: String?
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