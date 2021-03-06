package rongshanghui.tastebychance.com.rongshanghui.common.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.tencent.wxop.stat.StatConfig;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventLoginSuc;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.qqshare.BaseUiListener;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.ImageUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.NoDoubleClickListener;
import rongshanghui.tastebychance.com.rongshanghui.util.PicassoUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UiHelper;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.wxshare.Util;

/**
 * ????????????ShowWebActivity ??????web?????? ?????????????????????????????????
 * ?????????: shenbh Email:shenbh@qq.com
 * ??????????????? 2017/10/31 18:43
 * ????????????
 * ???????????????2017/10/31 18:43
 * ???????????????
 *
 * @version 1.0
 */
public class ShowWebActivity extends MyBaseActivity {
    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.includestatusbar_rl)
    RelativeLayout includestatusbarRl;
    @BindView(R.id.head_bottomline)
    View headBottomline;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.detail_share_iv)
    ImageView detailShareIv;
    @BindView(R.id.detail_collect_iv)
    ImageView detailCollectIv;
    @BindView(R.id.detail_praise_iv)
    ImageView detailPraiseIv;
    @BindView(R.id.relativelayout)
    RelativeLayout relativelayout;
    @BindView(R.id.web_rootlayout)
    RelativeLayout webRootlayout;

    /**
     * ??????????????????
     */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;


    private ShowWebBean showWebBean;

    private static final int LIKE_WHAT = 100;
    private static final int COLLECT_WHAT = 101;

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler<T extends Activity> extends Handler {
        private final WeakReference<T> mT;

        public MyHandler(T t) {
            this.mT = new WeakReference<T>(t);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            final T t = mT.get();
            if (null != t) {
                try {
                    switch (msg.what) {
                        case LIKE_WHAT:
                            ToastUtils.showOneToast(t.getApplicationContext(), (String) msg.obj);
                            if (((ShowWebActivity) t).isLiked) {
                                ((ShowWebActivity) t).detailPraiseIv.setImageDrawable(t.getResources().getDrawable(R.drawable.detail_praised_icon));
                            } else {
                                ((ShowWebActivity) t).detailPraiseIv.setImageDrawable(t.getResources().getDrawable(R.drawable.detail_unpraise_icon));
                            }
                            break;
                        case COLLECT_WHAT:
                            ToastUtils.showOneToast(t.getApplicationContext(), (String) msg.obj);
                            if (((ShowWebActivity) t).isCollected) {
                                ((ShowWebActivity) t).detailCollectIv.setImageDrawable(t.getResources().getDrawable(R.drawable.detail_collected_icon));
                            } else {
                                ((ShowWebActivity) t).detailCollectIv.setImageDrawable(t.getResources().getDrawable(R.drawable.detail_uncollect_icon));
                            }
                            break;
                        default:
                            break;
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private MyHandler handler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_web);
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


        if (null != getIntent()) {
            showWebBean = (ShowWebBean) getIntent().getSerializableExtra("showWebBean");
        }

        if (null != showWebBean) {
            setTitle();
        }
        setStatusBar();

        initView();

        registerToWX();
        initQQShare();
    }

    @Override
    protected void onStop() {
        super.onStop();
        webview.reload();
    }

    private void initView() {
        if (null == showWebBean) {
            return;
        }
        if (showWebBean.getIsCollect() == 1) {
            isCollected = true;
            detailCollectIv.setImageDrawable(getResources().getDrawable(R.drawable.detail_collected_icon));
        } else {
            isCollected = false;
            detailCollectIv.setImageDrawable(getResources().getDrawable(R.drawable.detail_uncollect_icon));
        }
        if (showWebBean.getIsLike() == 1) {
            isLiked = true;
            detailPraiseIv.setImageDrawable(getResources().getDrawable(R.drawable.detail_praised_icon));
        } else {
            isLiked = false;
            detailPraiseIv.setImageDrawable(getResources().getDrawable(R.drawable.detail_unpraise_icon));
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
            ((RelativeLayout) webview.getParent()).removeView(webview);//???webview ?????????parent????????????
            webview.stopLoading();//????????????
            webSettings.setJavaScriptEnabled(false);//?????????JavaScript??????????????????????????????????????????????????????????????????
            webview.clearHistory();//??????????????????
            webview.removeAllViews();//??????????????????
            webview.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private WebSettings webSettings;

    private void setWebView() {
        webSettings = webview.getSettings();

        // ??????WebView?????????????????????Javascript??????
        webSettings.setJavaScriptEnabled(true);
        // ????????????????????????
        webSettings.setAllowFileAccess(true);
        // ??????????????????
        webSettings.setBuiltInZoomControls(true);


        //????????????
//        WebSettings webSettings = webview.getSettings();
//        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); // ?????????
        webSettings.setAllowFileAccess(true); // ??????????????????
//        webSettings.setSupportZoom(true); // ????????????
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // ?????????????????????


        // ??????Web??????
        webview.setWebViewClient(new webViewClient());

        //????????????
        webview.setWebChromeClient(new WebChromeClient() {

            /*** ??????????????????????????? **/
            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(ShowWebActivity.this);
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
                // ??????Javascript????????????
                CrashReport.setJavascriptMonitor(webview, true);
                super.onProgressChanged(view, newProgress);
            }
        });

        // ??????Web??????
        webview.loadUrl(showWebBean.getUrl());
    }

    /**
     * ----------------------------------------?????????????????? --begin---------------------------
     **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        ShowWebActivity.this.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(ShowWebActivity.this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * ??????????????????
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
        webview.setVisibility(View.VISIBLE);
    }

    /**
     * ??????????????????
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
                /** ????????? ???????????? ?????????:??????????????????-????????????-???????????? */
                if (customView != null) {
                    hideCustomView();
                } else if (webview.canGoBack()) {
                    webview.goBack();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    /**
     * ----------------------------------------?????????????????? --begin---------------------------
     **/

    // Web??????
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (ShowWebActivity.this == null) {
                return false;
            }
            //??????????????????
            if (url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:") || url.startsWith("smsto:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            return false;
        }
    }


    /**
     * ????????????
     */
    private void setTitle() {
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
                    ShowWebActivity.this.finish();
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

    //?????????????????????????????????
    public void setLayoutPadding(Activity activity, DrawerLayout drawerLayout) {
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        contentLayout.getChildAt(1)
                .setPadding(contentLayout.getPaddingLeft(), statusHeight + contentLayout.getPaddingTop(),
                        contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
    }

    /**
     * ??????
     * token 	??? 	string 	token???
     * post_id 	??? 	int 	??????id
     * type 	??? 	int 	1 ?????? 2 ????????????
     *
     * @param tempIsLiked ???????????????
     */
    private void praise(final boolean tempIsLiked) {
        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
        final String url = UrlManager.URL_HOME_POSTLIKE;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("post_id", showWebBean.getId() + "")
                .add("type", tempIsLiked ? "2" : "1")//type 	??? 	int 	1 ?????? 2 ????????????
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isLiked = !isLiked;
                                if (isLiked) {
                                    Message msg = new Message();
                                    msg.what = LIKE_WHAT;
                                    msg.obj = "????????????";
                                    handler.sendMessage(msg);
                                } else {
                                    Message msg = new Message();
                                    msg.what = LIKE_WHAT;
                                    msg.obj = "?????????????????????";
                                    handler.sendMessage(msg);
                                }
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("??????" + url + " ???????????????????????????");
                }
            }
        });
    }

    /**
     * ??????
     * token 	??? 	string 	token???
     * post_id 	??? 	int 	??????id
     * type 	??? 	int 	1 ?????? 2 ????????????
     *
     * @param tempIsCollected
     */
    private void collect(boolean tempIsCollected) {
        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
        final String url = UrlManager.URL_HOME_COLLECT;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("post_id", showWebBean.getId() + "")
                .add("type", tempIsCollected ? "2" : "1")//type 	??? 	int 	1 ?????? 2 ????????????
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    Log.i(Constants.TAG, str);

                    ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
                    if (resInfo.getCode() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isCollected = !isCollected;
                                if (isCollected) {
                                    Message msg = new Message();
                                    msg.what = COLLECT_WHAT;
                                    msg.obj = "????????????";
                                    handler.sendMessage(msg);
                                } else {
                                    Message msg = new Message();
                                    msg.what = COLLECT_WHAT;
                                    msg.obj = "?????????????????????";
                                    handler.sendMessage(msg);
                                }
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = ERROR_WHAT;
                        msg.obj = resInfo.getError_message();
                        myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().handlerError("??????" + url + " ???????????????????????????");
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuc(@NonNull EventLoginSuc eventLoginSuc) {
        if (StringUtil.isEmpty(eventLoginSuc.getToActivity())) {
            return;
        }
        if (Constants.CLICK_COLLECT.equals(eventLoginSuc.getToActivity())) {
            clickCollect();
        } else if (Constants.CLICK_PRAISE.equals(eventLoginSuc.getToActivity())) {
            clickPraise();
        }
    }

    private void clickCollect() {
        if (null != showWebBean) {
            collect(isCollected);
        }
    }

    private void clickPraise() {
        if (null != showWebBean) {
            praise(isLiked);//type 	??? 	int 	1 ?????? 2 ????????????
        }
    }

    private void incHints() {
        //?????????????????????token????????????????????????
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

        //??????okhttp3?????????????????????
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
                    CrashHandler.getInstance().handlerError("??????" + url + " ???????????????????????????");
                }
            }
        });
    }

    /*-------------------------------------------------??????---------------------------------------------------------*/
    //IWXAPI????????????app??????????????????openapi??????
    private IWXAPI iwxapi;

    private void registerToWX() {
        //??????WXAPIFactory???????????????IWXAPI?????????
        iwxapi = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, true);
        //????????????appId???????????????
        iwxapi.registerApp(Constants.WX_APP_ID);
    }

    private Tencent mTencent;

    private void initQQShare() {
        StatConfig.setDebugEnable(true);
        // Tencent??????SDK???????????????????????????????????????Tencent????????????????????????OpenAPI???
        // ??????APP_ID??????????????????????????????appid????????????String???
        mTencent = Tencent.createInstance(Constants.QQ_APP_ID, this.getApplicationContext());
        // 1.4??????:???????????????????????????????????????????????????context????????????activity???getApplicationContext????????????
        // ???????????????
//        initViews();
    }

    private PopupWindow popupWindow;

    private void sharePopupWindow() {
        if (null != popupWindow) {
            //??????popupwindow??????????????????????????????
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                //????????????????????????
//                setBackgroundAlpha(0.3f);
//                popupWindow.showAtLocation(this.findViewById(R.id.relativelayout), Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);

                showPopUp(findViewById(R.id.relativelayout));
            }
        } else {
            initPopupWindow();
        }
    }

    private void initPopupWindow() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupWindow_view = inflater.inflate(R.layout.web_detail_share, null, false);
