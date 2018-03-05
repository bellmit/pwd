package rongshanghui.tastebychance.com.rongshanghui.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventSysNewsNum;
import rongshanghui.tastebychance.com.rongshanghui.bean.EventToSysNews;
import rongshanghui.tastebychance.com.rongshanghui.news.caremsg.CareMsgFragment;
import rongshanghui.tastebychance.com.rongshanghui.news.systempush.SystemPushFragment;
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

public class NewsActivity extends FragmentActivity {

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
    @BindView(R.id.unread)
    TextView unread;
    @BindView(R.id.news_xitong_rl)
    RelativeLayout newsXitongRl;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.content_news)
    RelativeLayout contentNews;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.news_rootlayout)
    CoordinatorLayout newsRootlayout;

    private static final int PAGER_NUM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        setTitle();
        initTab();

        //ViewPager的adapter
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(PAGER_NUM);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetTab();
                if (position == 0) {
                    tabGuanzhuClick();
                } else if (position == 1) {
                    tabXitongClick();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        newsGuanzhuIv.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));
        newsGuanzhuTv.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
        newsXitongIv.setBackgroundColor(this.getResources().getColor(R.color.white));
        newsXitongTv.setTextColor(this.getResources().getColor(R.color.textgray));
    }

    private void resetTab() {
        newsGuanzhuIv.setBackgroundColor(this.getResources().getColor(R.color.white));
        newsGuanzhuTv.setTextColor(this.getResources().getColor(R.color.textgray));
        newsXitongIv.setBackgroundColor(this.getResources().getColor(R.color.white));
        newsXitongTv.setTextColor(this.getResources().getColor(R.color.textgray));
    }

    private void tabGuanzhuClick() {
        newsGuanzhuIv.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));
        newsGuanzhuTv.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
    }

    private void tabXitongClick() {
        newsXitongIv.setBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));
        newsXitongTv.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
    }

    private void setTitle() {
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.VISIBLE);
        }
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("消息");
        }
        if (null != headRightTv) {
            headRightTv.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void intentToSysNews(@NonNull EventToSysNews eventToSysNews) {
        viewpager.setCurrentItem(1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateSysUnread(@NonNull EventSysNewsNum eventSysNewsNum) {
        updateSysUnread();
    }

    private void updateSysUnread() {
        if (SystemUtil.getInstance().getIntFromSP(Constants.KEY_SYSNEWSUNREADNUM) > 0) {
            unread.setVisibility(View.VISIBLE);
        } else {
            unread.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStart() {
        EventBusUtils.register(this);
        SystemUtil.getInstance().hasNewNotify();
        super.onStart();
    }

    @Override
    public void onResume() {
        updateSysUnread();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        EventBusUtils.unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.head_left_iv, R.id.news_guanzhu_rl, R.id.news_xitong_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_left_iv:
                this.finish();
                break;
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
