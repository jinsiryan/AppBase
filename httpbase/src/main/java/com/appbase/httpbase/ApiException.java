package com.appbase.httpbase;

/**
 * Created by Administrator on 2017/8/29.
 */

public class ApiException extends RuntimeException {

    private static final String TAG = "ApiException";
    private int errCode;
    private String errMsg;

    public ApiException(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;

    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

}
