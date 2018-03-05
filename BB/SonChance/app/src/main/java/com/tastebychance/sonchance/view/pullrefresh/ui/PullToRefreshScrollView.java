package com.tastebychance.sonchance.view.pullrefresh.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.tastebychance.sonchance.view.pullrefresh.interfacepackage.ILoadingLayout;
import com.tastebychance.sonchance.view.pullrefresh.layoutpackage.FooterLoadingLayout;
import com.tastebychance.sonchance.view.pullrefresh.layoutpackage.LoadingLayout;
import com.tastebychance.sonchance.view.pullrefresh.layoutpackage.RotateLoadingLayout;

/**
 * 项目名称：SonChance  这个类实现了ScrollView下拉刷新，上加载更多和滑到底部自动加载
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/14 17:38
 * 修改人：Administrator
 * 修改时间：2017/9/14 17:38
 * 修改备注：
 */

public class PullToRefreshScrollView extends PullToRefreshBase<ScrollView>  {

    /**
     * 构造方法
     *
     * @param context context
     */
    public PullToRefreshScrollView(Context context) {
        this(context, null);
    }

    /**
     * 构造方法
     *
     * @param context context
     * @param attrs attrs
     */
    public PullToRefreshScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造方法
     *
     * @param context context
     * @param attrs attrs
     * @param defStyle defStyle
     */
    public PullToRefreshScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * @see com.nj1s.lib.pullrefresh.PullToRefreshBase#createRefreshableView(android.content.Context, android.util.AttributeSet)
     */
    @Override
    protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
        ScrollView scrollView = new ScrollView(context);
        return scrollView;
    }

    /**
     * @see com.nj1s.lib.pullrefresh.PullToRefreshBase#isReadyForPullDown()
     */
    @Override
    protected boolean isReadyForPullDown() {
        return mRefreshableView.getScrollY() == 0;
    }

    /**
     * @see com.nj1s.lib.pullrefresh.PullToRefreshBase#isReadyForPullUp()
     */
    @Override
    protected boolean isReadyForPullUp() {
/*        View scrollViewChild = mRefreshableView.getChildAt(0);
        if (null != scrollViewChild) {
            return mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        return false;*/


        View scrollViewChild = mRefreshableView.getChildAt(0);
        if (null != scrollViewChild) {
            return mRefreshableView.getScrollY() > 0;
        }
        return false;
    }

}
