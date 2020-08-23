package com.boniu.shouyoujiasuqi.thirdsdk.payDialog

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialog
import com.boniu.shouyoujiasuqi.thirdsdk.R
import com.boniu.shouyoujiasuqi.thirdsdk.view.PayItemView
import kotlinx.android.synthetic.main.dialog_selected_pay.*
import kotlinx.android.synthetic.main.view_pay_item.view.*

/**
Created by yanzs on 2020/5/18
 */
open class PaySelectedDialog(context: Context) : AppCompatDialog(context){
    var payZfb: PayItemView? = null
    var payWechat: PayItemView? = null
    var btnPay: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_selected_pay)
        this.payWechat  = pay_wechat
        this.payZfb = pay_zfb
        this.btnPay = btn_pay
        pay_zfb.checkbox.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                pay_wechat.isCheck = false
            }
        }
        pay_wechat.checkbox.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                pay_zfb.isCheck = false
            }
        }
        btn_pay?.setOnClickListener {
            
        }
    }

}