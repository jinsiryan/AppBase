package com.base.shouyoujiasuqi.common

import android.content.Context
import com.appbase.common.SubListener
import com.appbase.httpbase.BaseExceptionHolder
import com.appbase.uikit.dialog.LoadingDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
Created by yanzs on 2020/5/16
 */
class ProgressSubscriber<T>(var context: Context, var listener: SubListener<T>? = null) : Observer<T> {

    override fun onComplete() {
        LoadingDialog.dismissDialog()
        listener?.onCompleted()
    }

    override fun onSubscribe(d: Disposable) {
        LoadingDialog.showLoading(context)
    }

    override fun onNext(t: T) {
        listener?.onNext(t)
    }
    open fun getErrHolder(e: Throwable):BaseExceptionHolder{
        return BaseExceptionHolder(e)
    }
    override fun onError(e: Throwable) {
        val errHolder = getErrHolder(e)
        errHolder.showErrMsg(context)
        LoadingDialog.dismissDialog()
        listener?.onError(errHolder)
    }
}