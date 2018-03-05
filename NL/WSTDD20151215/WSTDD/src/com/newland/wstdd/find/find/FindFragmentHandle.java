package com.newland.wstdd.find.find;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.find.find.bean.FindRes;
import com.newland.wstdd.originate.beanresponse.SingleActivityRes;
/**
 * 发现主界面的处理
 * @author Administrator
 *
 */
public class FindFragmentHandle extends Handler {
	public static final int FIND = 0;//第一次注册请求
	public static final int SINGLE_ACTIVITY = 30;//发现热门活动
	private FindFragment context;
	public FindFragmentHandle(FindFragment context) {
		this.context = context;
	}

	
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case FIND:
			// 只有当msg.obj不为字符串的时候，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof FindRes) {
					context.OnHandleResultListener(msg.obj, FIND);
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
