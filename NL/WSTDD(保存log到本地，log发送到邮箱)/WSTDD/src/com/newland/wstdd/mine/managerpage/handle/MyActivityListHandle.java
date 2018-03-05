package com.newland.wstdd.mine.managerpage.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.mine.managerpage.MyActivitiesListAcitivity;
import com.newland.wstdd.mine.managerpage.beanresponse.MyActivityListRes;
import com.newland.wstdd.originate.beanresponse.SingleActivityRes;

public class MyActivityListHandle extends Handler {
	public static final int MYACTIVITY_LIST = 1;//我的发起，参与，收藏的活动列表
	public static final int SINGLE_ACTIVITY = 30;//发现热门活动
	private MyActivitiesListAcitivity context;
	public MyActivityListHandle(MyActivitiesListAcitivity context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		
		case MYACTIVITY_LIST:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof MyActivityListRes) {
					context.OnHandleResultListener(msg.obj, MYACTIVITY_LIST);
				}
			}
			break;
		case SINGLE_ACTIVITY:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof SingleActivityRes) {
					context.OnHandleResultListener(msg.obj, SINGLE_ACTIVITY);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
