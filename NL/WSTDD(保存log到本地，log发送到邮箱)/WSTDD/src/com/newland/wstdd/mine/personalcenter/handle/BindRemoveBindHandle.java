package com.newland.wstdd.mine.personalcenter.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.beanresponse.CheckCodeRes;
import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.RegistFirstRes;
import com.newland.wstdd.login.login.LoginFragment;
import com.newland.wstdd.login.regist.RegistFragment;
import com.newland.wstdd.mine.personalcenter.RemoveBindActivity;
import com.newland.wstdd.mine.personalcenter.beanres.BindRes;
import com.newland.wstdd.mine.personalcenter.beanres.RemoveBindRes;

public class BindRemoveBindHandle extends Handler {
	public static final int BIND = 0;//绑定
	public static final int REMOVE_BIND = 1;//解除绑定
	private RemoveBindActivity context;
	public BindRemoveBindHandle(RemoveBindActivity context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case BIND:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof BindRes) {
					context.OnHandleResultListener(msg.obj, BIND);
				}
			}
			break;
		
	  case REMOVE_BIND:
		// 只有当msg.obj对象为空，表示访问服务器成功
		if (msg.obj != null) {
			if (msg.obj instanceof String) {
				context.OnFailResultListener((String)msg.obj);
			} else if (msg.obj instanceof RemoveBindRes) {
				context.OnHandleResultListener(msg.obj, REMOVE_BIND);
			}
		}
		break;
		}
		super.handleMessage(msg);
	}

}
