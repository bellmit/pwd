package com.newland.wstdd.originate.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.mine.minesetting.beanresponse.VersionRes;
import com.newland.wstdd.originate.OriginateFragment;
import com.newland.wstdd.originate.beanresponse.OriginateFragmentRes;

public class OriginateFragmentHandle extends Handler {
	public static final int ORIGINATE_FRAGMENT = 1;//搜索请求
	public static final int VERSION_INFO = 0;
	private OriginateFragment context;
	public OriginateFragmentHandle(OriginateFragment context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {		
		case ORIGINATE_FRAGMENT:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof OriginateFragmentRes) {
					context.OnHandleResultListener(msg.obj, ORIGINATE_FRAGMENT);
				}
			}
			break;
		case VERSION_INFO:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof VersionRes) {
					context.OnHandleResultListener(msg.obj, VERSION_INFO);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
