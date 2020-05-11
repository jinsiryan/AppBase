package com.appbase.uikit.cache.bindData;


import com.appbase.uikit.cache.IBindHolder;
import com.appbase.uikit.cache.ViewCachePool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 绑定data与view的关系并实时通知
 */
public class BindDataViewManger {
    static Map<Class<?>,IBindGetData> bindDataMap = new ConcurrentHashMap<>();

    public static void unregisterBind(Class bindClass) {
        bindDataMap.remove(bindClass);
    }

    public interface IBindGetData{
       Object getData(Object id);
    }

    /**
     * 注册data获取对象
     * @param bindclass
     * @param bindData
     */
    public static void registBind(Class<?> bindclass, IBindGetData bindData){
        bindDataMap.put(bindclass,bindData);
    }



    public static Object getBindData(Class<?> bindClass, Object id){
        IBindGetData bindGetData =  bindDataMap.get(bindClass);
        if(bindGetData == null){
            return null;
        }
        return bindGetData.getData(id);
    }
    /**
     * 立该就会获取数据
     * @param bindClass
     * @param bindHolder
     */
    public static void bindAndGetDataViewHolder(Class<?> bindClass, IBindHolder bindHolder, Object id){
        Object object = getBindData(bindClass,id);
        if(object != null){
            bindHolder.onBindData(object);
        }
        putBindHolder(bindHolder);
    }
    public static void putBindHolder(IBindHolder bindHolder){
        ViewCachePool.getInstance().put(bindHolder,bindHolder.getKey());
    }

    public static void bindDataChanges(Object object){
        ViewCachePool.getInstance().dataChanges(object);
    }



}
