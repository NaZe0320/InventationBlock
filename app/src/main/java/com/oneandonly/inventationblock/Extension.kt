package com.oneandonly.inventationblock

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.oneandonly.inventationblock.datasource.model.data.State

fun Context.makeToast(text:String) {
    Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
}

fun Context.afterUpdate() {
    Toast.makeText(this,"업데이트 예정입니다",Toast.LENGTH_SHORT).show()
}

/*
fun <T: ViewModel> stateObserving(modelClass: Class<T>, state:LiveData<State>, ) {

}*/
