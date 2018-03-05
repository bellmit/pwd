package com.newland.wstdd.mine.managerpage.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.RegistFirstRes;
import com.newland.wstdd.login.login.LoginFragment;
import com.newland.wstdd.login.regist.RegistFragment;
import com.newland.wstdd.mine.beanresponse.DeleteActivityRes;
import com.newland.wstdd.mine.managerpage.ManagerPageActivity;
import com.newland.wstdd.mine.managerpage.beanresponse.MyActivityListRes;
import com.newland.wstdd.mine.managerpage.beanresponse.OnTddRecommendRes;
import com.newland.wstdd.mine.managerpage.beanresponse.OpenActivityPeoplesRes;
import com.newland.wstdd.mine.managerpage.beanresponse.RegistrationCheckRes;
import com.newland.wstdd.mine.managerpage.beanresponse.RegistrationStateRes;
import com.newland.wstdd.mine.managerpage.coversetting.ModifyCoverRes;

public class ManagerpageHandle extends Handler {
	public static final int OPENACTIVITY_PEOPLES = 0;//是否公开活动报名人数
	public static final int ONTDD_RECOMMENT = 1;//是否上团大大热搜
	public static final int REGISTRATION_STATE = 2;//是否公开活动报名人数
	public static final int REGISTRATION_CHECK = 3;//报名是否需要审核
	public static final int DELETE_ACTIVITY = 4;//删除活动
	public static final int MODIFY_COVER = 5;//更改封面
	
	private ManagerPageActivity context;
	public ManagerpageHandle(ManagerPageActivity context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case OPENACTIVITY_PEOPLES:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof OpenActivityPeoplesRes) {
					context.OnHandleResultListener(msg.obj, OPENACTIVITY_PEOPLES);
				}
			}
			break;
		case ONTDD_RECOMMENT:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof OnTddRecommendRes) {
					context.OnHandleResultListener(msg.obj, ONTDD_RECOMMENT);
				}
			}
			break;
		case REGISTRATION_STATE:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof RegistrationStateRes) {
					context.OnHandleResultListener(msg.obj, REGISTRATION_STATE);
				}
			}
			break;
		case REGISTRATION_CHECK:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof RegistrationCheckRes) {
					context.OnHandleResultListener(msg.obj, REGISTRATION_CHECK);
				}
			}
			break;
		case DELETE_ACTIVITY:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof DeleteActivityRes) {
					context.OnHandleResultListener(msg.obj, DELETE_ACTIVITY);
				}
			}
			break;	
		case MODIFY_COVER:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof ModifyCoverRes) {
					context.OnHandleResultListener(msg.obj, MODIFY_COVER);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
