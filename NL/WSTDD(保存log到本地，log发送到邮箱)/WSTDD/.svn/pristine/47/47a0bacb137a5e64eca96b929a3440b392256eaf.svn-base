/**
 * 
 */
package com.newland.wstdd.find.categorylist.detail;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.find.categorylist.detail.bean.LikeRes;

/**点赞 处理
 * @author H81 2015-11-22
 *
 */
public class LikeHandle extends Handler {
	public static final int LIKE = 19;//
	private FindChairDetailActivity context;

	public LikeHandle(FindChairDetailActivity context) {
		this.context = context;
	}

	public void handleMessage(Message msg) {
		switch (msg.what) {
		case LIKE:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String) msg.obj);
				} else if (msg.obj instanceof LikeRes) {
					context.OnHandleResultListener(msg.obj, LIKE);
				}
			}
			break;

		default:
			break;
		}
	}
}
