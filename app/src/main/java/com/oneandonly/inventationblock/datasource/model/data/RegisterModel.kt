package com.oneandonly.inventationblock.datasource.model.data

data class RegisterModel(
    var id: String,
    var hint: String,
    var inputType: Int,
    var content: String = "",
    var comment: String = ""
)
