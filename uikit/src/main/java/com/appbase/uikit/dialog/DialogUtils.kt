package com.appbase.uikit.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.allen.android.lib.PermissionUtils
import com.appbase.uikit.R

/**
Created by yanzs on 2020/6/18
 */
object DialogUtils {
    fun showPermissionsSettingDailog(
        context: Context,
        permissionStr: String?,
        funstr: String?
    ) {
        val message = String.format(
            context.getString(R.string.func_permissions_text),
            permissionStr,
            funstr
        )
        AlertDialog.Builder(context)
            .setTitle(R.string.permission_setting)
            .setMessage(message)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(
                R.string.goto_setting,
                DialogInterface.OnClickListener { dialog, which ->
                    try {
                        PermissionUtils.toPermissionSetting(context)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }).show()
    }
}