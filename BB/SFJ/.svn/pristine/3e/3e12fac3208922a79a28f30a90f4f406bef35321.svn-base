package com.tastebychance.sfj.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

/**
 * 类描述：弹出框
 * 创建人：Administrator
 * 创建时间： 2017/11/22 11:11
 * 修改人：Administrator
 * 修改时间：2017/11/22 11:11
 * 修改备注：
 */

public class DialogUtils {
    public static DialogUtils getInstance() {
        return SingletonHolder.mInstance;
    }

    private static class SingletonHolder {
        private static final DialogUtils mInstance = new DialogUtils();
    }

    private DialogUtils() {
    }


    public void showResultDialog(Context context, String msg, String title) {
        if (msg == null) {
            return;
        }
        String rmsg = msg.replace(",", "\n");
        Log.d("Util", rmsg);
        new AlertDialog.Builder(context).setTitle(title).setMessage(rmsg).setNegativeButton("知道了", null).create().show();
    }

    /**
     * 弹出一个对话框（一个按钮）
     *
     * @param context
     * @param title    标题
     * @param btnName1 按钮名
     * @param msg      弹出信息
     * @param l1       监听事件1
     */
    public void AlertMessage(Context context, String title, String msg, String btnName1,
                             DialogInterface.OnClickListener l1) {
        if (context != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            if (builder != null) {
                builder.setTitle(title);
                builder.setMessage(msg);
                builder.setCancelable(false);
                builder.setPositiveButton(btnName1, l1);
                builder.create().show();
            }
        }
    }

    /**
     * 弹出一个对话框（两个按钮）
     *
     * @param context
     * @param title    标题
     * @param btnName1 按钮名1 （左键）
     * @param btnName2 按钮名2 (右键)
     * @param msg      弹出信息
     * @param l1       监听事件1
     * @param l2       监听事件2
     */
    public void AlertMessage(Context context, String title, String msg, String btnName1, String btnName2,
                             DialogInterface.OnClickListener l1, DialogInterface.OnClickListener l2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (null != title) {
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        builder.setNegativeButton(btnName1, l1);//左键
        builder.setPositiveButton(btnName2, l2);//右键
        builder.create().show();
    }

    /**
     * 弹出一个对话框（三个按钮）
     *
     * @param context
     * @param title    标题
     * @param btnName1 按钮名1
     * @param btnName2 按钮名2
     * @param btnName3 按钮名3
     * @param msg      弹出信息
     * @param l1       监听事件1
     * @param l2       监听事件2
     * @param l3       监听事件3
     */
    public void AlertMessage(Context context, String title, String msg, String btnName1, String btnName2,
                             String btnName3, DialogInterface.OnClickListener l1, DialogInterface.OnClickListener l2,
                             DialogInterface.OnClickListener l3) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton(btnName1, l1);//左键
        builder.setNeutralButton(btnName2, l2);//中间键
        builder.setPositiveButton(btnName3, l3);//右键
        builder.create().show();
    }

    /**
     * 确认对话框 有Message，确认按钮，取消按钮
     *
     * @param context
     * @param message
     * @param posListener 确认后的监听事件
     * @return
     */
    public AlertDialog showConfirmCancelDialog(Context context, String message,
                                               DialogInterface.OnClickListener posListener) {
        AlertDialog dlg = new AlertDialog.Builder(context).setMessage(message).setPositiveButton("确认", posListener)
                .setNegativeButton("取消", null).create();
        dlg.setCanceledOnTouchOutside(false);
        dlg.show();
        return dlg;
    }

    /**
     * 确认对话框 有title，Message，确认按钮，取消按钮
     *
     * @param context
     * @param title
     * @param message
     * @param posListener
     * @return
     */
    public AlertDialog showCustomConfirmCancelDialog(Context context, String title, String message,
                                                     DialogInterface.OnClickListener posListener) {
        AlertDialog dlg = new AlertDialog.Builder(context).setMessage(message).setTitle(title)
                .setPositiveButton("确认", posListener).setNegativeButton("取消", null).create();
        dlg.setCanceledOnTouchOutside(false);
        dlg.show();
        return dlg;
    }
}
