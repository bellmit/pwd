package com.newland.wstdd.common.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;
/**
 * 这里可以设置滑动的效果，要是想去除滑动的效果，完全可以设置切换的时间接近0，这样就看不出切换的效果
 * @author Administrator
 *
 */
@SuppressLint("NewApi")
public class ViewPagerScroller extends Scroller {

	
    private int mScrollDuration = 0;


    public ViewPagerScroller(Context context) {

        super(context);

    }


    public ViewPagerScroller(Context context, Interpolator interpolator) {

        super(context, interpolator);

    }


    public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {

        super(context, interpolator, flywheel);

    }



    @Override

    public void startScroll(int startX, int startY, int dx, int dy, int duration) {

        super.startScroll(startX, startY, dx, dy, mScrollDuration);

    }


    @Override

    public void startScroll(int startX, int startY, int dx, int dy) {

        super.startScroll(startX, startY, dx, dy, mScrollDuration);

    }

}

