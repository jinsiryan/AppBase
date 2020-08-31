package com.base.thirdsdk

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import com.appbase.httpbase.Config
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

/**
Created by yanzs on 2020/6/30
 */
class ThirdSdkHelper {

    companion object {
        const val Ument_app_key = "xx"
        fun init(context: Context) {
            // 初始化SDK
            UMConfigure.init(
                context,
                Ument_app_key,
                "",
                UMConfigure.DEVICE_TYPE_PHONE,
                null
            )
            // 选用AUTO页面采集模式
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
            getChannel(context)?.let {
                Config.CHANNEL = it
            }
        }

        fun getChannel(context: Context): String? {
            try {
                val info = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                return info.metaData.getString("UMENG_CHANNEL")
            } catch (e: Exception) {
            }
            return null
        }
    }
}