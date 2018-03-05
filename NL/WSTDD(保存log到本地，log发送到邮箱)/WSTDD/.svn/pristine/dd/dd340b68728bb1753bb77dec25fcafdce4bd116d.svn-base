package com.newland.wstdd.login.handle;

import android.R.integer;
import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.beanresponse.CheckCodeRes;
import com.newland.wstdd.login.beanresponse.LoginBindRes;
import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.RegistFirstRes;
import com.newland.wstdd.login.beanresponse.ThirdLoginRes;
import com.newland.wstdd.login.login.LoginBindActivity;
import com.newland.wstdd.login.login.LoginFragment;
import com.newland.wstdd.login.regist.RegistFragment;

public class CheckCodeHandle extends Handler {
	public static final int CHECK_CODE = 01;//第一次注册请求
	public static final int BING_LOGIN = 02;//绑定的请求
	public static final int LOGIN_BIND =03;//利用绑定进行登入
	private LoginBindActivity context;
	public CheckCodeHandle(LoginBindActivity context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case CHECK_CODE:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof CheckCodeRes) {
					context.OnHandleResultListener(msg.obj, CHECK_CODE);
				}
			}
			break;
		case BING_LOGIN:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof LoginBindRes) {
					context.OnHandleResultListener(msg.obj, BING_LOGIN);
				}
			}
			break;
		case LOGIN_BIND:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof ThirdLoginRes) {
					context.OnHandleResultListener(msg.obj, LOGIN_BIND);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
