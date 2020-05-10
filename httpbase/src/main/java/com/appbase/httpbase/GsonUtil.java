package com.appbase.httpbase;

import com.appbase.httpbase.gson.DoubleDefault0Adapter;
import com.appbase.httpbase.gson.IntegerDefault0Adapter;
import com.appbase.httpbase.gson.LongDefault0Adapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * com.kook.libs.utils.
 * Created by fanxiaoyong    2017/6/5.
 */
public class GsonUtil {
    public static Gson GSON;

    static {
        GSON = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
                .create();
    }


    /**
     * 检测String是否是jsonObject
     *
     * @param datas
     * @return
     */
    public static boolean checkJsonObject(String datas) {
        if (datas.startsWith("{") && datas.endsWith("}")) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否是jsonArray
     *
     * @param datas
     * @return
     */
    public static boolean checkJsonArray(String datas) {
        if (datas.startsWith("[") && datas.endsWith("]")) {
            return true;
        }
        return false;
    }
}
