package com.appbase.uikit.cache.bindData;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.kook.view.cache.IBindHolder;

import java.util.UUID;

public class BindDataViewHolder extends BaseViewHolder implements IBindHolder {
    private String key = null;
    protected long id;
    public BindDataViewHolder(View itemView) {
        super(itemView);
        key = UUID.randomUUID().toString();
    }


    @Override
    public void onBindData(Object data) {


    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * 调用这个方法，会立刻获取数据，若有数据返回会调用 onBindData
     * @param cls
     * @param id
     */
    public void bindData(Class<?> cls, long id){
        this.id = id;
       BindDataViewManger.bindAndGetDataViewHolder(cls,this,id);
    }

    @Override
    public String getKey() {
        return key;
    }
}
