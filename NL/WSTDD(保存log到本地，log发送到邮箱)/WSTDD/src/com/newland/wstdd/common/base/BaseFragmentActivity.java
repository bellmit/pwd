package com.newland.wstdd.common.base;

import android.content.Context;
import android.os.AsyncTask;
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
import com.newland.wstdd.common.dialog.CustomProgressDialog;
import com.newland.wstdd.common.view.LoadingDialog;

public abstract class BaseFragmentActivity extends FragmentActivity implements
		OnClickListener {
	private MainFrameTask mMainFrameTask = null;
	protected CustomProgressDialog progressDialog = null;

	
	
	
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
			  
	}

	@Override
	protected void onDestroy() {
		stopProgressDialog();
		
		if (mMainFrameTask != null && !mMainFrameTask.isCancelled()){
			mMainFrameTask.cancel(true);
		}
		
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
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
	public void refreshDialog(){
		 mMainFrameTask = new MainFrameTask(this);
	     mMainFrameTask.execute();
	}
	
	public void initDialog(Context context) {
		dialog = new LoadingDialog(context);
//		dialog.setTvMessage("正在加载...");
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
	
	
	

	private void startProgressDialog(){
		if (progressDialog == null){
			progressDialog = CustomProgressDialog.createDialog(this);
//	    	progressDialog.setMessage("正在加载中...");
		}
		
    	progressDialog.show();
	}
	
	private void stopProgressDialog(){
		if (progressDialog != null){
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	
	public class MainFrameTask extends AsyncTask<Integer, String, Integer>{
		private BaseFragmentActivity BaseFragmentActivity = null;
		
		public MainFrameTask(BaseFragmentActivity BaseFragmentActivity){
			this.BaseFragmentActivity = BaseFragmentActivity;
		}
		
		@Override
		protected void onCancelled() {
			stopProgressDialog();
			super.onCancelled();
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			
			try {
				if (progressDialog != null) {
					while(progressDialog.isContinueDialog()){
						Thread.sleep(1000);//睡一秒
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
			
		

		@Override
		protected void onPreExecute() {
			startProgressDialog();
		}

		@Override
		protected void onPostExecute(Integer result) {
			stopProgressDialog();
		}	
	}

	
	
	
	
}
