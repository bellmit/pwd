package com.tastebychance.sfj.view.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by shenbinghong on 2018/1/17.
 */

public class PullableGridView extends GridView implements Pullable {
    public PullableGridView(Context context) {
        super(context);
    }

    public PullableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        if (getCount() == 0) {//没有item的时候也可以下拉刷新
            return true;
        } else if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() >= 0) {//滑到顶部了
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPullUp() {
        if (getCount() == 0) {//没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {//滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null &&
                    getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight()) {
                return true;
            }
        }
        return false;
    }
}
