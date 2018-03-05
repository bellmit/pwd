package com.newland.wstdd.mine.managerpage.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.mine.managerpage.ManagerPageActivity;
import com.newland.wstdd.originate.beanresponse.SingleActivityRes;
/**
 * 单个活动的处理
 * @author Administrator
 *
 */
public class ManagerPagerSingleActivityHandle extends Handler {
	public static final int SINGLE_ACTIVITY = 30;//发现热门活动
	private ManagerPageActivity context;
	public ManagerPagerSingleActivityHandle(ManagerPageActivity context) {
		this.context = context;
	}

	
	public void handleMessage(Message msg) {
		switch (msg.what) {
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
