package com.newland.wstdd.common.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.content.Intent;

import com.newland.wstdd.login.login.LoginActivity;

/**
 * 应用程序异常类：用于捕获异常
 * @author linh
 */
public class AppException implements UncaughtExceptionHandler{
	
	private static Context context=null;
	static AppException appException=null;
	
	public static AppException getAppException(Context context){
		AppException.context=context;
		if(null==appException){
			appException=new AppException();
		}
		return appException;
	}

	public void handlerException(Thread thread, Throwable ex) {
		System.out.println("===============uncaughtException=================");
		System.out.println("thread=======================" + thread.getName());
		//判断是否为UI线程异常，thread.getId()==1 为UI线程
		if (thread.getId() != 1) {
			thread.interrupt();
			System.out.println("==============非UI线程异常=================");
		}else{
			Writer wr = new StringWriter();  
			PrintWriter err = new PrintWriter(wr);  
	        ex.printStackTrace(err);  
	        Throwable cause = ex.getCause();  
	        while (cause != null) {  
	            cause.printStackTrace(err);  
	            cause = cause.getCause();  
	        }  
	        err.close();  
	        System.out.println("result=================="+wr.toString());
			AppManager.getAppManager().finishAllActivity();
			Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);                     
            android.os.Process.killProcess(android.os.Process.myPid()); 
		}
	}

	public void handlerException(Exception e) {
		System.out.println("==============uncaughtException===============");
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
        System.out.println("result=================="+result);
        AppManager.getAppManager().finishAllActivity();
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		handlerException(thread, ex);
	}

}
