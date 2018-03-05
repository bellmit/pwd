package com.tastebychance.sonchance.view.adjustsizelinearlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * 项目名称：SonChance
 * 类描述：监听键盘弹出、收缩
 * 创建人：Administrator
 * 创建时间： 2017/10/12 15:45
 * 修改人：Administrator
 * 修改时间：2017/10/12 15:45
 * 修改备注：
 */

public class KeyboardLayout extends RelativeLayout {

    private onSizeChangedListener mChangedListener;
    private static final String TAG = "KeyboardLayoutTAG";
    private boolean mShowKeyboard = false;

    public KeyboardLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public KeyboardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public KeyboardLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure-----------");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "onLayout-------------------");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "--------------------------------------------------------------");
        Log.d(TAG, "w----" + w + "\n" + "h-----" + h + "\n" + "oldW-----" + oldw + "\noldh----" + oldh);
        if (null != mChangedListener && 0 != oldw && 0 != oldh) {
            if (h < oldh) {
                mShowKeyboard = true;
            } else {
                mShowKeyboard = false;
            }
            mChangedListener.onChanged(mShowKeyboard);
            Log.d(TAG, "mShowKeyboard-----      " + mShowKeyboard);
        }
    }

    public void setOnSizeChangedListener(onSizeChangedListener listener) {
        mChangedListener = listener;
    }

    interface onSizeChangedListener {

        void onChanged(boolean showKeyboard);
    }
}