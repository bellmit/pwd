package com.newland.wstdd.originate.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.originate.OriginateFragment;
import com.newland.wstdd.originate.beanresponse.OriginateFragmentRes;
import com.newland.wstdd.originate.beanresponse.OriginateSearchRes;
import com.newland.wstdd.originate.search.OriginateSearchActivity;
import com.newland.wstdd.originate.search.OriginateSearchResultFragment;

public class OriginateFragmentHandle extends Handler {
	public static final int ORIGINATE_FRAGMENT = 1;//搜索请求
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
		}
		super.handleMessage(msg);
	}

}
