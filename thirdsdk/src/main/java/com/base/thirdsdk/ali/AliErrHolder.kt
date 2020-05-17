package com.base.thirdsdk.ali

import android.content.Context
import com.appbase.uikit.utils.ToastUtil

/**
Created by yanzs on 2020/5/17
 */
class AliErrHolder(var payResult: PayResult) {
    fun showMsg(context: Context){
        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
        ToastUtil.showToast(context,payResult.memo)
    }
}