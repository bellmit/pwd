package com.newland.wstdd.mine.personalcenter;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.mine.beanresponse.MinePersonInfoGetRes;
import com.newland.wstdd.mine.personalcenter.bean.MineEditPersonRes;

public class MinePersonInfoGetHandle extends Handler {
	public static final int PERSON_INFO_GET = 0;// 第一次注册请求
	public static final int PERSON_EDIT_INFO = 1;// 个人信息编辑提交
	private MinePersonalCenterActivity context;

	public MinePersonInfoGetHandle(MinePersonalCenterActivity context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case PERSON_INFO_GET:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String) msg.obj);
				} else if (msg.obj instanceof MinePersonInfoGetRes) {
					context.OnHandleResultListener(msg.obj, PERSON_INFO_GET);
				}
			}
			break;
		case PERSON_EDIT_INFO:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String) msg.obj);
				} else if (msg.obj instanceof MineEditPersonRes) {
					context.OnHandleResultListener(msg.obj, PERSON_EDIT_INFO);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
