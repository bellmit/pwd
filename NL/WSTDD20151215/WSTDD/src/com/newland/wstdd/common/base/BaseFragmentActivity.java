package com.newland.wstdd.common.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.newland.wstdd.R;
import com.newland.wstdd.common.common.AppException;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.view.LoadingDialog;
import com.newland.wstdd.mine.applyList.dialog.MailDialog;

public abstract class BaseFragmentActivity extends FragmentActivity implements
		OnClickListener {

	protected abstract void processMessage(Message msg);

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			processMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		AppManager.getAppManager().addActivity(this);//添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}

	protected void handlerException(Exception e) {
		AppException.getAppException(this).handlerException(e);
	}

	public void sendEmptyMessage(int what) {
		handler.sendEmptyMessage(what);
	}

	public void sendEmptyMessageAtTime(int what, long uptimeMillis) {
		handler.sendEmptyMessageAtTime(what, uptimeMillis);
	}

	public void sendEmptyMessageDelayed(int what, long delayMillis) {
		handler.sendEmptyMessageDelayed(what, delayMillis);
	}

	public void sendMessage(Message msg) {
		handler.sendMessage(msg);
	}

	public void sendMessageAtFrontOfQueue(Message msg) {
		handler.sendMessageAtFrontOfQueue(msg);
	}

	public void sendMessageAtTime(Message msg, long uptimeMillis) {
		handler.sendMessageAtTime(msg, uptimeMillis);
	}

	public void sendMessageDelayed(Message msg, long delayMillis) {
		handler.sendMessageDelayed(msg, delayMillis);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.head_left_iv:
			finish();
			break;

		default:
			break;
		}
		
	}
	
	public LoadingDialog dialog;//加载对话框
	
	public void refresh(){
		if (dialog == null) {
			initDialog(this);
		}else {
			dialog.dismiss();
			initDialog(this);
		}
	}
	public void initDialog(Context context) {
		dialog = new LoadingDialog(context);
		dialog.setTvMessage("正在加载...");
		if (!isFinishing()) {
			dialog.show(true);
		}else {
			dialog.show(false);
		}
	}
	
	public abstract void initView();

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
}
