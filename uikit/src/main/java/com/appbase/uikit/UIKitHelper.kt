package com.appbase.uikit

import android.content.Context
import com.appbase.uikit.exception.ExceptionHandler
import com.facebook.drawee.backends.pipeline.Fresco

/**
Created by yanzs on 2020/5/10
 */
object UIKitHelper {
    fun init(context: Context){
        Fresco.initialize(context)
        ExceptionHandler.init(context)
    }
}