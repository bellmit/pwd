package com.newland.wstdd.common.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ViewPager类   这个类的主要作用：
 * 设置是否可以滑动的功能
 */
public class HomeViewPager extends ViewPager {
	private boolean isSlipping = true;//默认方式是可以滑动的

	public HomeViewPager(Context context) {
		super(context);
	}

	public HomeViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (!isSlipping) {
			return false;
		}
		return super.onInterceptTouchEvent(arg0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (!isSlipping) {
			return false;
		}
		return super.onTouchEvent(arg0);
	}

	/**
	 *@Title: setSlipping
	 *@Description: TODO
	 *@param isSlipping
	 */
	public void setSlipping(boolean isSlipping) {
		this.isSlipping = isSlipping;
	}
}
