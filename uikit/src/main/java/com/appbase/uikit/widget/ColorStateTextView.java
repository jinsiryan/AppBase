package com.appbase.uikit.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.appbase.uikit.utils.ColorUtil;


public class ColorStateTextView extends AppCompatTextView {
    public ColorStateTextView(@NonNull Context context) {
        super(context);
    }

    public ColorStateTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setTextColor(getCurrentTextColor());
    }

    public ColorStateTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTextColor(int color) {
        setTextColor(ColorUtil.createColorStateList(color));
    }
}
