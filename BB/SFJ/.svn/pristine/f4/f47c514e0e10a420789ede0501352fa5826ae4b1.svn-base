package com.tastebychance.sfj.view.pullableview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shenbinghong on 2018/1/17.
 */

public class PullableTextView extends android.support.v7.widget.AppCompatTextView implements Pullable {
    public PullableTextView(Context context) {
        super(context);
    }

    public PullableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        return true;
    }

    @Override
    public boolean canPullUp() {
        return true;
    }
}
