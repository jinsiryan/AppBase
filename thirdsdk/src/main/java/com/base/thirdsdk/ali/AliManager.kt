package com.base.thirdsdk.ali

import android.app.Activity
import android.content.Context
import com.alipay.sdk.app.PayTask
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
Created by yanzs on 2020/5/17
 */
class AliManager {
    companion object{
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
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