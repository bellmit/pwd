package com.newland.wstdd.common.log.transaction.log.manager;

import android.app.Activity;

public interface ILogManager {
	/**
	 * Log
	 * 
	 * @param tag
	 * @param msg
	 * @param logType
	 * @return
	 */
	public boolean log(String tag, String msg, int logType);

	/**
	 * Register crash handler to handle exception.
	 * 
	 * @return
	 */
	public boolean registerCrashHandler();

	/**
	 * Unregister crash handler to handle exception.
	 * 
	 * @return
	 */
	public boolean unregisterCrashHandler();

	/**
	 * Register activity into the stack.
	 * 
	 * @param activity
	 * @return
	 */
	public boolean registerActivity(final Activity activity);

	/**
	 * Unregister activity into the stack.
	 * 
	 * @param activity
	 * @return
	 */
	public boolean unregisterActivity(final Activity activity);
}
