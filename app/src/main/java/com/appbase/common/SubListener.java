package com.appbase.common;

import com.appbase.httpbase.BaseExceptionHolder;

/**
 * Created by yanzs on 2020/5/16
 */
public interface SubListener<T> {
    void onNext(T t);

    default void onCompleted(){}
    default void onError(BaseExceptionHolder errHolder) { }
}
