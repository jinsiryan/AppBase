package com.appbase.httpbase

/**
Created by yanzs on 2020/5/9
 */
class BaseResponse<T> {
    var errorCode: String = ""
    var errorMsg: String = ""
    var result:T? = null
    var success = true
    var timeOut = false
    fun isSucceed() : Boolean{
        return true
    }
}
