package com.appbase.uikit.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**单例吐司*/
public class ToastUtil {
    private static Toast toast;

    enum TypeGravity{
        BOTTOM,CENTER,TOP
    }
    /**全局上下文，避免泄露*/
    private static void getInstance(Context context, String message){
        if(toast==null){
            toast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
        }else {
            toast.setText(message);
        }
    }
    public static void showToast(Context context, String message){
        getInstance(context,message);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static void showToast(Context context, String message, TypeGravity type, int xoffset, int yoffset){
        getInstance(context,message);
        switch (type){
            case BOTTOM:
                toast.setGravity(Gravity.BOTTOM,xoffset,yoffset);
                break;
            case CENTER:
                toast.setGravity(Gravity.CENTER,xoffset,yoffset);
                break;
            case TOP:
                toast.setGravity(Gravity.TOP,xoffset,yoffset);
                break;

        }
        toast.show();
    }

    public void showToast(Context context, View view){
        getInstance(context,"");
        toast.setView(view);
        toast.show();

    }
}
