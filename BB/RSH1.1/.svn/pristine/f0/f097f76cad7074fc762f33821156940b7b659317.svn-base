package rongshanghui.tastebychance.com.rongshanghui.news;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseFragment;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventToSysNews;
import rongshanghui.tastebychance.com.rongshanghui.news.caremsg.CareMsgFragment;
import rongshanghui.tastebychance.com.rongshanghui.news.systempush.SystemPushFragment;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import toptabdemo.ab.com.library.TabPageIndicator;

/**
 * 项目名称：RongShangHui
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/21 15:31
 * 修改人：Administrator
 * 修改时间：2017/12/21 15:31
 * 修改备注：
 */

public class News1Fragment extends MyBaseFragment {

    private static final String[] TITLE = new String[]{"关注消息", "系统推送"};
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
    @BindView(R.id.indicator)
    TabPageIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.content_news)
    RelativeLayout contentNews;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.news_rootlayout)
    CoordinatorLayout newsRootlayout;
    Unbinder unbinder;
    private View rootView;
    private News1Fragment fragment;

    private static final String EXTRA_CONTENT = "content";

    public static News1Fragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        News1Fragment homeFragment = new News1Fragment();
        homeFragment.setArguments(arguments);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.StyledIndicators);
        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        View contentView = localInflater.inflate(R.layout.activity_news, null);
        unbinder = ButterKnife.bind(this, contentView);
        rootView = contentView;
        fragment = this;

        //ViewPager的adapter
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getChildFragmentManager());
//        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(TITLE.length);

//        tabPageIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewpager);//绑定

        //设置指示器
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*Toast.makeText(getApplicationContext(), TITLE[position], Toast.LENGTH_LONG).show();
                if (position == 0 && null != right_tv) {
                    right_tv.setVisibility(View.VISIBLE);
                    right_tv.setText("添加关注");
                } else {
                    right_tv.setVisibility(View.GONE);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setTitle();

        return contentView;
    }

    private void setTitle() {
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.INVISIBLE);
        }
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("消息");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.GONE);
        }
        /*if (null != headRightBtn){
            headRightBtn.setVisibility(View.VISIBLE);
            headRightBtn.setIma
        }*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * ViewPager适配器
     *
     * @author len
     */
    class TabPageIndicatorAdapter extends FragmentPagerAdapter {
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            //新建一个Fragment来展示ViewPager item的内容，并传递参数
            Fragment fragment = new CareMsgFragment();
            if (position == 0) {
                fragment = new CareMsgFragment();
            } else if (position == 1) {
                fragment = new SystemPushFragment();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void intentToSysNews(@NonNull EventToSysNews eventToSysNews) {
        indicator.setCurrentItem(1);
    }

    @OnClick(R.id.head_right_iv)
    public void onViewClicked() {
    }

    @Override
    public void onStart() {
        EventBusUtils.register(this);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
    }
}
