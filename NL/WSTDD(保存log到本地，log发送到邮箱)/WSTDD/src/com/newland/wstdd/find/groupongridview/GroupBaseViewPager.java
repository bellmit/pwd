package com.newland.wstdd.find.groupongridview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义高度的viewpapger
 */
public class GroupBaseViewPager extends ViewPager {
	private boolean scrollable = true;

	public GroupBaseViewPager(Context context) {
		super(context);
	}

	public GroupBaseViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置viewpager是否可以滚动
	 * 
	 * @param enable
	 */
	public void setScrollable(boolean enable) {
		scrollable = enable;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (scrollable) {
			return super.onInterceptTouchEvent(event);
		} else {
			return false;
		}
	}
}