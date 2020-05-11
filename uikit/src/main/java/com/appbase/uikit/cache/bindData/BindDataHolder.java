package com.appbase.uikit.cache.bindData;
import com.appbase.uikit.cache.IBindHolder;
import java.util.UUID;

public class BindDataHolder implements IBindHolder {
    protected long id;
    String key = UUID.randomUUID().toString();
    public BindDataHolder(){

    }
    @Override
    public void onBindData(Object data) {

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
