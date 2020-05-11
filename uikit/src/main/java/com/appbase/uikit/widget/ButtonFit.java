package com.appbase.uikit.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by yan on 2016/12/16.
 */

public class ButtonFit extends Button {
    public ButtonFit(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSizeUtil.measureWidth(widthMeasureSpec);
        int measuredHeight = MeasureSizeUtil.measureHeight(heightMeasureSpec);
        measuredHeight = (int) (measuredHeight * TextSizeUtil.scale());
        measuredWidth = (int) (measuredWidth * TextSizeUtil.scale());
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

}
