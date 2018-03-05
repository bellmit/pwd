package com.tastebychance.sfj.apply;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tastebychance.sfj.MyBaseFragment;
import com.tastebychance.sfj.R;
import com.tastebychance.sfj.apply.myapply.ApplyActivity;
import com.tastebychance.sfj.apply.myapply.MyApplyFragment;
import com.tastebychance.sfj.apply.mywaitdealwith.MyJobFragment;
import com.tastebychance.sfj.common.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 项目名称：RongShangHui
 * 类描述：申请审核
 * 创建人：Administrator
 * 创建时间： 2017/12/21 15:31
 * 修改人：Administrator
 * 修改时间：2017/12/21 15:31
 * 修改备注：
 */

public class ApplyFragment extends MyBaseFragment {
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
    @BindView(R.id.fragment_myapply_name_tv)
    TextView fragmentMyapplyNameTv;
    @BindView(R.id.fragment_myapply_underline_iv)
    ImageView fragmentMyapplyUnderlineIv;
    @BindView(R.id.fragment_myapply_rl)
    RelativeLayout fragmentMyapplyRl;
    @BindView(R.id.fragment_myneedtobedealwith_name_tv)
    TextView fragmentMyneedtobedealwithNameTv;
    @BindView(R.id.fragment_myneedtobedealwith_underline_iv)
    ImageView fragmentMyneedtobedealwithUnderlineIv;
    @BindView(R.id.unread)
    TextView unread;
    @BindView(R.id.fragment_myneedtobedealwith_rl)
    RelativeLayout fragmentMyneedtobedealwithRl;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;
    private View rootView;

    private static final int PAGER_NUM = 2;

    public static ApplyFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString(Constants.EXTRA_CONTENT, content);
        ApplyFragment homeFragment = new ApplyFragment();
        homeFragment.setArguments(arguments);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.StyledIndicators);
        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = localInflater.inflate(R.layout.fragment_apply, null);
            unbinder = ButterKnife.bind(this, rootView);
            setTitle();
            resetTab(fragmentMyapplyUnderlineIv, fragmentMyapplyNameTv);
            resetTab(fragmentMyneedtobedealwithUnderlineIv, fragmentMyneedtobedealwithNameTv);
            tabClick(fragmentMyapplyUnderlineIv, fragmentMyapplyNameTv);
            //ViewPager的adapter
            FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getChildFragmentManager());
            viewpager.setAdapter(adapter);
            viewpager.setOffscreenPageLimit(PAGER_NUM);
            viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    resetTab(fragmentMyapplyUnderlineIv, fragmentMyapplyNameTv);
                    resetTab(fragmentMyneedtobedealwithUnderlineIv, fragmentMyneedtobedealwithNameTv);
                    if (position == 0){
                        tabClick(fragmentMyapplyUnderlineIv, fragmentMyapplyNameTv);
                    }else if (position == 1){
                        tabClick(fragmentMyneedtobedealwithUnderlineIv, fragmentMyneedtobedealwithNameTv);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }


        return rootView;
    }

    private static final String[] TITLE = new String[]{Constants.APPLY_MYAPPLY, Constants.APPLY_MYJOB};

    /**
     * ViewPager适配器
     *
     * @author len
     */
    private class TabPageIndicatorAdapter extends FragmentPagerAdapter {
        private TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            //新建一个Fragment来展示ViewPager item的内容，并传递参数
            Fragment fragment = new MyJobFragment();
            if (position == 0){
                fragment = new MyApplyFragment();
            } else if (position == 1){
                fragment = new MyJobFragment();
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
            return PAGER_NUM;
        }
    }

    private void resetTab(@NonNull ImageView imageView, @NonNull TextView textView) {
        imageView.setBackgroundColor(getActivity().getResources().getColor(R.color.bg_gray));
        textView.setTextColor(getActivity().getResources().getColor(R.color.textgray));
    }

    private void tabClick(@NonNull ImageView imageView, @NonNull TextView textView) {
        imageView.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
    }

    private void setTitle() {
        if (headLeftIv != null) {
            headLeftIv.setVisibility(View.INVISIBLE);
        }
        if (headCenterTitleTv != null) {
            headCenterTitleTv.setVisibility(View.VISIBLE);
            headCenterTitleTv.setText("申请审核");
        }
        if (null != headRightIv) {
            headRightIv.setVisibility(View.VISIBLE);
            headRightIv.setImageResource(R.drawable.newlyin);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.head_right_iv, R.id.fragment_myapply_rl, R.id.fragment_myneedtobedealwith_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_right_iv:
                getActivity().startActivity(new Intent(getActivity(), ApplyActivity.class));
                break;
            case R.id.fragment_myapply_rl:
                resetTab(fragmentMyapplyUnderlineIv, fragmentMyapplyNameTv);
                resetTab(fragmentMyneedtobedealwithUnderlineIv, fragmentMyneedtobedealwithNameTv);
                tabClick(fragmentMyapplyUnderlineIv, fragmentMyapplyNameTv);
                viewpager.setCurrentItem(0);
                break;
            case R.id.fragment_myneedtobedealwith_rl:
                resetTab(fragmentMyapplyUnderlineIv, fragmentMyapplyNameTv);
                resetTab(fragmentMyneedtobedealwithUnderlineIv, fragmentMyneedtobedealwithNameTv);
                tabClick(fragmentMyneedtobedealwithUnderlineIv, fragmentMyneedtobedealwithNameTv);
                viewpager.setCurrentItem(1);
                break;
            default:break;
        }
    }
}
