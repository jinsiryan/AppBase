package com.base.thirdsdk.wxapi;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.appbase.uikit.utils.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = WXEntryActivity.class.getSimpleName();
    // IWXAPI 是第三方app和微信通信的openapi接口

    private static final int RETURN_MSG_TYPE_LOGIN = 1;//登陆
    private static final int RETURN_MSG_TYPE_SHARE = 2;//分享

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        WeChatManager.Companion.getInstance().handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        Log.e("WXEntryActivity", String.valueOf(req));

    }


    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        Log.e("WXEntryActivity",resp.errCode+":"+resp.errStr+";");
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                if (RETURN_MSG_TYPE_SHARE == resp.getType())
                    ToastUtil.showToast(this, "分享失败");
                else if (RETURN_MSG_TYPE_LOGIN == resp.getType())
                    ToastUtil.showToast(this, "登录失败");

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType())
                    ToastUtil.showToast(this, "分享取消");
                else if (RETURN_MSG_TYPE_LOGIN == resp.getType())
                    ToastUtil.showToast(this, "登录取消");
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;//061X2b282DYGIL05hX082Fn1282X2b2l
//                        YuxiangrousiWechat.getInstance().getSignInCallback().onSignInSuccess(code);
                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        //ToastUtil.showToast("分享成功！");
                        break;
                }
                break;

            case BaseResp.ErrCode.ERR_UNSUPPORT:
                break;
            default:
                if (RETURN_MSG_TYPE_SHARE == resp.getType())
                    ToastUtil.showToast(this, "分享失败");
                else if (RETURN_MSG_TYPE_LOGIN == resp.getType())
                    ToastUtil.showToast(this, "登录失败");
                break;
        }
        finish();
    }


}

