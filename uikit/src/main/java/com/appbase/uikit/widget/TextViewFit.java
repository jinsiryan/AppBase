package com.appbase.uikit.widget;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by fanxiaoyong on 2016/12/15.
 */

public class TextViewFit extends AppCompatTextView {

    private float originalTextSize;

    public TextViewFit(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        originalTextSize = getTextSize();
        if (attrs == null) {
            return;
        }
        TextPaint paint = getPaint();
        paint.setTextSize(TextSizeUtil.scale() * originalTextSize);
    }

    public TextViewFit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextViewFit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public void invalidate() {
        super.invalidate();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void setTextSize(int unit, float size) {
        originalTextSize = TypedValue.applyDimension(unit, size, getResources().getDisplayMetrics());
        super.setTextSize(unit, TextSizeUtil.scale() * originalTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        TextPaint paint = getPaint();
        paint.setTextSize(TextSizeUtil.scale() * originalTextSize);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
    }
}
