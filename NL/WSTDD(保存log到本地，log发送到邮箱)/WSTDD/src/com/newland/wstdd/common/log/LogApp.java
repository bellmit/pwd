package com.newland.wstdd.common.log;

import com.newland.wstdd.common.log.transaction.log.manager.LogManager;

import android.app.Application;

/**
 * Log application
 * 
 * @author Administrator
 * 
 */
public class LogApp extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		LogManager.getManager(getApplicationContext()).registerCrashHandler();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		LogManager.getManager(getApplicationContext()).unregisterCrashHandler();
	}
}
