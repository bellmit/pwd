package rongshanghui.tastebychance.com.rongshanghui.common.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyAppCompatActivity;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.ManagerJJActivity;
import rongshanghui.tastebychance.com.rongshanghui.mime.managercommon.jjedt.jcvideoplayer.MyUserActionStandard;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.ScreenUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;

import static rongshanghui.tastebychance.com.rongshanghui.common.web.ShowWebActivity.COVER_SCREEN_PARAMS;

/**
 * 类描述：ShowWeb2Activity 单纯展示web内容 （不可分享、点赞、收藏）
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/5 10:13
 * 修改人：
 * 修改时间：2017/12/5 10:13
 * 修改备注：
 *
 * @version 1.0
 */
public class ShowWeb2Activity extends MyAppCompatActivity {

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.jc_video)
    JCVideoPlayerStandard jcVideo;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    private ShowWebBean showWebBean;


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
        setContentView(R.layout.activity_show_web2);
        ButterKnife.bind(this);

        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);

        // set a custom tint color for all system bars
        //        tintManager.setTintColor(Color.parseColor("#990000FF"));
        tintManager.setTintColor(getResources().getColor(R.color.colorPrimaryDark));

        // set a custom navigation bar resource
        //        tintManager.setNavigationBarTintResource(R.drawable.my_tint);
        // set a custom status bar drawable
        //        tintManager.setStatusBarTintDrawable(MyDrawable);

        Intent intent = getIntent();
        if (null != getIntent()) {
            showWebBean = (ShowWebBean) getIntent().getSerializableExtra("showWebBean");
        }

        if (null != showWebBean) {
            setTitle();

            if (showWebBean.isJJ() && StringUtil.isNotEmpty(showWebBean.getVideoUrl())) {
                jcVideo.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.width = ScreenUtils.getScreenWidth() * 94 / 100;
                int marginLeft = ScreenUtils.getScreenWidth() * 3 / 100;
                lp.setMargins(marginLeft, 0, marginLeft, 20);
                jcVideo.setLayoutParams(lp);

                setVideo();
            } else {
                jcVideo.setVisibility(View.GONE);
            }
        }
        setStatusBar();
    }

    private void setVideo() {
        if (StringUtil.isEmpty(showWebBean.getVideoUrl())) {
            return;
        }
        jcVideo.setUp(showWebBean.getVideoUrl().replaceAll("\"", ""), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        Picasso.with(ShowWeb2Activity.this)
                .load(R.drawable.shiping)
                .into(jcVideo.thumbImageView);
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
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
            ((RelativeLayout) webView.getParent()).removeView(webView);//将webview 从它的parent上面移除
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
                FrameLayout frameLayout = new FrameLayout(ShowWeb2Activity.this);
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

        ShowWeb2Activity.this.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(ShowWeb2Activity.this);
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
            if (ShowWeb2Activity.this == null) {
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
        ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
        ImageView right_btn = (ImageView) findViewById(R.id.head_right_iv);
        TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
        TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
        if (center_tv != null)
            center_tv.setText(showWebBean.getTitle());
        if (left_btn != null) {
            left_btn.setVisibility(View.VISIBLE);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowWeb2Activity.this.finish();
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

    private void incHints() {
        //取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
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
        });
    }
}
