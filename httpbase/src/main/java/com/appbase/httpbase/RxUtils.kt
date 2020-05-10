package com.appbase.httpbase

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
Created by yanzs on 2020/5/10
 */
object RxUtils {
    fun <T> observe(observable: Observable<T>): Observable<T>{
        return observable
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}