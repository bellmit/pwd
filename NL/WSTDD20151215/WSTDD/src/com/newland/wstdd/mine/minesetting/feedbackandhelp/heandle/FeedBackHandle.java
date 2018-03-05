package com.newland.wstdd.mine.minesetting.feedbackandhelp.heandle;

import com.newland.wstdd.mine.minesetting.feedbackandhelp.FeedBackActivity;
import com.newland.wstdd.mine.minesetting.feedbackandhelp.bean.FeedBackRes;

import android.os.Handler;
import android.os.Message;

/**意见反馈
 * @author Administrator
 * 2015-12-8
 */
public class FeedBackHandle extends Handler{
	public static final int FEED_BACK = 0 ;
	private FeedBackActivity context;
	public FeedBackHandle(FeedBackActivity context) {
		this.context= context;
	}
	
	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case FEED_BACK:
			//只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				}else  if (msg.obj instanceof FeedBackRes) {
					context.OnHandleResultListener(msg.obj, FEED_BACK);
				}
			}
			break;

		default:
			break;
		}
		super.handleMessage(msg);
	}
}
