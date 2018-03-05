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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rongshanghui.tastebychance.com.rongshanghui.MyBaseFragment;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventToSysNews;
import rongshanghui.tastebychance.com.rongshanghui.news.caremsg.CareMsgFragment;
import rongshanghui.tastebychance.com.rongshanghui.news.caremsg.bean.CareMsgBean;
import rongshanghui.tastebychance.com.rongshanghui.news.systempush.SystemPushFragment;
import rongshanghui.tastebychance.com.rongshanghui.news.systempush.bean.SystemMsgBean;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.EventBusUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;

/**
 * 项目名称：RongShangHui
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/12/21 15:31
 * 修改人：Administrator
 * 修改时间：2017/12/21 15:31
 * 修改备注：
 */

public class NewsFragment extends MyBaseFragment {


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
    @BindView(R.id.news_guanzhu_tv)
    TextView newsGuanzhuTv;
    @BindView(R.id.news_guanzhu_iv)
    ImageView newsGuanzhuIv;
    @BindView(R.id.news_guanzhu_rl)
    RelativeLayout newsGuanzhuRl;
    @BindView(R.id.news_xitong_tv)
    TextView newsXitongTv;
    @BindView(R.id.news_xitong_iv)
    ImageView newsXitongIv;
    @BindView(R.id.news_xitong_rl)
    RelativeLayout newsXitongRl;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.content_news)
    RelativeLayout contentNews;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.news_rootlayout)
    CoordinatorLayout newsRootlayout;
    @BindView(R.id.unread)
    TextView unread;
    private View rootView;
    private NewsFragment fragment;

    private static final int PAGER_NUM = 2;

    private static final String EXTRA_CONTENT = "content";
    private Unbinder unbinder;

    public static NewsFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        NewsFragment homeFragment = new NewsFragment();
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

        setTitle();
        initTab();

        //ViewPager的adapter
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getChildFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(PAGER_NUM);

        return contentView;
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
//            args.putString("type", TITLE[position]);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
//            return TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() {
            return PAGER_NUM;
        }
    }

    private void initTab() {
        newsGuanzhuIv.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        newsGuanzhuTv.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        newsXitongIv.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        newsXitongTv.setTextColor(getActivity().getResources().getColor(R.color.textgray));
    }

    private void resetTab() {
        newsGuanzhuIv.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        newsGuanzhuTv.setTextColor(getActivity().getResources().getColor(R.color.textgray));
        newsXitongIv.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        newsXitongTv.setTextColor(getActivity().getResources().getColor(R.color.textgray));
    }

    private void tabGuanzhuClick() {
        newsGuanzhuIv.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        newsGuanzhuTv.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
    }

    private void tabXitongClick() {
        newsXitongIv.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        newsXitongTv.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
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
    }

    //TODO:
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void intentToSysNews(@NonNull EventToSysNews eventToSysNews) {
        viewpager.setCurrentItem(1);
    }


    @Override
    public void onStart() {
        EventBusUtils.register(this);
        super.onStart();
    }

    @Override
    public void onResume() {
        if (SystemUtil.getInstance().getIntFromSP(Constants.KEY_SYSNEWSUNREADNUM) > 0) {
            unread.setVisibility(View.VISIBLE);
        } else {
            unread.setVisibility(View.INVISIBLE);
        }
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.news_guanzhu_rl, R.id.news_xitong_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.news_guanzhu_rl:
                resetTab();
                tabGuanzhuClick();
                viewpager.setCurrentItem(0);
                break;
            case R.id.news_xitong_rl:
                resetTab();
                tabXitongClick();
                viewpager.setCurrentItem(1);
                break;
        }
    }
}
