package com.newland.wstdd.login.handle;

import android.R.integer;
import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.ThirdLoginRes;
import com.newland.wstdd.login.login.LoginFragment;

public class LoginFragmentHandle extends Handler {
	public static final int USER_LOGIN = 0;//正常登录
	public static final int THIRD_LOGIN = 1;//第三方登录

	private LoginFragment context;

	public LoginFragmentHandle(LoginFragment context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case USER_LOGIN:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String) msg.obj);
				} else if (msg.obj instanceof LoginRes) {
//					com.newland.wstdd.common.tools.UiHelper.ShowOneToast(context.getActivity(), "请求成功了");
					context.OnHandleResultListener(msg.obj, USER_LOGIN);
				}
			}
			break;
			
		case THIRD_LOGIN:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String) msg.obj);
				} else if (msg.obj instanceof ThirdLoginRes) {
//					com.newland.wstdd.common.tools.UiHelper.ShowOneToast(context.getActivity(), "请求成功了");
					context.OnHandleResultListener(msg.obj, THIRD_LOGIN);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
