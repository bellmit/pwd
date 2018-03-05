package com.newland.wstdd.originate.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.originate.beanresponse.OriginateSearchRes;
import com.newland.wstdd.originate.beanresponse.SingleActivityRes;
import com.newland.wstdd.originate.search.OriginateSearchResultFragment;

public class OriginateSearchHandle extends Handler {
	public static final int ORIGINATE_SEARCH = 0;//搜索请求
	public static final int SINGLE_ACTIVITY = 30;//发现热门活动
	private OriginateSearchResultFragment context;
	public OriginateSearchHandle(OriginateSearchResultFragment context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case ORIGINATE_SEARCH:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof OriginateSearchRes) {
					context.OnHandleResultListener(msg.obj, ORIGINATE_SEARCH);
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
