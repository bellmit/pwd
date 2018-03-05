package com.newland.wstdd.login.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.RegistFirstRes;
import com.newland.wstdd.login.login.LoginBindActivity;
import com.newland.wstdd.login.login.LoginFragment;
import com.newland.wstdd.login.regist.RegistFragment;

public class BindRegistFragmentHandle extends Handler {
	public static final int BIND_REGIST_FIRST = 0;//第一次注册请求
	private LoginBindActivity context;
	public BindRegistFragmentHandle(LoginBindActivity context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case BIND_REGIST_FIRST:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof RegistFirstRes) {
					context.OnHandleResultListener(msg.obj, BIND_REGIST_FIRST);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
