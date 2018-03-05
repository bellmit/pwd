package com.tastebychance.sonchance.find;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.homepage.HomeActivity;
import com.tastebychance.sonchance.util.UrlManager;
/**
 * 发现界面
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class FindActivity extends MyBaseActivity{

	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_khxz);

		setTitle();
//		setLayoutPadding(this,R.layout.register_khxz);
        setStatusBar();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setWebView();

		System.out.println("Find----------------------------------------------------"+getTaskId());
	}

	private WebSettings webSettings;

	private void setWebView() {
		webView = (WebView) findViewById(R.id.webview);
		webSettings = webView.getSettings();

		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置支持缩放
		webSettings.setBuiltInZoomControls(true);

		// webView.loadUrl("file:///android_asset/27_two.html");

//		webView.loadUrl("http://47.52.74.67/test/work/222222.html");
//		webView.loadUrl(UrlManager.requestWebURL + "/WebApp/discount");
		webView.loadUrl(UrlManager.URL_DISCOUNT);

		// 设置Web视图
		webView.setWebViewClient(new webViewClient());
	}

	// Web视图
	private class webViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	/**
	 * 设置标题
	 */
	private void setTitle() {
		//动态设置状态栏下方的控件（view）的高度
		View view = (View) findViewById(R.id.view1);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.height = statusHeight;
		view.setLayoutParams(lp);

		ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
		ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
		TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
		if (center_tv != null)
			center_tv.setText("发现");
		if (left_btn != null) {
			left_btn.setVisibility(View.GONE);
	/*		left_btn.setVisibility(View.VISIBLE);
			left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
			left_btn.setOnClickListener(new android.view.View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					FindActivity.this.finish();
				}
			});*/
		}
		if (right_btn != null) {
			right_btn.setVisibility(View.VISIBLE);
			right_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_share));
		}
		if (right_tv != null) {
			right_tv.setVisibility(View.GONE);
		}
	}

	//设置布局距离状态栏高度
    public void setLayoutPadding(Activity activity, DrawerLayout drawerLayout) {
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        contentLayout.getChildAt(1)
                .setPadding(contentLayout.getPaddingLeft(), statusHeight + contentLayout.getPaddingTop(),
                        contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
    }

	//记录用户首次点击返回键的时间
	private long firstTime = 0;
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//双击退出程序
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
			if (System.currentTimeMillis() - firstTime > 2000){
				Toast.makeText(FindActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
				firstTime = System.currentTimeMillis();
			}else{
				MyApplication.getAppContext().clearStatck();
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
}
