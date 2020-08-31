package com.appbase.uikit.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.facebook.drawee.view.SimpleDraweeView

/**
Created by yanzs on 2020/5/23
 */
class FlagSimpleDraweeView(context: Context?, attrs: AttributeSet?) :
    SimpleDraweeView(context, attrs) {
    /**
     * 是否启动蒙版 true --- 启动蒙版
     */
    private val mbFlag = true
    private var mPaint: Paint? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mbFlag) {
            if (mPaint == null) {
                mPaint = Paint()
                mPaint?.setColor(Color.BLACK) //设置Color为黑色
                mPaint?.setAlpha(0x80)
                mPaint?.setStyle(Paint.Style.FILL)
            }
            //绘制蒙版
            canvas.drawPaint(mPaint!!)
        }
    }
}