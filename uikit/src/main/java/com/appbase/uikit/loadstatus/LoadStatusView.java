package com.appbase.uikit.loadstatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;


public abstract class LoadStatusView implements View.OnClickListener{
    View loadingView;
    View emptyView;
    View failView;
    private BaseQuickAdapter baseQuickAdapter;

    public LoadStatusView(Context context, BaseQuickAdapter baseQuickAdapter){
        LayoutInflater inflate = LayoutInflater.from(context);
        this.baseQuickAdapter = baseQuickAdapter;
        emptyView = inflate.inflate(getEmtpyLayoutId(),null);
        failView = inflate.inflate(getFailLayoutId(),null);
        loadingView = inflate.inflate(getLoadingLayoutId(),null);
        failView.setOnClickListener(this);
    }

    public int getLoadingLayoutId(){
        return ViewLoadStatusUtils.getLoadingLayoutId();
    }
    public int getEmtpyLayoutId(){
        return ViewLoadStatusUtils.getEmtpyLayoutId();
    }
    public int getFailLayoutId(){
        return ViewLoadStatusUtils.getFailLayoutId();
    }

    public View getLoadingView() {
        return loadingView;
    }
    public void setEmptyView(){
        baseQuickAdapter.setEmptyView(emptyView);
    }
    public void setLoadingView(){
        baseQuickAdapter.setEmptyView(loadingView);
    }
    public void setFailView(){
        baseQuickAdapter.setEmptyView(failView);
    }

    @Override
    public void onClick(View v) {
        onLoadFailClick(v);
    }
    public abstract  void onLoadFailClick(View v);
}
