package com.newland.wstdd.find.hotlist;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.find.hotlist.bean.FindCategoryRes;
import com.newland.wstdd.originate.beanresponse.SingleActivityRes;
/**
 * 发现-热门全部数据的列表界面 的处理
 * @author Administrator
 *
 */
public class HotListHandle extends Handler {
	public static final int HOT_ALLLIST = 0;//发现热门活动
	public static final int SINGLE_ACTIVITY = 30;//发现热门活动
	private HotListListViewActivity context;
	public HotListHandle(HotListListViewActivity context) {
		this.context = context;
	}

	
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case HOT_ALLLIST:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof FindCategoryRes) {
					context.OnHandleResultListener(msg.obj, HOT_ALLLIST);
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
