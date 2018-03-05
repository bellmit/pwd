package rongshanghui.tastebychance.com.rongshanghui.home.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.dyhdyh.widget.loading.bar.LoadingBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import rongshanghui.tastebychance.com.rongshanghui.MyBaseFragmentActivity;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.ResInfo;
import rongshanghui.tastebychance.com.rongshanghui.common.AppManager;
import rongshanghui.tastebychance.com.rongshanghui.home.search.bean.PostListBean;
import rongshanghui.tastebychance.com.rongshanghui.home.search.bean.UserListBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.CrashHandler;
import rongshanghui.tastebychance.com.rongshanghui.util.StringUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;
import rongshanghui.tastebychance.com.rongshanghui.util.ToastUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.UrlManager;
import rongshanghui.tastebychance.com.rongshanghui.view.CustomLoadingFactory;
import toptabdemo.ab.com.library.TabPageIndicator;

/**
 * 类描述：HomeSearchActivity 首页搜索
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/11/20 15:38
 * 修改人：
 * 修改时间：2017/11/20 15:38
 * 修改备注：
 *
 * @version 1.0
 *          <p>
 *          需要分两列来进行请求数据
 */
public class HomeSearchActivity extends MyBaseFragmentActivity implements View.OnClickListener {
    public static final String SEARCH_POST_ACTION = "SEARCH_POST_ACTION";
    public static final String SEARCH_USER_ACTION = "SEARCH_USER_ACTION";

    private EditText searchEdt;
    private TextView searchCancelTv;
    private TabPageIndicator indicator;
    private ViewPager viewpager;

    private int currentPosition = 0;
    /**
     * Tab标题
     */
    private static final String[] TITLE = new String[]{"内容", "通讯录"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        AppManager.getAppManager().addActivity(this);

        initView();

        //ViewPager的adapter
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);

        TabPageIndicator tabPageIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        tabPageIndicator.setViewPager(pager);//绑定

        //设置指示器
        tabPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(getApplicationContext(), TITLE[position], Toast.LENGTH_LONG).show();
                currentPosition = position;

                if (canSearch()) {
                    SystemUtil.getInstance().saveStrToSP("keyword", searchEdt.getText().toString());

                    Intent intent = new Intent();
                    if (currentPosition == 0) {
                        intent.setAction(SEARCH_POST_ACTION);
                    } else {
                        intent.setAction(SEARCH_USER_ACTION);
                    }
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        searchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    if (canSearch()) {
                        SystemUtil.getInstance().saveStrToSP("keyword", searchEdt.getText().toString());

                        Intent intent = new Intent();
                        if (currentPosition == 0) {
                            intent.setAction(SEARCH_POST_ACTION);
                        } else {
                            intent.setAction(SEARCH_USER_ACTION);
                        }
                        sendBroadcast(intent);
                    }
                    return true;
                }
                return false;
            }
        });

        searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SystemUtil.getInstance().saveStrToSP("keyword", s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        searchEdt = (EditText) findViewById(R.id.search_edt);
        searchCancelTv = (TextView) findViewById(R.id.search_cancel_tv);
        searchCancelTv.setOnClickListener(this);
        indicator = (TabPageIndicator) findViewById(R.id.indicator);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    private boolean canSearch() {
        if (null != searchEdt && StringUtil.isNotEmpty(searchEdt.getText().toString())) {
            return true;
        }
        return false;
    }

    /**
     * ViewPager适配器
     *
     * @author len
     */
    private ItemPostFragment itemPostFragment;
    private ItemUserFragment itemUserFragment;

    class TabPageIndicatorAdapter extends FragmentPagerAdapter {
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //新建一个Fragment来展示ViewPager item的内容，并传递参数
            Fragment fragment = new ItemPostFragment();
            itemPostFragment = new ItemPostFragment();
            itemUserFragment = new ItemUserFragment();
            if (position == 0) {
                fragment = itemPostFragment;
            } else {
                fragment = itemUserFragment;
            }
            Bundle args = new Bundle();
            args.putString("type", TITLE[position]);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() {
            return TITLE.length;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_cancel_tv:
                HomeSearchActivity.this.finish();
                break;
        }
    }
}