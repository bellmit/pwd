package rongshanghui.tastebychance.com.rongshanghui.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

/**
 * 项目名称：RongShangHui2
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/11/21 20:45
 * 修改人：Administrator
 * 修改时间：2017/11/21 20:45
 * 修改备注：
 */

public class ToastUtils {

    private static Toast toast;

    /**
     * 解决短时间内不断弹出Toast的方法 就只会弹出一次
     */
    public static void showOneToast(Context context, String mess) {
        try {

            if (toast == null) {
                toast = Toast.makeText(context, mess, Toast.LENGTH_SHORT);
            } else {
                toast.setText(mess);
            }
            toast.show();
            new CountDownTimer(1000, 1000) {
                // 显示的持续时间
                public void onTick(long millisUntilFinished) {
                    toast.show();
                }

                // 显示持续时间结束时调用
                public void onFinish() {
                    toast.cancel();
                }
            }.start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


    /**
     * 打印消息并且用Toast显示消息
     *
     * @param activity
     * @param message
     */
    public static final void toastMessage(final Activity activity, final String message) {
        toastMessage(activity, message, null);
    }

    private static Dialog mProgressDialog;

    public static final void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private static Toast mToast;

    /**
     * 打印消息并且用Toast显示消息
     *
     * @param activity
     * @param message
     * @param logLevel 填d, w, e分别代表debug, warn, error; 默认是debug
     */
    public static final void toastMessage(final Activity activity, final String message, String logLevel) {
        if ("w".equals(logLevel)) {
            Log.w("sdkDemo", message);
        } else if ("e".equals(logLevel)) {
            Log.e("sdkDemo", message);
        } else {
            Log.d("sdkDemo", message);
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                mToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }
}
