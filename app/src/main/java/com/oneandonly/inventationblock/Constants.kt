package com.oneandonly.inventationblock

import com.oneandonly.inventationblock.datasource.model.data.RegisterModel

object Constants {
    const val BASE_URL = "https://api.yukaigames.com/"
    var tokens: String? = null

    fun registerList(): ArrayList<RegisterModel>{
        val registerList = ArrayList<RegisterModel>()

        registerList.add(RegisterModel(1,"id","아이디",1,null))
        registerList.add(RegisterModel(2,"password","비밀번호",81,null))
        registerList.add(RegisterModel(3,"passwordCheck","비밀번호 확인",81,null))
        registerList.add(RegisterModel(4,"name","사용자명",1,null))
        registerList.add(RegisterModel(5,"email","이메일",21,null))
        registerList.add(RegisterModel(6,"businessName","사업자명",1,null))
        registerList.add(RegisterModel(7,"businessNum","사업자등록번호",2,null))

        return registerList
    }
}