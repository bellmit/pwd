package com.newland.wstdd.find.categorylist.registrationedit.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.CancelRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.editregistration.EditRegistrationEditActivity;

/**
 * 提交报名活动的处理
 * @author Administrator
 *
 */
public class EditCancelRegistrationHandle extends Handler {
	public static final int CANCEL_REGISTRATION = 1;//发现热门活动
	private EditRegistrationEditActivity context;
	public EditCancelRegistrationHandle(EditRegistrationEditActivity context) {
		this.context = context;
	}

	
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case CANCEL_REGISTRATION:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
											  
				} else if (msg.obj instanceof CancelRegistrationRes) {
					context.OnHandleResultListener(msg.obj, CANCEL_REGISTRATION);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
