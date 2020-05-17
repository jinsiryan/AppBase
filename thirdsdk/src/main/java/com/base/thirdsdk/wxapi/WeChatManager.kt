package com.base.thirdsdk.wxapi

import android.content.Intent
import com.appbase.uikit.UIKitHelper
import com.base.thirdsdk.SdkConfig
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.jakewharton.rxrelay2.PublishRelay
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.reactivex.Observable

/**
Created by yanzs on 2020/5/17
 */
class WeChatManager {
    companion object{
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            WeChatManager()
        }
    }

    var WXAPI: IWXAPI

    val payRelay = PublishRelay.create<WeChatInfo>()
    init {
        WXAPI = WXAPIFactory.createWXAPI(UIKitHelper.context, SdkConfig.WX_APP_ID, true)
        WXAPI.registerApp(SdkConfig.WX_APP_ID)
    }
    fun handleIntent(intent :Intent, handler : IWXAPIEventHandler){
        WXAPI.handleIntent(intent,handler)
    }

    fun onResp(resp : BaseResp){
        payRelay.accept(WeChatInfo(resp))
    }
    fun paserTo(order:String):PayReq{
        val map = JsonParser().parse(order) as JsonObject
        val req = PayReq()
        req.appId = map.get("appid").asString
        req.nonceStr = map.get("noncestr").asString
        req.packageValue = map.get("package").asString
        req.prepayId = map.get("prepayid").asString
        req.sign = map.get("sign").asString
        req.partnerId = map.get("partnerid").asString
        req.timeStamp = map.get("timestamp").asString
        return req
    }
    fun pay(req: PayReq): Observable<WeChatInfo>{
        WXAPI.sendReq(req)
        return payRelay.take(1)
    }
    fun pay2(order:String):Observable<WeChatInfo>{
        return pay(paserTo(order))
    }
}