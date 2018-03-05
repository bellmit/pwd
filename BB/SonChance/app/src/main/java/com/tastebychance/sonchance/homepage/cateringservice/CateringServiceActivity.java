package com.tastebychance.sonchance.homepage.cateringservice;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
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
 * 配餐服务
 *
 * @author shenbh
 * @date 2017/9/5
 */
public class CateringServiceActivity extends MyBaseActivity {

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
        webView.setWebChromeClient(new WebChromeClient());

        webSettings = webView.getSettings();
        // 设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        webSettings.setBuiltInZoomControls(true);


        // 设置Web视图
//        webView.setWebViewClient(new webViewClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
//                String urls = url+"/token/"+SystemUtil.getInstance().getToken();
//                return super.shouldOverrideUrlLoading(view, urls);
            }
        });
        webView.loadUrl(UrlManager.URL_HOME_PCSERVICE + SystemUtil.getInstance().getToken());
//        webView.loadUrl(UrlManager.requestWebURL + "/Wei/service/pcservice" );
    }

    // Web视图
    private class webViewClient extends WebViewClient {
       /* public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url+"/token/"+SystemUtil.getInstance().getToken());
            return true;
        }*/
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
            center_tv.setText("配餐服务");
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
            left_btn.setOnClickListener(new android.view.View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    CateringServiceActivity.this.finish();
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
