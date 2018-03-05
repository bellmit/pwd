package com.tastebychance.sfj.view.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by shenbinghong on 2018/1/17.
 */

public class PullableWebView extends WebView implements Pullable {
    public PullableWebView(Context context) {
        super(context);
    }

    public PullableWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        if (getScrollY() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPullUp() {
        if (getScrollY() >= getContentHeight() * getScale() - getMeasuredHeight()) {
            return true;
        } else {
            return false;
        }
    }
}
