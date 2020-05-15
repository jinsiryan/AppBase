package com.appbase.uikit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.annotation.Keep;

import java.util.Set;

/**
 * 全局的
 * SharedPreference封装
 */
@Keep
public class PreferenceManager {
    public static String SP_FILE = "sp_file";
    private static Context mContext;

    public static void init(Context context){
        mContext = context;
    }
    /**
     * 保存数据到 global sp file
     *
     * @param key   key
     * @param value value
     */
    public static void saveGlobal(String key, Object value) {
        save(SP_FILE, key, value);
    }

    public static <T> T getGlobal(String key, T defValue) {
        return get(SP_FILE, key, defValue);
    }
    public static boolean removeGlobal(String key) {
        return remove(SP_FILE, key);
    }

//    public static <T> void saveList(String fileName, String key, List<T> value) {
//        if (value == null) {
//            value = new ArrayList<>();
//        }
//        String json = new Gson().toJson(value);
//        save(fileName, key, json);
//    }
//
//    public static <T> List<T> getList(String fileName, String key, Class<T> clazz) {
//        try {
//            Type listType = new TypeToken<List<T>>(){}.getType();
//            String json = get(fileName, key, "[]");
//            return new Gson().fromJson(json, listType);
//        } catch (Exception e) {
//
//        }
//        return null;
//    }

    /**
     * 保存数据到 指定的file sp file
     *
     * @param key   key
     * @param value value
     */
    public static void save(String fileName, String key, Object value) {
        SharedPreferences sp = mContext.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        if (value instanceof String) {
            editor.putString(key, value.toString());
        } else if (value instanceof Integer) {
            editor.putInt(key,
                    Integer.parseInt(value.toString()));
        } else if (value instanceof Long) {
            editor.putLong(key,
                    Long.parseLong((value.toString())));
        } else if (value instanceof Float) {
            editor.putFloat(key,
                    Float.parseFloat((value.toString())));
        } else if (value instanceof Boolean) {
            editor.putBoolean(key,
                    Boolean.parseBoolean((value.toString())));
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set) value);
        }
        editor.commit();

    }

    public static boolean remove(String fileName, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }


    /**
     * 从指定的file 获取数据
     *
     * @param fileName
     * @param key
     * @param defValue
     * @param <T>
     * @return
     */
    public static <T> T get(String fileName, String key, T defValue) {
        Object t = defValue;
        try {
            SharedPreferences sp = mContext.getSharedPreferences(fileName,
                    Context.MODE_PRIVATE);
            if (defValue instanceof String) {
                t = sp.getString(key, defValue.toString());
            } else if (defValue instanceof Integer) {
                t = sp.getInt(key,
                        (Integer) defValue);
            } else if (defValue instanceof Long) {
                t = sp.getLong(key,  (Long)defValue);
            } else if (defValue instanceof Float) {
                t = sp.getFloat(key, (Float)defValue);
            } else if (defValue instanceof Boolean) {
                t = sp.getBoolean(key, (Boolean)defValue);
            } else if (defValue instanceof Set) {
                t = sp.getStringSet(key, (Set) defValue);
            }
            return (T) t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }
}
