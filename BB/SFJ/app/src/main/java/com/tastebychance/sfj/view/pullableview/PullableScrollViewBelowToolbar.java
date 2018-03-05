package com.tastebychance.sfj.view.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by shenbinghong on 2018/1/17.
 */

public class PullableScrollViewBelowToolbar extends ScrollView implements Pullable {
    public PullableScrollViewBelowToolbar(Context context) {
        super(context);
    }

    public PullableScrollViewBelowToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableScrollViewBelowToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        if (getScaleY() == 1.0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPullUp() {
        if (getScaleY() >= (getChildAt(0).getHeight() - getMeasuredHeight())) {
            return true;
        } else {
            return false;
        }
    }
}
