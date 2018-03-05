package com.newland.wstdd.common.log.transaction.log.manager;

import android.content.Context;

public class LogManager {
	private static ILogManager sManager;

	/**
	 * Get singleton manager. DO NOT GET MANAGER BY THE CONTEXT OF ACTIVITY.
	 * 
	 * @param context
	 *            The context of application.
	 * @return
	 */
	public synchronized static ILogManager getManager(Context context) {
		if (context == null) {
			return null;
		}

		if (sManager == null) {
			sManager = new LogManagerImpl(context);
		}

		return sManager;
	}
}