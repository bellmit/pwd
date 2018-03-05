package com.newland.wstdd.mine.managerpage.ilike.likelisthandle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.RegistFirstRes;
import com.newland.wstdd.login.login.LoginFragment;
import com.newland.wstdd.login.regist.RegistFragment;
import com.newland.wstdd.mine.managerpage.ilike.LikeListActivity;
import com.newland.wstdd.mine.managerpage.ilike.beanresponse.LikeListRes;

public class LikeListHandle extends Handler {
	public static final int LIKE_LIST = 0;//第一次注册请求
	private LikeListActivity context;
	public LikeListHandle(LikeListActivity context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case LIKE_LIST:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof LikeListRes) {
					context.OnHandleResultListener(msg.obj, LIKE_LIST);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
