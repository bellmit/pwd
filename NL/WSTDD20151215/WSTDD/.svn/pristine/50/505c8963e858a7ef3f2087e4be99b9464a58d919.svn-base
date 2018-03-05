package com.newland.wstdd.originate.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.originate.beanresponse.OriginateSearchRes;
import com.newland.wstdd.originate.origateactivity.OriginateChairActivity;
import com.newland.wstdd.originate.origateactivity.beanresponse.SingleActivityPublishRes;
import com.newland.wstdd.originate.search.OriginateSearchActivity;

public class SingleActivityPublishHandle extends Handler {
	public static final int SINGLE_ACTIVITY_PUBLISH = 0;//第一次注册请求
	private OriginateChairActivity context;
	public SingleActivityPublishHandle(OriginateChairActivity context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case SINGLE_ACTIVITY_PUBLISH:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof SingleActivityPublishRes) {
					context.OnHandleResultListener(msg.obj, SINGLE_ACTIVITY_PUBLISH);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
