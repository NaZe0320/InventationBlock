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
    var sid: Int?,
    var name: String?,
    var amount: Int?,
    var oldestDate: Date?,
    var unit: String?,
    var safeStandard: Int?,
    var pinned: Int?,
    var date: Date?,
    var reason: String?
)

data class RecipeModel(
    var result: Int?,
    var message: String?,
    var response: List<RecipeResponse?>?
)

data class RecipeResponse(
    var rid: Int?,
    var name: String?,
    var leastSell: Int?,
    var elements: List<RecipeElement>?
)

data class RecipeModel2(
    var result: Int?,
    var message: String?,
    var response: RecipeResponse2?
)

data class RecipeResponse2(
    var rid: Int?,
    var name: String?,
    var leastSell: Int?,
    var elements: List<RecipeElement>?
)

data class RecipeElement(
    var sid: Int?,
    var name: String ?= "",
    var amount:Int ?= 0,
    var unit: String ?="",
    var amountTotal: Int?,
    var date: Date?,
    var safeStandard: Int?,
    var pinned: Int?
)

data class UpdateModel(
    var result: Int?,
    var message: String?,
    var response: Update?
)
data class Update(
    var date: Date?,
    var toString: String?
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
    Fail(),
    Null()
}