package com.tastebychance.sonchance.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.homepage.HomeActivity;
import com.tastebychance.sonchance.util.UrlManager;
/**
 * 关于界面
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class AboutActivity extends MyBaseActivity{

	private WebView webView;
	private WebSettings webSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_khxz);

		setWebView();
		setTitle();
	}

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
		webView.loadUrl(UrlManager.URL_ABOUT);

		// 设置Web视图
		webView.setWebViewClient(new webViewClient());
	}

	// Web视图
	private class webViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (AboutActivity.this == null) {
				return false;
			}
			//调用拨号程序
			if (url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:") || url.startsWith("smsto:")) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);
				return true;
			}
			return false;
		}
	}

	/**
	 * 设置标题
	 */
	private void setTitle() {
		ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
		ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
		TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
		if (center_tv != null)
			center_tv.setText("关于我们");
		if (left_btn != null) {
			left_btn.setVisibility(View.INVISIBLE);
		}
		if (right_btn != null) {
			right_btn.setVisibility(View.VISIBLE);
			right_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_share));
		}
		if (right_tv != null) {
			right_tv.setVisibility(View.GONE);
		}
	}


	/*//公司简介
	public void aboutInstrumentClick(View view){
		Toast.makeText(this,"该功能正在开发！",Toast.LENGTH_SHORT).show();
	}*/
	//记录用户首次点击返回键的时间
	private long firstTime = 0;
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//双击退出程序
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
			if (System.currentTimeMillis() - firstTime > 2000){
				Toast.makeText(AboutActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
				firstTime = System.currentTimeMillis();
			}else{
				MyApplication.getAppContext().clearStatck();
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
}
