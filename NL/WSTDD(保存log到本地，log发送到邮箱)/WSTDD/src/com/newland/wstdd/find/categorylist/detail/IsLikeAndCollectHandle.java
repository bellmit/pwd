/**
 * @fields IsLikeAndCollectHandle.java
 */
package com.newland.wstdd.find.categorylist.detail;


import com.newland.wstdd.find.categorylist.detail.bean.IsLikeAndCollectRes;

import android.os.Handler;
import android.os.Message;

/**活动是否点赞收藏 处理
 * @author H81 2015-11-26
 *
 */
public class IsLikeAndCollectHandle extends Handler{
	public static final int ISLIKEANDCOLLECT = 25;//
	private FindChairDetailActivity context;

	public IsLikeAndCollectHandle(FindChairDetailActivity context) {
		this.context = context;
	}

	public void handleMessage(Message msg) {
		switch (msg.what) {
		case ISLIKEANDCOLLECT:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String) msg.obj);
				} else if (msg.obj instanceof IsLikeAndCollectRes) {
					context.OnHandleResultListener(msg.obj, ISLIKEANDCOLLECT);
				}
			}
			break;
		default:
			break;
		}
	}
}
