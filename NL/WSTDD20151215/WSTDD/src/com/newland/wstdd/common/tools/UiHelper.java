package com.newland.wstdd.common.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.newland.wstdd.find.categorylist.ShowFindListViewActivity;
import com.newland.wstdd.login.regist.RegistFinishActivity;

/**
 *  应用程序UI工具包：封装UI相关的一些操作 1. 进行意图的传递 通过主界面点击相应的GridView元素后 切换到相应界面
 * 通过显示意图进行调用的
 * 
 * 
 * @author 
 */
public class UiHelper {

	private static Toast toast;
	/**
	 * 解决短时间内不断弹出Toast的方法   就只会弹出一次
	 * 
	 */
	public static void ShowOneToast(Context context,String mess){
		try {
			
			if (toast == null) {
	            toast = Toast.makeText(context, mess, Toast.LENGTH_SHORT);
	        } else {
	            toast.setText(mess);
	        }
	        toast.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	
	/**
	 * 显示注册第2个界面
	 */
	public static void showRegistFinishActivity(Context context, Bundle bundle){
		Intent intent = new Intent(context, RegistFinishActivity.class);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	

	
	/**
	 * 发现部分
	 * @param context
	 * @param type 
	 */
	//讲座部分
	public static void showFindListViewActivity(Context context,String mess, String type){
		Intent intent = new Intent(context, ShowFindListViewActivity.class);
		Bundle bundle=new Bundle();
		bundle.putString("mess", mess);
		bundle.putString("type", type);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	/**
	 * 热门列表
	 * @param context
	 * @param type 
	 */
	public static void HotListListViewActivity(Context context){
		Intent intent = new Intent(context, com.newland.wstdd.find.hotlist.HotListListViewActivity.class);
//		Bundle bundle=new Bundle();
//		bundle.putString("mess", mess);
		
//		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	/**
	 * 将ImageView读取成圆形的
	 * @param bitmap
	 * @param ratio
	 * @return
	 */
	public static Bitmap CircleImageView(Bitmap bitmap, float ratio) {
        System.out.println("图片是否变成圆形模式了+++++++++++++");
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
                bitmap.getHeight() / ratio, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        System.out.println("pixels+++++++" + String.valueOf(ratio));

        return output;

    }
	
	public static final void showResultDialog(Context context, String msg,
			String title) {
		if(msg == null) return;
		String rmsg = msg.replace(",", "\n");
		Log.d("Util", rmsg);
		new AlertDialog.Builder(context).setTitle(title).setMessage(rmsg)
				.setNegativeButton("知道了", null).create().show();
	}
	/**
	 * 打印消息并且用Toast显示消息
	 * 
	 * @param activity
	 * @param message
	 * @param logLevel
	 *            填d, w, e分别代表debug, warn, error; 默认是debug
	 */
	public static final void toastMessage(final Activity activity,
			final String message) {
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
	 * @param logLevel
	 *            填d, w, e分别代表debug, warn, error; 默认是debug
	 */
	public static final void toastMessage(final Activity activity,
			final String message, String logLevel) {
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
				// TODO Auto-generated method stub
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
