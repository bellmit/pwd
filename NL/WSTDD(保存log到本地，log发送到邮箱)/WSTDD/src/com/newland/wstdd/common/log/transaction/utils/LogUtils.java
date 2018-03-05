package com.newland.wstdd.common.log.transaction.utils;

import java.io.File;

import android.content.Context;
import android.text.TextUtils;

public class LogUtils {
	public static boolean DEBUG = true; // 关闭或打开普通日志
	public static boolean CRASH_SAVE_2_FILE = true;// 关闭或打开crash日志写入文件。
	public static boolean CRASH_UPLOAD_2_NETWORK = false;// 关闭或打开crash日志上传服务器。

	public static final int LOG_TYPE_2_LOGCAT = 0x01;
	public static final int LOG_TYPE_2_FILE = 0x02;
	public static final int LOG_TYPE_2_FILE_AND_LOGCAT = 0x03;
	public static final int LOG_TYPE_2_NETWORK = 0x04;

	public final static String LOG_CACHE_DIRECTORY_NAME = "log";
	public final static String CRASH_CACHE_DIRECTORY_NAME = "crash";
	private static String sLogFileName = null;
	private static String sCrashFileName = null;

	public static File getLogDir(Context context) {
		return Utils.getAppCacheDir(context, LOG_CACHE_DIRECTORY_NAME);
	}

	public static File getCrashDir(Context context) {
		return Utils.getAppCacheDir(context, CRASH_CACHE_DIRECTORY_NAME);
	}

	public static String getLogFileName(Context context) {
		if (TextUtils.isEmpty(sLogFileName)) {
			File sub = new File(getLogDir(context), "log_" + System.currentTimeMillis() + ".txt");
			sLogFileName = sub.getAbsolutePath();
		}

		return sLogFileName;
	}

	public static String getCrashFileName(Context context) {
		if (TextUtils.isEmpty(sCrashFileName)) {
			File sub = new File(getCrashDir(context), "crash_" + System.currentTimeMillis() + ".txt");
			sCrashFileName = sub.getAbsolutePath();
		}

		return sCrashFileName;
	}
}