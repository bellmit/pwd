/**
 * 
 */
package com.newland.wstdd.find.categorylist.detail;

import com.newland.wstdd.find.categorylist.detail.bean.CollectRes;

import android.os.Handler;
import android.os.Message;

/**
 * 收藏 处理
 * 
 * @author H81 2015-11-22
 * 
 */
public class CollectHandle extends Handler {
	public static final int COLLECT = 18;//
	private FindChairDetailActivity context;

	public CollectHandle(FindChairDetailActivity context) {
		this.context = context;
	}

	public void handleMessage(Message msg) {
		switch (msg.what) {
		case COLLECT:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String) msg.obj);
				} else if (msg.obj instanceof CollectRes) {
					context.OnHandleResultListener(msg.obj, COLLECT);
				}
			}
			break;

		default:
			break;
		}
	}
}
