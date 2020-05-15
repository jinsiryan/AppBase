package com.appbase.uikit

import android.app.Application
import android.content.Context
import com.appbase.uikit.exception.ExceptionHandler
import com.appbase.uikit.utils.PreferenceManager
import com.facebook.drawee.backends.pipeline.Fresco
import rx_activity_result2.RxActivityResult

/**
Created by yanzs on 2020/5/10
 */
object UIKitHelper {
    fun init(context: Context){
        Fresco.initialize(context)
        ExceptionHandler.init(context)
        PreferenceManager.init(context)
        RxActivityResult.register(context.applicationContext as Application)
    }
}