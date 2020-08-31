package com.appbase.uikit.cache;


import com.appbase.uikit.utils.reference.WeakReferenceMap;
import com.appbase.uikit.utils.reference.WeakValue;

import java.util.Collection;


/**
 * Created by yan on 2017/3/15.
 */

public class ViewCachePool extends WeakReferenceMap<String, IBindHolder> {
    private static ViewCachePool instance = new ViewCachePool();

    public static ViewCachePool getInstance() {
        return instance;
    }

    public void dataChanges(Object data) {
        if (temp.isEmpty()) {
            return;
        }
        long startTime = System.currentTimeMillis();
        Collection<WeakValue<String, IBindHolder>> views = temp.values();
        IBindHolder view = null;
        for (WeakValue<String, IBindHolder> viewRef : views) {
            view = viewRef.get();
            if (view != null) {
                view.onBindData(data);
            }
        }
    }

    public IBindHolder put(IBindHolder value, String key) {
        if (!containsKey(key)) {
            return super.put(key, value);
        }
        return value;
    }
}
