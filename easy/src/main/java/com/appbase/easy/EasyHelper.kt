package com.appbase.easy

import android.content.Context
import com.appbase.uikit.UIKitHelper

/**
Created by yanzs on 2020/5/10
 */
object EasyHelper {
    fun init(context: Context) {
        HttpManager.init(context)
        UIKitHelper.init(context)
    }
}