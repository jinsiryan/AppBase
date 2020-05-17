package com.base.thirdsdk.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.appbase.uikit.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeChatManager.Companion.getInstance().handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e("WECHAT_PAY", String.valueOf(resp));
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    ToastUtil.showToast(this, "支付失败");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    ToastUtil.showToast(this, "支付取消");
                    break;
                case BaseResp.ErrCode.ERR_OK:
                    ToastUtil.showToast(this, "支付成功");
                    break;
                default:
                    ToastUtil.showToast(this, "支付失败");
                    break;

            }
            finish();
        }
    }
}