package com.tastebychance.sfj;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.tastebychance.sfj.apply.ApplyFragment;
import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.filedealwith.FileDealWithFragment;
import com.tastebychance.sfj.home.HomeFragment;
import com.tastebychance.sfj.mine.MineFragment;
import com.tastebychance.sfj.util.AppManager;
import com.tastebychance.sfj.util.PackageManagerUtils;
import com.tastebychance.sfj.util.SystemUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author shenbinghong
 * @time 2018/1/30 11:52
 * @path com.tastebychance.sfj.HostActivity.java
 */
public class HostActivity extends BaseActivity {


    class TabBean {
        private int drawableId;
        private String nameStr;
        private int textColor;

        public int getDrawableId() {
            return drawableId;
        }

        public void setDrawableId(int drawableId) {
            this.drawableId = drawableId;
        }

        public String getNameStr() {
            return nameStr;
        }

        public void setNameStr(String nameStr) {
            this.nameStr = nameStr;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }
    }

    private TabLayout mTabTl;
    private ViewPager mContentVp;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    private ArrayList<TabBean> tabItemBg;

    private Bundle fragmentBundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);

        setContentView(R.layout.activity_host);

        mTabTl =  findViewById(R.id.tl_tab);
        mContentVp =  findViewById(R.id.vp_content);
        mContentVp.setOffscreenPageLimit(4);

        fragmentBundle = new Bundle();

        initTabBg();
        if (tabItemBg.size() > 0) {
            tabItemBg.get(0).setDrawableId(R.drawable.index);
            tabItemBg.get(0).setTextColor(R.color.tab_pressed_color);
        }
        initContent();
        initTab();

        initListener();
    }

    private void initListener() {
        mTabTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tab被选的时候回调

                try {
                    if (null == tabItemBg) {
                        tabItemBg = new ArrayList<>();
                    }
                    tabItemBg.clear();

                    initTabBg();
                    switch (tab.getPosition()) {
                        case 0:
                            tabItemBg.get(0).setDrawableId(R.drawable.index);
                            tabItemBg.get(0).setTextColor(R.color.tab_pressed_color);
                            break;
                        case 1:
                            tabItemBg.get(1).setDrawableId(R.drawable.file);
                            tabItemBg.get(1).setTextColor(R.color.tab_pressed_color);
                            break;
                        case 2:
                            tabItemBg.get(2).setDrawableId(R.drawable.apply);
                            tabItemBg.get(2).setTextColor(R.color.tab_pressed_color);
                            break;
                        case 3:
                            tabItemBg.get(3).setDrawableId(R.drawable.mine);
                            tabItemBg.get(3).setTextColor(R.color.tab_pressed_color);
                            break;
                        default:break;
                    }
                    setTabValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab未被选择的时候回调
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //tab重新选择的时候回调
            }
        });
    }

    private void initTabBg() {
        if (null == tabItemBg) {
            tabItemBg = new ArrayList<>();
        }

        WeakReference<TabBean> wf = new WeakReference<TabBean>(new TabBean());
        wf.get().setDrawableId(R.drawable.index_0);
        wf.get().setNameStr("首页");
        wf.get().setTextColor(R.color.tab_normal_color);
        tabItemBg.add(wf.get());

        WeakReference<TabBean> wf1 = new WeakReference<TabBean>(new TabBean());
        wf1.get().setDrawableId(R.drawable.file_0);
        wf1.get().setNameStr("名片夹");
        wf1.get().setTextColor(R.color.tab_normal_color);
        tabItemBg.add(wf1.get());

        WeakReference<TabBean> wf2 = new WeakReference<TabBean>(new TabBean());
        wf2.get().setDrawableId(R.drawable.apply_0);
        wf2.get().setNameStr("政务大厅");
        wf2.get().setTextColor(R.color.tab_normal_color);
        tabItemBg.add(wf2.get());

        WeakReference<TabBean> wf3 = new WeakReference<TabBean>(new TabBean());
        wf3.get().setDrawableId(R.drawable.mine_0);
        wf3.get().setNameStr("我的");
        wf3.get().setTextColor(R.color.tab_normal_color);
        tabItemBg.add(wf3.get());
    }

    private void initTab() {
        mTabTl.setTabMode(TabLayout.MODE_FIXED);
        mTabTl.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(mContentVp);

        setTabValue();
        mTabTl.getTabAt(0).getCustomView().setSelected(true);
    }

    private void setTabValue() {
        for (int i = 0; i < tabIndicators.size(); i++) {
            if (mTabTl.getTabAt(i) != null) {
                if (null == mTabTl.getTabAt(i).getCustomView()) {
                    mTabTl.getTabAt(i).setCustomView(R.layout.item_tab_home);
                }
                TextView indicator =  mTabTl.getTabAt(i).getCustomView().findViewById(R.id.tab_text);

                Drawable drawable = getResources().getDrawable(tabItemBg.get(i).getDrawableId());
                int drawableWidth = drawable.getIntrinsicWidth() + drawable.getIntrinsicWidth() / 4;
                int drawableHeight = drawable.getIntrinsicHeight() + drawable.getIntrinsicHeight() / 4;
                drawable.setBounds(0, 0, drawableWidth, drawableHeight);
                indicator.setCompoundDrawables(null, drawable, null, null);
                mTabTl.getTabAt(i).setTag(i);
            }
        }

    }

    private HomeFragment homeFragment;
    private FileDealWithFragment fileDealWithFragment;
    private ApplyFragment applyFragment;
    private MineFragment mineFragment;

    private void initContent() {
        tabIndicators = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tabIndicators.add("Tab " + i);
        }
        tabFragments = new ArrayList<>();
        homeFragment = HomeFragment.newInstance(tabIndicators.get(0));
        fileDealWithFragment = FileDealWithFragment.newInstance(tabIndicators.get(1));
        applyFragment = ApplyFragment.newInstance(tabIndicators.get(2));
        mineFragment = MineFragment.newInstance(tabIndicators.get(3));

        resetTabFragments();
    }

    private void resetTabFragments() {
        if (null != tabFragments) {
            tabFragments.clear();
        }
        tabFragments.add(homeFragment);
        tabFragments.add(fileDealWithFragment);
        tabFragments.add(applyFragment);
        tabFragments.add(mineFragment);

        contentAdapter = new ContentPagerAdapter(this, getSupportFragmentManager(), tabFragments);
        mContentVp.setAdapter(contentAdapter);

        if (mTabTl != null) {
            mTabTl.setVisibility(View.VISIBLE);
        }
    }

    private boolean isLoginSuc = false;

    class ContentPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments = null;
        private Context context;

        public ContentPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.context = context;
            this.fragments = fragments;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

    @Override
    protected void onResume() {
        if (isLoginSuc) {
            isLoginSuc = false;
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

        if (null != mTabTl && null != mContentVp && !SystemUtil.getInstance().getBooleanFromSP(Constants.KEY_LOGINSTATE)) {
            mTabTl.setSelectedTabIndicatorHeight(0);
            mTabTl.getTabAt(0).getCustomView().setSelected(true);
            mContentVp.setCurrentItem(0);
        }

        setTabValue();

        super.onResume();
    }
    @Override
    protected void onStart() {
        if (Constants.IS_DEVELOPING) {
            System.out.println(PackageManagerUtils.getRunningActivityName(this) + "----------------------------------------------------" + getTaskId());
        }

        super.onStart();
    }

    @Override
    protected void onDestroy() {
        AppManager.getInstance().finishActivity(this);
        super.onDestroy();
    }

}
