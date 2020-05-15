package com.appbase.httpbase

import io.reactivex.Observable
import io.reactivex.functions.Function




class HttpResponseFunc<T> : Function<Throwable, Observable<T>> {
    override fun apply(throwable: Throwable): Observable<T> {
        return Observable.error(ExceptionHandle.unifiedError(throwable))
    }
}
