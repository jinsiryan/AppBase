package com.boniu.shouyoujiasuqi.thirdsdk.ali

import android.app.Activity
import android.util.Log
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
Created by yanzs on 2020/5/17
 */
class AliManager {
    companion object{
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
//            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
            AliManager()
        }
    }

    fun pay(orderInfo : String,context: Activity) : Observable<PayResult>{
       return Observable.just(orderInfo)
            .map {
                val alipay = PayTask(context)
                val result: Map<String, String> =
                    alipay.payV2(orderInfo, true)
                 PayResult(result)
            }
            .subscribeOn(Schedulers.newThread())

    }
}