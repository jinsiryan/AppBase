package com.appbase.uikit.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.appbase.uikit.utils.ColorUtil;

public class PressImageView extends AppCompatImageView {
 
    public PressImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public PressImageView(Context context) {
        super(context);
    }
 

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        ColorUtil.setViewPressState(this,pressed);
    }

}