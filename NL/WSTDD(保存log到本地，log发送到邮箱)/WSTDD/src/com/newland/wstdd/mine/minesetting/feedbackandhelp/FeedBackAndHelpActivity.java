package com.newland.wstdd.mine.minesetting.feedbackandhelp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
import com.newland.wstdd.common.widget.PengTextView;

/**反馈与帮助界面
 * @author Administrator
 * 2015-12-7
 */
public class FeedBackAndHelpActivity extends BaseFragmentActivity implements OnClickListener{
	private static final String TAG = "FeedBackAndHelpActivity";//收集异常日志tag
	private WebView webView;
	private PengTextView mine_setting_exit_feedback_ptv;//反馈
	private LinearLayout mine_setting_exit_feedback_layout;//与反馈功能一样
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		AppManager.getAppManager().addActivity(this);// 添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮
		setContentView(R.layout.activity_mine_setting_feedbackandhelp);
		
		 /**收集异常日志*/
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        //如果你要收集普通的日志到文件或者服务器，那么调用下面的方法即可。
        LogManager.getManager(getApplicationContext()).log(TAG, "onCreate",LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**收集异常日志*/
        
		setTitle();
		bindViews();
		setWebView();
	}

	@Override
	protected void onDestroy() {
		/** 收集异常日志 */
		LogManager.getManager(getApplicationContext()).unregisterActivity(this);
		/** 收集异常日志 */
		super.onDestroy();
	}
	/**
	 * 设置标题
	 */
	private void setTitle() {
		ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
		ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
		TextView center_tv = (TextView) findViewById(R.id.head_center_title);
		if (center_tv != null)
			center_tv.setText("反馈与帮助");
		if (left_btn != null) {
			left_btn.setVisibility(View.VISIBLE);
			left_btn.setImageDrawable(getResources().getDrawable(R.drawable.left_expandsion));
			left_btn.setOnClickListener(this);
		}
		if (right_btn != null) {
			right_btn.setVisibility(View.GONE);
		}
		if (right_tv != null) {
			right_tv.setVisibility(View.GONE);
		}
	}
	private void bindViews() {
		mine_setting_exit_feedback_ptv = (PengTextView) findViewById(R.id.mine_setting_exit_feedback_ptv);
		mine_setting_exit_feedback_ptv.setOnClickListener(this);
		mine_setting_exit_feedback_layout = (LinearLayout) findViewById(R.id.mine_setting_exit_feedback_ll);
		mine_setting_exit_feedback_layout.setOnClickListener(this);
	}
	/**
	 * 设置webview
	 */
	private WebSettings webSettings;
	private void setWebView() {
		webView = (WebView) findViewById(R.id.webview);
		webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.loadUrl("file:///android_asset/help.html");
	}
	@Override
	protected void processMessage(Message msg) {
	}

	@Override
	public void refresh() {
	}

	@Override
	public void initView() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_left_iv:
			finish();
			break;
		case R.id.mine_setting_exit_feedback_ptv:
			Intent intent= new Intent(FeedBackAndHelpActivity.this, FeedBackActivity.class);
			startActivity(intent);
			break;
		case R.id.mine_setting_exit_feedback_ll:
			Intent intent1= new Intent(FeedBackAndHelpActivity.this, FeedBackActivity.class);
			startActivity(intent1);
			break;
		default:
			break;
		}
	}
}
