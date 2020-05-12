package com.appbase.uikit.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.appbase.uikit.R;

import java.lang.ref.WeakReference;


/**
 * 菊花Dialog
 * Created by yan on 2016/11/17.
 */

public class LoadingDialog extends AppCompatDialog {

    private static WeakReference<AppCompatDialog> weakloadingDialog;

    public static void showLoading(Context context){
        showLoading(context);
    }
    public static void showLoading(Context context,String msg){
        AppCompatDialog loadingDialog  = null;
        if(weakloadingDialog != null){
            loadingDialog = weakloadingDialog.get();
        }
        if(loadingDialog == null || loadingDialog.getContext() != context){
            loadingDialog  = new LoadingDialog(context);
        }
        if(!loadingDialog.isShowing()){
            loadingDialog.show();
        }
        weakloadingDialog = new WeakReference<AppCompatDialog>(loadingDialog);
    }
    public static void dismissDialog(){
        if(weakloadingDialog == null){
            return;
        }
        AppCompatDialog loadingDialog  = weakloadingDialog.get();
        if(loadingDialog != null){
            loadingDialog.dismiss();
            weakloadingDialog = null;
        }
    }


    private ProgressBar pb;
    private TextView message;
    private boolean backCancelable;
    private CharSequence msg;

    public LoadingDialog(Context context) {
        super(context, R.style.DialogStyle);
        setContentView(R.layout.dialog_waiting);
        initView();
        initWindow();
    }

    private void initView() {
        pb = ((ProgressBar) findViewById(R.id.progressBar1));
        message = ((TextView) findViewById(R.id.tv_content));
    }

    private void initWindow() {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public void setMessage(CharSequence msg) {
        if (isShowing()) {
            message.setText(msg);
        }
        this.msg = msg;
    }

    @Override
    public void show() {
        super.show();
        message.setText(msg);
    }

    public void setBackCancelable(boolean cancelable) {
        backCancelable = cancelable;
    }

    @Override
    public void onBackPressed() {
        if (backCancelable) {
            super.onBackPressed();
        }
    }
}
