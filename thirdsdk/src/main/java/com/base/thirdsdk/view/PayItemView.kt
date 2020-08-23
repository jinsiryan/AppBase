package com.boniu.shouyoujiasuqi.thirdsdk.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.boniu.shouyoujiasuqi.thirdsdk.R
import kotlinx.android.synthetic.main.view_pay_item.view.*

/**
Created by yanzs on 2020/5/18
 */
class PayItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr){
    var isCheck = false
    set(value) {
        checkbox.isChecked = value
        field = value
    }
    get() {
        return checkbox.isChecked
    }
    init {
        View.inflate(context, R.layout.view_pay_item, this)
        val ta = context.obtainStyledAttributes(attrs,
            R.styleable.PayItemView, 0, 0)
        try{
            val name = ta.getString(R.styleable.PayItemView_pay_name)
            val price = ta.getString(R.styleable.PayItemView_pay_price)
            val check = ta.getBoolean(R.styleable.PayItemView_pay_check, false)
            val leftIcon = ta.getResourceId(R.styleable.PayItemView_pay_icon, -1)
            if (leftIcon != -1){
                setIcon(leftIcon)
            }
            setName(name)
            setPrice(price)
            isCheck = check
        }finally {
            ta.recycle()
        }
    }
    fun setIcon(res:Int){
        icon.setImageResource(res)
    }
    fun setPrice(price:String?){
        tv_price.text = price
    }
    fun setName(name:String?){
        tv_title.text = name
    }


}