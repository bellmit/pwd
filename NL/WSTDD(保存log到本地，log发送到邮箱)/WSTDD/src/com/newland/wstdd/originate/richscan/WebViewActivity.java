package com.newland.wstdd.originate.richscan;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.newland.wstdd.R;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;

public class WebViewActivity extends Activity{
	private static final String TAG = "WebViewActivity";//收集异常日志tag
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		AppManager.getAppManager().addActivity(this);// 添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮
		setContentView(R.layout.activity_web_view);
		
		 /**收集异常日志*/
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        //如果你要收集普通的日志到文件或者服务器，那么调用下面的方法即可。
        LogManager.getManager(getApplicationContext()).log(TAG, "onCreate",LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**收集异常日志*/
        
		String web = getIntent().getStringExtra("web");
		
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		 //设置可以访问文件 

		webView.getSettings().setAllowFileAccess(true); 

         //设置支持缩放 

		webView.getSettings().setBuiltInZoomControls(true);
		webView.loadUrl(web);
		
//		webView.loadUrl("http://www.cnblogs.com/over140/archive/2013/03/07/2947721.html");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
       webView.setWebViewClient(new WebViewClient(){
           @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            view.loadUrl(url);
            return true;
        }
           
        
       });
    }

	@Override
	protected void onDestroy() {
		/** 收集异常日志 */
		LogManager.getManager(getApplicationContext()).unregisterActivity(this);
		/** 收集异常日志 */
		super.onDestroy();
	}
	 @Override  

	    //设置回退   

	    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法   

	    public boolean onKeyDown(int keyCode, KeyEvent event) {   

	        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {   

	            webView.goBack(); //goBack()表示返回WebView的上一页面   

	            return true;   

	        }   

	        finish();//结束退出程序 

	        return false;   

	    }  
	}

