package com.newland.wstdd.find.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.find.RecommendListActivity;
import com.newland.wstdd.find.bean.FindRecommendRes;
/**
 * 推荐列表的处理
 * @author Administrator
 *
 */
public class FindRecommendListHandle extends Handler {
	public static final int FIND_RECOMMEND_LIST = 0;//发现热门活动
	private RecommendListActivity context;
	public FindRecommendListHandle(RecommendListActivity context) {
		this.context = context;
	}

	
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case FIND_RECOMMEND_LIST:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof FindRecommendRes) {
					context.OnHandleResultListener(msg.obj, FIND_RECOMMEND_LIST);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
