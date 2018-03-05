package com.test;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.beanresponse.CheckCodeRes;
import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.RegistFirstRes;
import com.newland.wstdd.login.login.LoginFragment;
import com.newland.wstdd.login.regist.RegistFragment;
import com.newland.wstdd.shortcut.ShortCutActivity;

public class TestHandle extends Handler {
	public static final int CANCEL_USER = 0;//注销用户
	private ShortCutActivity context;
	public TestHandle(ShortCutActivity context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case CANCEL_USER:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof DeleteUserInfoRes) {
					context.OnHandleResultListener(msg.obj, CANCEL_USER);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
