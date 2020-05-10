package com.appbase

import android.app.Application
import com.appbase.easy.EasyHelper

/**
Created by yanzs on 2020/5/10
 */
class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        EasyHelper.init(this)
    }
}