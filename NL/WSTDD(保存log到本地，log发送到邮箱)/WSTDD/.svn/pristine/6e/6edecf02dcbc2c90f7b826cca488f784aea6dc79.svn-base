package com.newland.wstdd.common.activity;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.mine.minesetting.beanresponse.VersionRes;

public class CheckVersionHandle extends Handler{

	public static final int VERSION_INFO = 0;
	private HomeActivity context;
	public CheckVersionHandle(HomeActivity context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {		
		case VERSION_INFO:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof VersionRes) {
					context.OnHandleResultListener(msg.obj, VERSION_INFO);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
