package com.tastebychance.sonchance.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.tastebychance.sonchance.MyApplication;

/**
 * 应用程序UI工具包：封装UI相关的一些操作 1. 进行意图的传递 通过主界面点击相应的GridView元素后 切换到相应界面 通过显示意图进行调用的
 * 
 * 
 * @author
 */
public class UiHelper {

	private static Toast toast;

	/**
	 * 解决短时间内不断弹出Toast的方法 就只会弹出一次
	 * 
	 */
	public static void ShowOneToast(Context context, String mess) {
		try {

			if (toast == null) {
				toast = Toast.makeText(context, mess, Toast.LENGTH_SHORT);
			}
			else {
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
	 * 将ImageView读取成圆形的
	 * 
	 * @param bitmap
	 * @param ratio
	 * @return
	 */
	public static Bitmap CircleImageView(Bitmap bitmap, float ratio) {
		if (MyApplication.isDevelopmentStage) {
			System.out.println("图片是否变成圆形模式了+++++++++++++");
		}
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);

		/*if (bitmap.getHeight() > bitmap.getWidth()){
			rect = new Rect(0, 0, bitmap.getHeight(), bitmap.getHeight());
			rectF = new RectF(rect);
			canvas.drawRoundRect(rectF, bitmap.getHeight() / ratio, bitmap.getHeight() / ratio, paint);
		}else{*/
			canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio, bitmap.getHeight() / ratio, paint);
		/*}*/

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		if (MyApplication.isDevelopmentStage) {
			System.out.println("pixels+++++++" + String.valueOf(ratio));
		}

		return output;

	}

	/**
	 * 将图片剪裁为圆形
	 */
	public static Bitmap createCircleImage(Bitmap source) {
		int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(target);
		canvas.drawCircle(length / 2, length / 2, length / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(source, 0, 0, paint);
		return target;
	}


	public static final void showResultDialog(Context context, String msg, String title) {
		if (msg == null)
			return;
		String rmsg = msg.replace(",", "\n");
		Log.d("Util", rmsg);
		new AlertDialog.Builder(context).setTitle(title).setMessage(rmsg).setNegativeButton("知道了", null).create()
				.show();
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
	 * @param logLevel
	 *            填d, w, e分别代表debug, warn, error; 默认是debug
	 */
	public static final void toastMessage(final Activity activity, final String message, String logLevel) {
		if ("w".equals(logLevel)) {
			Log.w("sdkDemo", message);
		}
		else if ("e".equals(logLevel)) {
			Log.e("sdkDemo", message);
		}
		else {
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

	/**
	 * 弹出一个对话框（一个按钮）
	 * 
	 * @param context
	 * @param title
	 *            标题
	 * @param btnName1
	 *            按钮名
	 * @param msg
	 *            弹出信息
	 * @param l1
	 *            监听事件1
	 */
	public static void AlertMessage(Context context, String title, String msg, String btnName1,
			DialogInterface.OnClickListener l1) {
		if (context != null) {
			Builder builder = new Builder(context);
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
	 * @param title
	 *            标题
	 * @param btnName1
	 *            按钮名1
	 * @param btnName2
	 *            按钮名2
	 * @param msg
	 *            弹出信息
	 * @param l1
	 *            监听事件1
	 * @param l2
	 *            监听事件2
	 */
	public static void AlertMessage(Context context, String title, String msg, String btnName1, String btnName2,
			DialogInterface.OnClickListener l1, DialogInterface.OnClickListener l2) {
		Builder builder = new Builder(context);
		if(null != title){
			builder.setTitle(title);
		}
		builder.setMessage(msg);
		builder.setPositiveButton(btnName1, l1);
		builder.setNegativeButton(btnName2, l2);
		builder.create().show();
	}

	/**
	 * 弹出一个对话框（三个按钮）
	 * 
	 * @param context
	 * @param title
	 *            标题
	 * @param btnName1
	 *            按钮名1
	 * @param btnName2
	 *            按钮名2
	 * @param btnName3
	 *            按钮名3
	 * @param msg
	 *            弹出信息
	 * @param l1
	 *            监听事件1
	 * @param l2
	 *            监听事件2
	 * @param l3
	 *            监听事件3
	 */
	public static void AlertMessage(Context context, String title, String msg, String btnName1, String btnName2,
			String btnName3, DialogInterface.OnClickListener l1, DialogInterface.OnClickListener l2,
			DialogInterface.OnClickListener l3) {
		Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(btnName1, l1);
		builder.setNeutralButton(btnName2, l2);
		builder.setNegativeButton(btnName3, l3);
		builder.create().show();
	}

	/**
	 * 确认对话框 有Message，确认按钮，取消按钮
	 * 
	 * @param context
	 * @param message
	 * @param posListener
	 *            确认后的监听事件
	 * @return
	 */
	public static AlertDialog showConfirmCancelDialog(Context context, String message,
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
	public static AlertDialog showCustomConfirmCancelDialog(Context context, String title, String message,
			DialogInterface.OnClickListener posListener) {
		AlertDialog dlg = new AlertDialog.Builder(context).setMessage(message).setTitle(title)
				.setPositiveButton("确认", posListener).setNegativeButton("取消", null).create();
		dlg.setCanceledOnTouchOutside(false);
		dlg.show();
		return dlg;
	}
	
	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 得到设备的密度
	 */
	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 把密度转换为像素
	 */
	public static int dip2px(Context context, float px) {
		final float scale = getScreenDensity(context);
		return (int) (px * scale + 0.5);
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {

		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		System.out.println("Drawable转Bitmap");
		Bitmap.Config config =
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
						: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		//注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);

		return bitmap;
	}

	public static Drawable bitmapToDrawable(Bitmap bitmap){
		return new BitmapDrawable(bitmap);
	}

	/**
	 * Gets the corresponding path to a file from the given content:// URI
	 * @param selectedVideoUri The content:// URI to find the file path from
	 * @param contentResolver The content resolver to use to perform the query.
	 * @return the file path as a string
	 */
	public static String getFilePathFromContentUri(Uri selectedVideoUri,
												   ContentResolver contentResolver) {
		String filePath;
		String[] filePathColumn = {MediaStore.MediaColumns.DATA};

		Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
//      也可用下面的方法拿到cursor
//      Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);

		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		filePath = cursor.getString(columnIndex);
		cursor.close();
		return filePath;
	}

	/**
	 * Gets the content:// URI  from the given corresponding path to a file
	 * @param context
	 * @param imageFile
	 * @return content Uri
	 */
	public static Uri getImageContentUri(Context context, java.io.File imageFile) {
		String filePath = imageFile.getAbsolutePath();
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Images.Media._ID },
				MediaStore.Images.Media.DATA + "=? ",
				new String[] { filePath }, null);
		if (cursor != null && cursor.moveToFirst()) {
			int id = cursor.getInt(cursor
					.getColumnIndex(MediaStore.MediaColumns._ID));
			Uri baseUri = Uri.parse("content://media/external/images/media");
			return Uri.withAppendedPath(baseUri, "" + id);
		} else {
			if (imageFile.exists()) {
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.DATA, filePath);
				return context.getContentResolver().insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			} else {
				return null;
			}
		}
	}
}
