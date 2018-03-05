package rongshanghui.tastebychance.com.rongshanghui.news.systempush;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.web.bean.ShowWebBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;

/**
 * 类描述：ShowWebSysNewsActivity 单纯展示系统推送消息-web内容 （不可分享、点赞、收藏）
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/12/5 10:13
 * 修改人：
 * 修改时间：2017/12/5 10:13
 * 修改备注：
 *
 * @version 1.0
 */
public class ShowWebSysNewsActivity extends MyBaseActivity {

    @BindView(R.id.webview)
    WebView webView;

    private ShowWebBean showWebBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sysnews_web);
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
        }
        setStatusBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != showWebBean) {
            setWebView();
        }

        System.out.println("Find----------------------------------------------------" + getTaskId());

        read();

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

    /**
     * 阅读了系统消息
     * token 	是 	string 	token值
     * notify_id 	是 	int 	消息id号
     */
    private void read() {
       /* if(null == loadingBar){
            loadingBar = LoadingBar.make(findViewById(R.id.xx),new CustomLoadingFactory());
        }
        if(null != loadingBar){
            loadingBar.show();
            loadingBar.setOnClickListener(null);
        }*/

//取到已经保存的token（登录后的信息）
        String token = SystemUtil.getInstance().getToken();
        Log.i(Constants.TAG, token);

//采用okhttp3来进行网络请求
        final String url = UrlManager.URL_NEWS_VIEWMSG;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("token", token)
                .add("notify_id", showWebBean.getId() + "").build();
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
                                //成功查看该片文章

                                SystemUtil.getInstance().hasNewNotify();
                                //TODO：临时
//                                SystemUtil.getInstance().saveIntToSP(Constants.KEY_SYSNEWSUNREADNUM, 0);
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
                    CrashHandler.getInstance().handlerError("处理" + url + " 返回的成功数据报错");
                }
            }
        });
    }

    private WebSettings webSettings;

    private void setWebView() {
        webSettings = webView.getSettings();

        // 设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        webSettings.setBuiltInZoomControls(true);

        webView.loadUrl(showWebBean.getUrl());
        // 设置Web视图
        webView.setWebViewClient(new webViewClient());
    }

    // Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (ShowWebSysNewsActivity.this == null) {
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
        /*View view = (View) findViewById(R.id.mine_myfollow_underline);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.height = statusHeight;
        view.setLayoutParams(lp);*/

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
                    ShowWebSysNewsActivity.this.finish();
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
