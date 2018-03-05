package com.newland.wstdd.mine.managerpage;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.adapter.BaseFragmentPagerAdapter;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.base.BaseFragment;
import com.newland.wstdd.common.tools.UiHelper;

/**
 * 我的活动--三个列表
 * 
 * @author Administrator
 * 
 */
public class MyActivitiesActivity extends FragmentActivity {

	private LinearLayout mHor_lin;
	private LinearLayout mLl_belate;
	private TextView mTv_belate;
	private ImageView mIv_belate;
	private LinearLayout mLl_leaveearly;
	private TextView mTv_leaveearly;
	private ImageView mIv_leaveearly;
	private LinearLayout mLl_absenteeism;
	private TextView mTv_absenteeism;
	private ImageView mIv_absenteeism;
	private android.support.v4.view.ViewPager mMViewPager;

	private MyActivitiesOriginateListFragment activitiesOriginateListFragment;
	private MyActivitiesOriginateListFragment activitiesOriginateListFragment1;
	private MyActivitiesOriginateListFragment activitiesOriginateListFragment2;
	private BaseFragment currentFragment;// 当前选中的Fragment
	private ViewPager mViewPager;
	private List<BaseFragment> listFragments;
	private int mScreenWidth = 0;// 屏幕宽度

	private String month;// 月份

