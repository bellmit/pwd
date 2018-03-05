package com.newland.wstdd.originate;

import android.app.Activity;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.common.AppManager;


/**
 * 发起--我的活动
 * 
 * @author H81 2015-10-31
 * 
 */
public class OriginateMyActivity extends BaseFragmentActivity {
	@Override
	protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
		super.onChildTitleChanged(childActivity, title);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		AppManager.getAppManager().addActivity(this);// 添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮
	}

	@Override
	protected void processMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
