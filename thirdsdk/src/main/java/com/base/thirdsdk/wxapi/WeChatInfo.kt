package com.base.thirdsdk.wxapi

import com.tencent.mm.opensdk.modelbase.BaseResp

/**
Created by yanzs on 2020/5/17
 */
class WeChatInfo(var resp: BaseResp) {
    fun isSucceed() : Boolean{
        return BaseResp.ErrCode.ERR_OK == resp.errCode
    }
}