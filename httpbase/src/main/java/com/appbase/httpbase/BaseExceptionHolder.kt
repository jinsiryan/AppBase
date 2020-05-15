package com.appbase.httpbase

import android.content.Context
import android.widget.Toast
/**
Created by yanzs on 2020/5/15
 */
class BaseExceptionHolder(throwable: Throwable) {
    var apiException:ApiException? = null
    init {
        if ( throwable is ApiException){
            apiException = throwable as ApiException
        }else{
            apiException = ApiException(-1,throwable.message)
        }
    }
    fun getErrMsg(context: Context):String{
        apiException?.apply {
            return message.toString()
        }
        return ""
    }
    fun showErrMsg(context: Context){
        Toast.makeText(context,getErrMsg(context),Toast.LENGTH_SHORT)
    }
}