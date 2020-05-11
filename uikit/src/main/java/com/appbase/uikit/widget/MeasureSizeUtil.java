package com.appbase.uikit.widget;

import android.view.View;

/**
 * Created by yan on 2016/12/16.
 */

public class MeasureSizeUtil {
    public static int measureWidth(int measureSpec) {
        int result = 0;
        // 对测量说明进行解码
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.UNSPECIFIED) {
            // 如果没有指定界限，则返回默认大小
           //必需指定大小
//            result =ScreenUtil.screenWidth;
        } else {
            // 由于希望填充可用的空间，所以返回整个可用的边界
            result = specSize;
        }
        return result;
    }

    public static int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.UNSPECIFIED) {
            //必需指定宽度
//            result =ScreenUtil.screenHeight;
        } else {
            result = specSize;
        }
        return result;
    }
}
