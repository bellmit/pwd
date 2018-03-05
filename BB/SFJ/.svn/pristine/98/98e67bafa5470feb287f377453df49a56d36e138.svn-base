package com.tastebychance.sfj.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.tastebychance.sfj.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CrashHandler implements UncaughtExceptionHandler {
    public static final boolean IS_SAVE_ERRORLOG = true;

    private static final String TAG = "ExceptionInfo";
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");

    private Context mContext;
    private UncaughtExceptionHandler mDefaulHanler;

    private static CrashHandler mInstance = new CrashHandler();

    private Map<String, String> mLogInfo = new HashMap<String, String>();

    public void init(Context context) {
        mContext = context;
        mDefaulHanler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return mInstance;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(thread, ex);
    }

    public void handleException(Exception e) {
        Writer wr = new StringWriter();
        PrintWriter err = new PrintWriter(wr);
        e.printStackTrace(err);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(err);
            cause = cause.getCause();
        }
        err.close();
        String result = wr.toString();
        System.out.println("result ===================== " + result);
        MyApplication.getAppContext().clearStatck();
    }

    public void handleException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaulHanler != null) {
            // 如果自定义的没有处理则让系统默认的异常处理器来处理
            mDefaulHanler.uncaughtException(thread, ex);
        } else {
            try {
                // 如果处理了，让程序继续运行1秒再退出，保证文件保存并上传到服务器
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        MyApplication.getAppContext().onTerminate();
        // 退出程序,注释下面的重启启动程序代码
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

        // 重新启动程序，注释上面的退出程序
        /*Intent intent = new Intent();
		intent.setClass(mContext, HostActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());*/
    }

    public boolean handleException(Throwable paramThrowable) {
        if (paramThrowable == null) {
            return false;
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                //Looper.prepare();
                // Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出", 1000).show();
                //Looper.loop();
            }
        }).start();

        // 获取设备参数信息
        getDeviceInfo(mContext);

        if (IS_SAVE_ERRORLOG) {
            // 保存日志文件
            saveCrashLogToFile(paramThrowable);

        }

        return true;
    }

    /**
     * 获取设备信息
     *
     * @param context
     * @author ab
     */
    private void getDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (null != packageInfo) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
                String packageName = packageInfo.packageName;
                mLogInfo.put("versionName", versionName);
                mLogInfo.put("versionCode", versionCode);
                mLogInfo.put("packageName", packageName);
            }

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        // 反射机制
        Field[] mFields = Build.class.getDeclaredFields();
        // 迭代Build的字段key-value 此处的信息主要是为了在服务器端展示各种版本手机报错的原因
        for (Field field : mFields) {
            try {
                field.setAccessible(true);
                mLogInfo.put(field.getName(), field.get("").toString());
                Log.d(TAG, field.getName() + ":" + field.get(""));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * {将崩溃的Log保存到本地} TODO 可拓展，将Log上传至指定服务器路径
     *
     * @param paramThrowable
     * @return
     * @author ab
     */
    private String saveCrashLogToFile(Throwable paramThrowable) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : mLogInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\r\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        paramThrowable.printStackTrace(printWriter);
        paramThrowable.printStackTrace();
        Throwable throwable = paramThrowable.getCause();
        // 迭代栈队列把所有的异常信息写入到writer中
        while (throwable != null) {
            throwable.printStackTrace(printWriter);
            ;
            printWriter.append("\r\n");
            throwable = throwable.getCause();
        }
        // 关闭流
        printWriter.close();

        String mResult = writer.toString();
        sb.append(mResult);

        // 保存文件，设置文件名
        String mTime = SDF.format(new Date());
        String mFileName = "SFJ_" + mTime + ".log";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File mDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "ExceptionInfos");
            Log.v(TAG, mDirectory.toString());
            if (!mDirectory.exists()) {
                mDirectory.mkdir();
            }

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(mDirectory + File.separator + mFileName);
                fileOutputStream.write(sb.toString().getBytes());
                fileOutputStream.close();

                return mFileName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void handlerError(String string) {
        // 保存文件，设置文件名
        String mTime = SDF.format(new Date());
        String mFileName = "SFJ_业务异常" + mTime + ".log";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File mDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "Exception司法局业务异常");
            Log.v(TAG, mDirectory.toString());
            if (!mDirectory.exists()) {
                mDirectory.mkdir();
            }

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(mDirectory + File.separator + mFileName);
                fileOutputStream.write(string.getBytes());

                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
