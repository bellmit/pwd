package rongshanghui.tastebychance.com.rongshanghui.cardclip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseFragment;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;

/**
 * 项目名称：RongShangHui
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/21 15:48
 * 修改人：Administrator
 * 修改时间：2017/12/21 15:48
 * 修改备注：
 */

public class CardClipFragment extends MyBaseFragment {


    private static final String EXTRA_CONTENT = "content";
    @BindView(R.id.head_left_iv)
    ImageView headLeftIv;
    @BindView(R.id.head_center_title_tv)
    TextView headCenterTitleTv;
    @BindView(R.id.head_right_iv)
    ImageView headRightIv;
    @BindView(R.id.head_right_tv)
    TextView headRightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.content_cardclip)
    RelativeLayout contentCardclip;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.activity_cardclip)
    CoordinatorLayout activityCardclip;
    Unbinder unbinder;

    public static CardClipFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        CardClipFragment homeFragment = new CardClipFragment();
        homeFragment.setArguments(arguments);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.activity_cardclip, null);
        unbinder = ButterKnife.bind(this, contentView);
        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle();
        setWebView();
    }


    private WebSettings webSettings;

    private void setWebView() {
        webSettings = webview.getSettings();

        // 设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        webSettings.setBuiltInZoomControls(true);

        String id = "";
        if (SystemUtil.getInstance().getUserInfo() != null) {
            id = SystemUtil.getInstance().getUserInfo().getId() + "";
        }
        webview.loadUrl(UrlManager.URL_WEB_PERSONALCARD + id);
        // 设置Web视图
        webview.setWebViewClient(new webViewClient());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void setTitle() {
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.INVISIBLE);
        }
        //TODO：动态设置标题
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("名片夹");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.GONE);
            headRightTv.setText("分享名片");
        }
    }

    @OnClick(R.id.head_right_tv)
    public void onViewClicked() {
    }

}