//        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, 180);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow_view.setFocusable(true);
        popupWindow_view.setFocusableInTouchMode(true);

        ImageView wxShareIv = (ImageView) popupWindow_view.findViewById(R.id.web_detail_share_wx_iv);
        ImageView wxpyqShareIv = (ImageView) popupWindow_view.findViewById(R.id.web_detail_share_wxpyq_iv);
        ImageView qqShareIv = (ImageView) popupWindow_view.findViewById(R.id.web_detail_share_qq_iv);

        wxShareIv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                wechatShare(true);
                if (null != popupWindow && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        wxpyqShareIv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                wechatShare(false);
                if (null != popupWindow && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        qqShareIv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                qqShare();
                if (null != popupWindow && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        popupWindow_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    return true;
                }
                return false;
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
                popupWindow = null;
            }
        });


//        setBackgroundAlpha(0.3f);
//        popupWindow.showAtLocation(this.findViewById(R.id.web_rootlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        showPopUp(findViewById(R.id.relativelayout));
    }

    private void showPopUp(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);

        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight());
    }

    // ???????????????
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        this.getWindow().setAttributes(layoutParams);
    }

    private static final int THUMB_SIZE = 150;


    private static DisplayImageOptions options; // ??????????????????????????????

    private void wechatShare(final boolean isWx) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bm = null;
                try {
                    URL iconUrl = new URL(showWebBean.getShareImgUrl());
                    URLConnection conn = iconUrl.openConnection();
                    HttpURLConnection http = (HttpURLConnection) conn;

                    int length = http.getContentLength();

                    conn.connect();
                    // ????????????????????????
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is, length);
                    bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();// ?????????
                } catch (Exception e) {
                    e.printStackTrace();
                }

                wechatShare(bm, isWx);
            }
        }).start();
    }

    private void wechatShare(Bitmap bmp, boolean isWx) {
        //???????????????WXWebpageObject???????????????url
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = StringUtil.isNotEmpty(showWebBean.getShareUrl()) ? showWebBean.getShareUrl() : showWebBean.getUrl();

        //???WXWebpageObject?????????????????????WXMediaMessage??????????????????????????????
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = StringUtil.isNotEmpty(showWebBean.getShareTitle()) ? showWebBean.getShareTitle() : showWebBean.getTitle();
        if (StringUtil.isNotEmpty(showWebBean.getShareDes())) {
            msg.description = showWebBean.getShareDes();
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;  //???????????????????????????????????????????????????????????????????????? //????????????????????????????????????????????????????????????????????????????????????????????????????????????

        /*if (bmp == null){*/
        bmp = ImageUtils.getInstance().readBitmap(getApplicationContext(), R.drawable.login_logo);
        /*}*/
        if (null != bmp) {
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
            bmp.recycle();
            msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        }

        //????????????Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");//transaction????????????????????????????????????
        req.message = msg;
        req.scene = isWx ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;

        //??????api???????????????????????????
        iwxapi.sendReq(req);
    }

    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void qqShare() {
        if (showWebBean == null) {
            return;
        }
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, StringUtil.isNotEmpty(showWebBean.getShareTitle()) ? showWebBean.getShareTitle() : showWebBean.getTitle());
        if (StringUtil.isNotEmpty(showWebBean.getShareDes())) {
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, showWebBean.getShareDes());
        }
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, StringUtil.isNotEmpty(showWebBean.getShareUrl()) ? showWebBean.getShareUrl() : showWebBean.getUrl());
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, showWebBean.getShareImgUrl());
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "?????????");
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "??????????????????");
        mTencent.shareToQQ(ShowWebActivity.this, params, new BaseUiListener());
    }
    /*-------------------------------------------------??????---------------------------------------------------------*/


    private boolean isLiked = false;//???????????????
    private boolean isCollected = false;//???????????????

    @OnClick({R.id.detail_share_iv, R.id.detail_collect_iv, R.id.detail_praise_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.detail_share_iv:
                sharePopupWindow();
                break;
            case R.id.detail_collect_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(ShowWebActivity.this, Constants.CLICK_COLLECT);
                } else {
                    clickCollect();
                }
                break;
            case R.id.detail_praise_iv:
                if (!SystemUtil.getInstance().getIsLogin()) {
                    SystemUtil.getInstance().intentToLoginActivity(ShowWebActivity.this, Constants.CLICK_PRAISE);
                } else {
                    clickPraise();
                }
                break;
        }
    }
}
