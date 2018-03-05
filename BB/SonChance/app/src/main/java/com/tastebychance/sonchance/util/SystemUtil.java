package com.tastebychance.sonchance.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.homepage.pay.bean.PayStatusInfo;
import com.tastebychance.sonchance.loginandregister.LoginActivity;
import com.tastebychance.sonchance.personal.bean.UserInfo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SystemUtil {

	public static SystemUtil getInstance(){
		return SingeltonHolder.mInstance;
	}
	private static class SingeltonHolder{
		private static final SystemUtil mInstance = new SystemUtil();
	}
	private SystemUtil(){}

	public String getToken(){
		return SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), Constants.TEMP, "token");
	}
	
	public void setToken(String token){
		SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), Constants.TEMP, "token", token);
	}

	//判断是否已经登录了  true为登录   false  为没有登录
	public boolean getIsLogin() {
		return SharedPreferencesUtils.getConfigBoolean(MyApplication.getContext(),Constants.TEMP,"isLogin");
	}

	public void setIsLogin(boolean isLogin) {
		SharedPreferencesUtils.setConfigBoolean(MyApplication.getContext(), Constants.TEMP, "isLogin", isLogin);
	}

	//跳转到登录界面
	public void intentToLoginActivity(Context context,String toActivity){
		Intent intent = new Intent(context, LoginActivity.class);
		intent.putExtra("toActivity",toActivity);
		context.startActivity(intent);
	}

	public void makeToastInfo(Context context){
		Toast.makeText(context,"该功能暂未开放,敬请期待",Toast.LENGTH_SHORT).show();
	}

	public void getPersonalUserInfo(){
		//取到已经保存的token（登录后的信息）
		String token = SystemUtil.getInstance().getToken();
		Log.i(Constants.TAG, token);

		//采用okhttp3来进行网络请求
		String url =   UrlManager.URL_PERSON_GETUSERINFO;
		OkHttpClient mOkHttpClient = new OkHttpClient();
		RequestBody formBody = new FormBody.Builder().add("token", token).build();
		Request request = new Request.Builder().url(url).post(formBody).build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try {
					String str = response.body().string();
					Log.i(Constants.TAG, str);

					ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);

					UserInfo userInfo = JSONObject.parseObject(resInfo.getData().toString(), UserInfo.class);
					if (resInfo.getResult() == Constants.RESULT_SUCCESS) {

                        SharedPreferencesUtils.saveToShared(MyApplication.getContext(), "userInfo", userInfo);

                        /*context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new GoodsReceiptAddressManagerAdapter(GoodsReceiptAddressManagerActivity.this,goodsReceiptInfos);
                                pullRefreshListView.setAdapter(adapter);
                            }
                        });*/
                    } else {

                    }
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});
	}

	/**
	 * 返回当前程序版本名
	 */
	public  String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			int versioncode = pi.versionCode;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	/**
	 * 获取版本号(内部识别号)
	 * @param context
	 * @return
     */
	public  int getVersionCode(Context context){
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionCode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public  boolean execShellCmd(String cmd) {

		try {
			System.out.println(cmd);
			// 申请获取root权限，这一步很重要，不然会没有作用
			Process process = Runtime.getRuntime().exec("su");
			// 获取输出流
			OutputStream outputStream = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(
					outputStream);
			dataOutputStream.writeBytes(cmd);
			dataOutputStream.flush();
			dataOutputStream.close();
			outputStream.close();
			String ls = null;
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream(), "gbk"));
			while ((ls = bufferedReader.readLine()) != null) {
				Log.v("CMD", ls);
				if ("INSTRUMENTATION_STATUS_CODE: 1".equals(ls)) {
					return true;
				}
			}

			bufferedReader.close();
			process.getOutputStream().close();

		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 获取当前手机所在省份城市名称
	 *
	 * @return
	 */
	public  String getLocationName() {
		String locateName = null;

		LocationUtil locationUtil = new LocationUtil(MyApplication.getContext());
		locateName = locationUtil.getAddressName();

		return locateName;
	}

	/**
	 * 获取网络类型
	 *
	 * @param context
	 * @return 为空串时是没开启网络
	 */
	public  String GetNetworkType(Context context) {
		String strNetworkType = "";

		ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo networkInfo = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (mWifi != null && mWifi.isConnected()) {
			strNetworkType = "WIFI";
			return strNetworkType;
		}

		if (networkInfo != null && networkInfo.isConnected()) {

			String _strSubTypeName = networkInfo.getSubtypeName();

			Log.e("cocos2d-x", "Network getSubtypeName : " + _strSubTypeName);

			// TD-SCDMA networkType is 17
			int networkType = networkInfo.getSubtype();
			switch (networkType) {
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_EDGE:
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_1xRTT:
				case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by 11
					strNetworkType = "2G";
					break;
				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by 14
				case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by 12
				case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by 15
					strNetworkType = "3G";
					break;
				case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by 13
					strNetworkType = "4G";
					break;
				default:
					// http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
					if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA")
							|| _strSubTypeName.equalsIgnoreCase("WCDMA")
							|| _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
						strNetworkType = "3G";
					} else {
						strNetworkType = _strSubTypeName;
					}

					break;
			}

			Log.e("cocos2d-x",
					"Network getSubtype : "
							+ Integer.valueOf(networkType).toString());

		}

		Log.e("cocos2d-x", "Network Type : " + strNetworkType);

		return strNetworkType;
	}

	/**
	 * 获取IMEI
	 *
	 * @param context
	 * @return
	 */
	public  String getIMEI(Context context) {
		// TelephonyManager telephonyManager = (TelephonyManager) context
		// .getSystemService(Context.TELEPHONY_SERVICE);
		//
		// String imei = telephonyManager.getDeviceId();
		// return imei;
		String serial = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class);
			serial = (String) get.invoke(c, "ro.serialno");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serial;
	}

	/**
	 * 获取手机号
	 *
	 * @param context
	 * @return
	 */
	public  String getPhoneNo(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		String phoneNo = telephonyManager.getLine1Number();
		return phoneNo;
	}

	/**
	 * 获取手机卡串号
	 *
	 * @param context
	 * @return
	 */
	public  String getImsiNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = tm.getSubscriberId();
		return imsi;
	}

	/**
	 * 重启机子
	 *
	 * @param context
	 */
	public  void rebootPhone(Context context) {
		// Intent restartIntent = new Intent(context, MainActivity.class);
		// int pendingId = 1;
		// PendingIntent pendingIntent = PendingIntent.getActivity(context,
		// pendingId, restartIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		// AlarmManager mgr =
		// (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		// mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 2000,
		// pendingIntent);
		String cmd = "su -c reboot";
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			new AlertDialog.Builder(context).setTitle("Error").setMessage(e.getMessage()).setPositiveButton("OK", null).show();
		}

	}

	/**
	 * 是否root过
	 * @return
	 */
	public  boolean isRoot() {
		try {
			Process process = Runtime.getRuntime().exec("su");
			process.getOutputStream().write("exit\n".getBytes());
			process.getOutputStream().flush();
			int i = process.waitFor();
			if (0 == i) {
				process = Runtime.getRuntime().exec("su");
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 获取Android本机IP地址
	 *
	 * @return
	 */
	public  String getLocalIPAddress() throws SocketException {
		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
			NetworkInterface intf = en.nextElement();
			for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
				InetAddress inetAddress = enumIpAddr.nextElement();
				if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
					return inetAddress.getHostAddress().toString();
				}
			}
		}
		return null;
	}

	/**
	 *
	 * 截屏
	 *
	 * @param activity
	 * @return
	 */
	public  String captureScreen(Context activity) {

		// 获取屏幕大小：
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager WM = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
		Display display = WM.getDefaultDisplay();
		display.getMetrics(metrics);
		int height = metrics.heightPixels; // 屏幕高
		int width = metrics.widthPixels; // 屏幕的宽

		// 获取显示方式
		int pixelformat = display.getPixelFormat();
		PixelFormat localPixelFormat1 = new PixelFormat();
		PixelFormat.getPixelFormatInfo(pixelformat, localPixelFormat1);
		int deepth = localPixelFormat1.bytesPerPixel;// 位深
		byte[] piex = new byte[height * width * deepth];
		try {
			Runtime.getRuntime().exec(new String[] { "/system/bin/su", "-c", "chmod 777 /dev/graphics/fb0" });
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// 获取fb0数据输入流
			InputStream stream = new FileInputStream(new File("/dev/graphics/fb0"));
			DataInputStream dStream = new DataInputStream(stream);
			dStream.readFully(piex);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 保存图片
		int[] colors = new int[height * width];
		for (int m = 0; m < colors.length; m++) {
			int r = (piex[m * 4] & 0xFF);
			int g = (piex[m * 4 + 1] & 0xFF);
			int b = (piex[m * 4 + 2] & 0xFF);
			int a = (piex[m * 4 + 3] & 0xFF);
			colors[m] = (a << 24) + (r << 16) + (g << 8) + b;
		}

		// piex生成Bitmap
		Bitmap bitmap = Bitmap.createBitmap(colors, width, height, Bitmap.Config.ARGB_8888);
		File f = null;
		try {
			Bitmap mBitmap = bitmap;
			f = new File(Environment.getExternalStorageDirectory() + "/mypic.png");
			f.createNewFile();
			FileOutputStream fOut = new FileOutputStream(f);
			mBitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return f.getAbsolutePath();
	}

	public  void killPorcess(String processId) {
		try {
			String cmd = "kill -9 " + processId;
			Process process = Runtime.getRuntime().exec("su");
			// 获取输出流
			OutputStream outputStream = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeBytes(cmd);
			dataOutputStream.flush();
			dataOutputStream.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void stopProcess(String processName) {
		String pscmd = "ps | grep " + processName;
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec(pscmd);
			InputStream inputstream = proc.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			String line = "";
			StringBuilder sb = new StringBuilder(line);
			while ((line = bufferedreader.readLine()) != null) {
				// System.out.println(line);
				sb.append(line);
				sb.append('\n');
			}
			proc.waitFor();
			String[] l = sb.toString().split("root");
			System.out.print(sb.toString());
			for (String s : l) {
				if (s.contains(processName)) {
					String[] ls = s.split(" ");
					for (String p : ls) {
						if (p.length() > 0) {
							killPorcess(p);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	final  Pattern pattern = Pattern.compile("\\S*[?]\\S*");

	public  void toggleWiFi(Context context, boolean enabled) {
		WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wm.setWifiEnabled(enabled);
//		try {
//			Thread.sleep(2500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	// 检测GPRS是否打开
	private  boolean gprsIsOpenMethod(ConnectivityManager mCM, String methodName) {
		Class cmClass = mCM.getClass();
		Class[] argClasses = null;
		Object[] argObject = null;

		Boolean isOpen = false;
		try {
			Method method = cmClass.getMethod(methodName, argClasses);

			isOpen = (Boolean) method.invoke(mCM, argObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isOpen;
	}

	// 开启/关闭GPRS
	private  void setGprsEnabled(ConnectivityManager mCM, boolean isEnable) {
		Class cmClass = mCM.getClass();
		Class[] argClasses = new Class[1];
		argClasses[0] = boolean.class;

		try {
			Method method = cmClass.getMethod("setMobileDataEnabled", argClasses);
			method.invoke(mCM, isEnable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final static String COMMAND_L_ON = "svc data enable\n ";
	private final static String COMMAND_L_OFF = "svc data disable\n ";
	private final static String COMMAND_SU = "su";
	public  void setGprsEnabled(Context context, boolean enable) {

		String command;
		if (enable)
			command = COMMAND_L_ON;
		else
			command = COMMAND_L_OFF;

		try {
			Process su = Runtime.getRuntime().exec(COMMAND_SU);
			DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

			outputStream.writeBytes(command);
			outputStream.flush();

			outputStream.writeBytes("exit\n");
			outputStream.flush();
			try {
				su.waitFor();
			} catch (Exception e) {
				e.printStackTrace();
			}

			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  boolean gprsEnabled(Context context, boolean bEnable) {
		if (Build.VERSION.SDK_INT >= 21) {
			setGprsEnabled(context, bEnable);
		} else {
			Object[] argObjects = null;
			ConnectivityManager mCM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			setGprsEnabled(mCM, bEnable);
		}
		return bEnable;
	}

	public int getStatusBarHeight(){
		int height = - 1;
		//方法一：获取status_bar_height资源的ID
		int resourceId = MyApplication.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0){
			//根据资源ID获取响应的尺寸值
			height = MyApplication.getContext().getResources().getDimensionPixelSize(resourceId);
		}
		
		//方法一未获取到状态栏高度，采用方法二进行获取(通过R类的反射)
		if (height == -1){
			try {
				Class<?> clazz = Class.forName("com.android.internal.R$dimen");
				Object object = clazz.newInstance();
				int tempHeight = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
				height = MyApplication.getContext().getResources().getDimensionPixelOffset(tempHeight);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//如果都没有获取到那么采用默认的25dp当状态栏的高度
		if (height == -1){
			float density = MyApplication.getContext().getResources().getDisplayMetrics().density;
			height = Integer.valueOf((25 * density + 0.5 )+"");
		}

		return height;
	}

	/**
	 * 设置支付状态
	 * @param order_sn 订单号
	 * @param isPayed 是否支付过
     */
	public void setPayStatus(String order_sn,boolean isPayed){
		WeakReference<PayStatusInfo> wf = new WeakReference<PayStatusInfo>(new PayStatusInfo());
		wf.get().setOrder_sn(order_sn);
		wf.get().setPayed(isPayed);
		SharedPreferencesUtils.saveToShared(MyApplication.getContext(),order_sn,wf.get());
	}

	public void clearData(){
		//清空已经选中的城市
		SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"chosedCity","");
		SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"city","");
		SystemUtil.getInstance().setToken(null);
		SystemUtil.getInstance().setIsLogin(false);
		SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"token","");
		SharedPreferencesUtils.setConfigStr(MyApplication.getContext(),Constants.TEMP,"phoneno","");
		SharedPreferencesUtils.saveToShared(MyApplication.getContext(),"userInfo",null);
	}

	/*public static void main(String[] args){
		int height = -1;
		float density = 3f;
		height = (int)(25 * density + 0.5 );
		System.out.println(height);
	}*/

}
