package com.tastebychance.sonchance.util;

import android.content.Context;
import android.content.Intent;

import com.tastebychance.sonchance.loginandregister.LoginActivity;

public class IntentToLogin {
	
	/**跳转到登录界面
	 * @param context
	 */
	public IntentToLogin(Context context) {
		Intent intent = new Intent(context, LoginActivity.class);
		context.startActivity(intent);
	}
	
}
