package com.tastebychance.sonchance.homepage.healthylife;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.UrlManager;
/**
 * 健康生活界面
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class HealthyLifeActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_khxz);

        setWebView();
        setTitle();
    }

    private WebView webView;
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

        webView.loadUrl(UrlManager.URL_HOME_HEALTHY + SystemUtil.getInstance().getToken());

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
        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (center_tv != null)
            center_tv.setText("健康生活");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new android.view.View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    HealthyLifeActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.VISIBLE);
            right_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_share));
        }
        if (right_tv != null) {
            right_tv.setVisibility(View.GONE);
        }
    }

}
