package com.oneandonly.inventationblock.datasource.model.data

data class RegisterModel(
    var num: Int,
    var id: String,
    var hint: String,
    var inputType: Int,
    var content: String?
) {
}