	private TextView right_tv;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		month = getIntent().getStringExtra("month");
		setContentView(R.layout.activity_myactivities);
		setTitle();
		initView();
		initData();
		initFragment();
	}

	/**
	 * 设置标题栏
	 */
	private void setTitle() {
		ImageView left_btn = (ImageButton) findViewById(R.id.head_left_iv);
		ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		right_tv = (TextView) findViewById(R.id.head_right_tv);
		TextView center_tv = (TextView) findViewById(R.id.head_center_title);
		if (center_tv != null)
			center_tv.setText("我的活动");
		if (left_btn != null) {// 返回
			left_btn.setVisibility(View.VISIBLE);
			left_btn.setImageDrawable(getResources().getDrawable(R.drawable.left_expandsion));
			left_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					MyActivitiesActivity.this.finish();
				}
			});
		}
		if (right_btn != null) {
			right_btn.setVisibility(View.VISIBLE);
			right_btn.setImageDrawable(getResources().getDrawable(R.drawable.test_myactivities_more));
			// 更多
			right_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					UiHelper.ShowOneToast(MyActivitiesActivity.this, "更多");
				}
			});
		}
		if (right_tv != null) {// 日期
			right_tv.setVisibility(View.GONE);
		}
	}

	/**
	 * 初始化viewpager颜色
	 */
	private void initData() {
		mIv_belate.setVisibility(View.VISIBLE);
		mIv_leaveearly.setVisibility(View.INVISIBLE);
		mIv_absenteeism.setVisibility(View.INVISIBLE);

		mTv_belate.setTextColor(this.getResources().getColor(R.color.originate_darkgreen));
		mTv_leaveearly.setTextColor(this.getResources().getColor(R.color.black));
		mTv_absenteeism.setTextColor(this.getResources().getColor(R.color.black));
	}

	/**
	 * 初始化Fragment
	 */
	private void initFragment() {
		listFragments = new ArrayList<BaseFragment>();
		activitiesOriginateListFragment = MyActivitiesOriginateListFragment.newInstance(MyActivitiesActivity.this);
		activitiesOriginateListFragment1 = MyActivitiesOriginateListFragment.newInstance(MyActivitiesActivity.this);
		activitiesOriginateListFragment2 = MyActivitiesOriginateListFragment.newInstance(MyActivitiesActivity.this);

		// 把月份传给Fragment
		// Bundle bundle = new Bundle();
		// bundle.putString("month", month);
		// attendanceStatisticsBeLateFragment.setArguments(bundle);
		// attendanceStatisticsLeaveEarlyFragment.setArguments(bundle);
		// attendanceStatisticsAbsenteeismFragment.setArguments(bundle);

		listFragments.add(activitiesOriginateListFragment);
		listFragments.add(activitiesOriginateListFragment1);
		listFragments.add(activitiesOriginateListFragment2);

		BaseFragmentPagerAdapter mAdapetr = new BaseFragmentPagerAdapter(getSupportFragmentManager(), listFragments);
		mViewPager.setAdapter(mAdapetr);
		mViewPager.setOnPageChangeListener(pageListener);
		mViewPager.setOffscreenPageLimit(3);
		currentFragment = activitiesOriginateListFragment;
	}

	/**
	 * ViewPager切换监听方法
	 * */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			clearPress();
			mViewPager.setCurrentItem(position);
			currentFragment = listFragments.get(position);
			switch (position) {
			case 0:
				mIv_belate.setVisibility(View.VISIBLE);
				mTv_belate.setTextColor(MyActivitiesActivity.this.getResources().getColor(R.color.originate_darkgreen));
				break;
			case 1:
				mIv_leaveearly.setVisibility(View.VISIBLE);
				mTv_leaveearly.setTextColor(MyActivitiesActivity.this.getResources().getColor(R.color.originate_darkgreen));
				break;
			case 2:
				mIv_absenteeism.setVisibility(View.VISIBLE);
				mTv_absenteeism.setTextColor(MyActivitiesActivity.this.getResources().getColor(R.color.originate_darkgreen));
				break;
			default:
				break;
			}
			selectTab(position);
		}
	};

	private void clearPress() {

		mIv_belate.setVisibility(View.INVISIBLE);
		mIv_leaveearly.setVisibility(View.INVISIBLE);
		mIv_absenteeism.setVisibility(View.INVISIBLE);

		mTv_belate.setTextColor(this.getResources().getColor(R.color.black));
		mTv_leaveearly.setTextColor(this.getResources().getColor(R.color.black));
		mTv_absenteeism.setTextColor(this.getResources().getColor(R.color.black));
	}

	private void selectTab(int tab_postion) {
		for (int i = 0; i < mHor_lin.getChildCount(); i++) {
			View checkView = mHor_lin.getChildAt(tab_postion);
			int k = checkView.getMeasuredWidth();
			int l = checkView.getLeft();
			int i2 = l + k / 2 - mScreenWidth / 2;
			// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
			// mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
			// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
			// mItemWidth , 0);
		}
	}

	private void initView() {
		mHor_lin = (LinearLayout) findViewById(R.id.hor_lin);
		// 迟到
		mLl_belate = (LinearLayout) findViewById(R.id.ll_belate);
		mTv_belate = (TextView) findViewById(R.id.tv_belate);
		mIv_belate = (ImageView) findViewById(R.id.iv_belate);
		// 早退
		mLl_leaveearly = (LinearLayout) findViewById(R.id.ll_leaveearly);
		mTv_leaveearly = (TextView) findViewById(R.id.tv_leaveearly);
		mIv_leaveearly = (ImageView) findViewById(R.id.iv_leaveearly);
		// 旷工
		mLl_absenteeism = (LinearLayout) findViewById(R.id.ll_absenteeism);
		mTv_absenteeism = (TextView) findViewById(R.id.tv_absenteeism);
		mIv_absenteeism = (ImageView) findViewById(R.id.iv_absenteeism);
		mViewPager = (android.support.v4.view.ViewPager) findViewById(R.id.mViewPager);
	}

	public void onClick(View view) {
		clearPress();
		if (view.getId() == R.id.ll_belate) // 迟到
		{
			mIv_belate.setVisibility(View.VISIBLE);
			mViewPager.setCurrentItem(0);
		} else if (view.getId() == R.id.ll_leaveearly) // 早退
		{
			mIv_leaveearly.setVisibility(View.VISIBLE);
			mViewPager.setCurrentItem(1);
		} else if (view.getId() == R.id.ll_absenteeism) // 旷工
		{
			mIv_absenteeism.setVisibility(View.VISIBLE);
			mViewPager.setCurrentItem(2);
		}

		else if (view.getId() == R.id.head_left_iv) // 退出
		{
			finish();
		}

	}
}
