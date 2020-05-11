package com.appbase.uikit.cache;

/**
 * Created by yan on 2017/8/31.
 */

public interface IBindHolder {
    /**
     * 数据发生变化
     * @param data
     */
    void onBindData(Object data);

    /**
     * 保持唯一
     * @return
     */
    String getKey();
}
