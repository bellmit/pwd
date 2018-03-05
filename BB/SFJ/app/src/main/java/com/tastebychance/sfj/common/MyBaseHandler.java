package com.tastebychance.sfj.common;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.tastebychance.sfj.R;

import name.quanke.app.libs.emptylayout.EmptyLayout;

/**
 * Created by shenbinghong on 2018/2/11.
 */

public class MyBaseHandler extends Handler {

    //empty
    public static final int EMPTY_NULL_PIC = R.drawable.nothing;
    public static final String EMPTY_NULL_STR = "";

    public void emptyLayoutShowOrHide(@NonNull EmptyLayout emptyLayout, @NonNull boolean toShow){
        try {
            if (null == emptyLayout){
                return;
            }
            if (toShow){
                emptyLayout.showEmpty(EMPTY_NULL_PIC, EMPTY_NULL_STR);
            } else {
                emptyLayout.hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
