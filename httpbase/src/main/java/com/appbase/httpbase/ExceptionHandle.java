package com.appbase.httpbase;

import android.net.ParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import retrofit2.HttpException;

/**
 * Created by yanzs on 2020/4/26
 */
public class ExceptionHandle {
    public static ApiException unifiedError(Throwable t){
        ApiException ex;
        if (t instanceof JSONException
                || t instanceof ParseException) {
            ex = new ApiException(ErrorType.PARSE_ERROR, "数据解析错误");//均视为解析错误

        } else if (t instanceof ConnectException || t instanceof SocketTimeoutException || t
                instanceof ConnectTimeoutException) {
            ex = new ApiException(ErrorType.HTTP_ERROR, "连接失败,请稍后重试"); //均视为连接错误

        } else if (t instanceof HttpException) {
            ex = new ApiException(ErrorType.NETWORK_ERROR, "网络异常,请稍后重试");//均视为网络错误
        } else {
            ex = new ApiException(ErrorType.UNKONW, "未知错误");
        }
        return ex;
    }
    public static <T> Observable<BaseResponse<T>> handResult(BaseResponse<T> t){
        if (t.isSucceed()) {
            return Observable.just(t);
        }
        return Observable.error(new ApiException(0,""));
    }

}
