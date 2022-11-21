package com.oneandonly.inventationblock.datasource.model.data

import java.util.Date

data class USER(
    var result: String?,
    var message: String?,
    var response: UserResponses?
)

data class UserResponses(
    var uid: String?,
    var id: String?,
    var name: String?,
    var email: String?,
    var businessName: String?,
    var businessNum: String?,
    var token: String?
)

data class StockModel(
    var result: String?,
    var message: String?,
    var response: List<StockResponses?>
)

data class StockResponses(
    var name: String?,
    var amount: Int?,
    var addDate: Any?,
    var unit: String?,
    var safeStandard: Int?,
    var pinned: Int?
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