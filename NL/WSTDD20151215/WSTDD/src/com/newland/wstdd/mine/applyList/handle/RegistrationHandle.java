package com.newland.wstdd.mine.applyList.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.mine.applyList.ManagerApplyListActivity;
import com.newland.wstdd.mine.applyList.bean.RegistrationListRes;
import com.newland.wstdd.mine.applyList.bean.UpdateRegistrationListRes;
import com.newland.wstdd.mine.applyList.beanres.MailUrlRes;

public class RegistrationHandle extends Handler {
	public static final int REGISTRATION_LIST = 0;//报名人员列表
	public static final int UPDATE_REGISTRATION_LIST=1;//更新报名人员列表
	public static final int MAIL_URL=2;//发送邮箱地址  导出名单
	private ManagerApplyListActivity context;
	public RegistrationHandle(ManagerApplyListActivity managerApplyListActivity) {
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
			
		case UPDATE_REGISTRATION_LIST:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof UpdateRegistrationListRes) {
					context.OnHandleResultListener(msg.obj, UPDATE_REGISTRATION_LIST);
				}
			}
			break;
			
		case MAIL_URL:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof MailUrlRes||msg.obj==null) {
					context.OnHandleResultListener(msg.obj, MAIL_URL);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
