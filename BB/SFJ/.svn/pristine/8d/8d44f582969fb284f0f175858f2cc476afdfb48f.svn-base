package com.tastebychance.sfj.common.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.tastebychance.sfj.MyAppCompatActivity;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.bean.ResInfo;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.common.MyBaseHandler;
import com.tastebychance.sfj.common.web.bean.ShowWebBean;
import com.tastebychance.sfj.util.CrashHandler;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.util.UrlManager;
import com.tastebychance.sfj.util.okgoutils.CommonOkGo;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 展示用户须知，入会须知 （不可分享、点赞、收藏）
 *
 * @author shenbinghong
 * @time 2018/1/26 12:02
 * @path rongshanghui.tastebychance.com.rongshanghui.common.web.ShowWebInfoActivity.java
 */
public class ShowWebInfoActivity extends MyAppCompatActivity {

    @BindView(R.id.webview)
    WebView webView;

    private static class MyHandler<T extends Activity> extends MyBaseHandler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t){
                try {
                    ResInfo resInfo = (ResInfo) msg.obj;
                    switch (msg.what) {
                        case Constants.WHAT_GETDATA:
                            break;
                        default:break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private MyHandler handler = new MyHandler(this);

    private ShowWebBean showWebBean;
    private boolean toRead = false;


    /**
     * 视频全屏参数
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_webinfo);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (null != getIntent()) {
            showWebBean = (ShowWebBean) getIntent().getSerializableExtra("showWebBean");
            toRead  = showWebBean.isToRead();
        }

        if (null != showWebBean) {
            setTitle();
        }
        setStatusBar();

        if (toRead){
            toRead();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != showWebBean) {
            setWebView();
        }

        System.out.println("Find----------------------------------------------------" + getTaskId());
        incHints();
    }

    @Override
    protected void onDestroy() {
        try {
            ((RelativeLayout)webView.getParent()).removeView(webView);//将webview 从它的parent上面移除
            webView.stopLoading();//停止加载
            webSettings.setJavaScriptEnabled(false);//取消对JavaScript的支持，移除绑定服务，否则某些特定系统会报错
            webView.clearHistory();//清除历史记录
            webView.removeAllViews();//移除所有视图
            webView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * token	是	string	token
     id	是	int	信件id
     */
    private void toRead(){
        CommonOkGo.getInstance().generateLoading(findViewById(R.id.activity_show_webinfo_rootlyaout));

        final String url = UrlManager.URL_READMAIL;
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("token", SystemUtil.getInstance().getStrFromSP(Constants.KEY_TOKEN));
        map.put("id", showWebBean.getId() + "");
        CommonOkGo.getInstance().postByOkGo(url, map, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    CommonOkGo.getInstance().dialogCancel();
                    final ResInfo resInfo = JSONObject.parseObject(s, ResInfo.class);
                    if (resInfo.getCode() == Constants.NET_RETURNCODE_SUC) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.what = Constants.WHAT_GETDATA;
                                msg.obj = resInfo;
                                handler.sendMessage(msg);
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = CommonOkGo.ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        CommonOkGo.getInstance().myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                CommonOkGo.getInstance().dialogCancel();
                super.onError(call, response, e);
            }
        });
    }

    private WebSettings webSettings;

    private void setWebView() {
        webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);// 设置WebView属性，能够执行Javascript脚本
        webSettings.setAllowFileAccess(true);// 设置可以访问文件
        webSettings.setBuiltInZoomControls(true);// 设置支持缩放
        webSettings.setDomStorageEnabled(true);//设置适应Html5的一些方法

        //全屏播放
//        WebSettings webSettings = webview.getSettings();
//        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setBlockNetworkImage(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容


        // 设置Web视图
        webView.setWebViewClient(new webViewClient());

        //全屏播放
        webView.setWebChromeClient(new WebChromeClient() {

            /*** 视频播放相关的方法 **/
            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(ShowWebInfoActivity.this);
                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // 增加Javascript异常监控
                CrashReport.setJavascriptMonitor(webView, true);
                super.onProgressChanged(view, newProgress);
            }

        });

        // 加载Web地址
        webView.loadUrl(showWebBean.getUrl());
    }

    /**
     * ----------------------------------------视频播放全屏 --begin---------------------------
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        ShowWebInfoActivity.this.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(ShowWebInfoActivity.this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        webView.setVisibility(View.VISIBLE);
    }

    /**
     * 全屏容器界面
     */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
                if (customView != null) {
                    hideCustomView();
                } else if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    /**
     * ----------------------------------------视频播放全屏 --begin---------------------------
     **/

    // Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (ShowWebInfoActivity.this == null) {
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
        //动态设置状态栏下方的控件（view）的高度
        ImageView left_btn =  findViewById(R.id.head_left_iv);
        ImageView right_btn =  findViewById(R.id.head_right_iv);
        TextView right_tv =  findViewById(R.id.head_right_tv);
        TextView center_tv =  findViewById(R.id.head_center_title_tv);
        if (center_tv != null) {
            center_tv.setText(showWebBean.getTitle());
        }
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowWebInfoActivity.this.finish();
                }
            });
        }
        if (right_btn != null) {
            right_btn.setVisibility(View.GONE);
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
   /* private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //双击退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
            if (System.currentTimeMillis() - firstTime > 2000){
                Toast.makeText(ShowWebActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }else{
                MyApplication.getAppContext().clearStatck();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }*/

    private void incHints() {
        //取到已经保存的token（登录后的信息）
        /*String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //采用okhttp3来进行网络请求
        final String url = UrlManager.URL_INCHITS;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if (null != SystemUtil.getInstance().getUserInfo()) {
            builder.add("user_id", SystemUtil.getInstance().getUserInfo().getId() + "");
            Log.i(Constants.TAG, SystemUtil.getInstance().getToken());
        }
        if (showWebBean != null) {
            builder.add("post_id", showWebBean.getId() + "");
        }

        RequestBody formBody = builder.build();
        if (null == formBody) {
            return;
        }
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                dialogCancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dialogCancel();
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    final ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });*/
    }
}
