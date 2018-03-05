package com.newland.wstdd.find.categorylist.registrationedit.handle;

import android.R.integer;
import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.CancelRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.EditRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.beanresponse.GetEditRegistrationRes;
import com.newland.wstdd.find.categorylist.registrationedit.editregistration.EditRegistrationEditActivity;

/**
 * 报名信息编辑
 * @author Administrator
 *
 */
public class EditRegistrationHandle extends Handler {
	public static final int GET_EDIT_REGISTRATION = 0;//报名信息编辑
	public static final int OK_EDIT_REGISTRATION = 1;//提交编辑好的报名信息编辑
	public static final int CANCEL_REGISTRATION = 2;//发现热门活动
	private EditRegistrationEditActivity context;
	public EditRegistrationHandle(EditRegistrationEditActivity context) {
		this.context = context;
	}

	
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case GET_EDIT_REGISTRATION:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof GetEditRegistrationRes) {
					context.OnHandleResultListener(msg.obj, GET_EDIT_REGISTRATION);
				}
			}
			break;
		case OK_EDIT_REGISTRATION:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof EditRegistrationRes) {
					context.OnHandleResultListener(msg.obj, OK_EDIT_REGISTRATION);
				}
			}
			break;
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
