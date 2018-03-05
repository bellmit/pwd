package com.newland.wstdd.mine.managerpage.multitext.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.mine.applyList.bean.RegistrationListRes;
import com.newland.wstdd.mine.managerpage.multitext.MultiTextActivity;
import com.newland.wstdd.mine.managerpage.multitext.bean.SendMessageRes;

public class MultiTextRegistrationHandle extends Handler {
	public static final int REGISTRATION_LIST = 0;//报名人员列表
	public static final int MULTITEXT = 1;//群发消息
	private MultiTextActivity context;
	public MultiTextRegistrationHandle(MultiTextActivity managerApplyListActivity) {
		this.context = managerApplyListActivity;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case REGISTRATION_LIST:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof RegistrationListRes) {
					context.OnHandleResultListener(msg.obj, REGISTRATION_LIST);
				}
			}
			break;
		case MULTITEXT:
			//只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String ) {
					context.OnFailResultListener((String)msg.obj);
				}else if (msg.obj instanceof SendMessageRes) {
					context.OnHandleResultListener(msg.obj, MULTITEXT);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
