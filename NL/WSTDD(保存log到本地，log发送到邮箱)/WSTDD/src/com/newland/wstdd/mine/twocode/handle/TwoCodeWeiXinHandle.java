package com.newland.wstdd.mine.twocode.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.RegistFirstRes;
import com.newland.wstdd.login.login.LoginFragment;
import com.newland.wstdd.login.regist.RegistFragment;
import com.newland.wstdd.mine.twocode.WeiXinFragment;
import com.newland.wstdd.mine.twocode.beanresponse.TwoCodePayRes;

public class TwoCodeWeiXinHandle extends Handler {
	public static final int TWO_CODE_WEIXIN = 0;//第一次注册请求
	private WeiXinFragment context;
	public TwoCodeWeiXinHandle(WeiXinFragment context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case TWO_CODE_WEIXIN:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof TwoCodePayRes) {
					context.OnHandleResultListener(msg.obj, TWO_CODE_WEIXIN);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
