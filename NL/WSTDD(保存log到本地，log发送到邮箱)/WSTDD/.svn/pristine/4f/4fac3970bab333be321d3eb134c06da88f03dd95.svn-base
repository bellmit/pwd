package com.newland.wstdd.find.categorylist.registrationedit.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.EditRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.registration.RegistrationSubmitActivity;
/**
 * 提交报名活动的处理
 * @author Administrator
 *
 */
public class SubmitRegistrationHandle extends Handler {
	public static final int SUBMIT_REGISTRATION = 3;//提交报名信息
	private RegistrationSubmitActivity context;
	public SubmitRegistrationHandle(RegistrationSubmitActivity context) {
		this.context = context;
	}

	
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case SUBMIT_REGISTRATION:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof EditRegistrationRes) {
					context.OnHandleResultListener(msg.obj, SUBMIT_REGISTRATION);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
