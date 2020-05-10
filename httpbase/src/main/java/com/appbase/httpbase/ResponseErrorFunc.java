package com.appbase.httpbase;


import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by yanzs on 2020/4/26
 */
public class ResponseErrorFunc<T> implements Function<BaseResponse<T>, Observable<BaseResponse<T>>> {
    @Override
    public Observable<BaseResponse<T>> apply(BaseResponse<T> t) throws Exception {
        if (t.isSucceed()) {
            return Observable.just(t);
        }
        return Observable.error(new ApiException(0,t.getErrorMsg()));
    }
}
