package com.tastebychance.sonchance.view.cleanableeditext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.tastebychance.sonchance.R;

/**
 * 项目名称：SonChance
 * 类描述：带删除按钮的EditText控件
 * 创建人：Administrator
 * 创建时间： 2017/10/11 11:16
 * 修改人：Administrator
 * 修改时间：2017/10/11 11:16
 * 修改备注：
 */

public class CleanableEditText extends EditText {
//    回调函数
    private TextWatcherCallBack mCallback;
//    右侧删除图标
    private Drawable mDrawable;
    private Context mContext;

    public void setCallBack(TextWatcherCallBack mCallback){
        this.mCallback = mCallback;
    }

    public CleanableEditText(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public CleanableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        mDrawable = mContext.getResources().getDrawable(R.drawable.addresssearch_clear);
        mCallback = null;
        //重写了TextWatcher，在具体实现时就不用每个方法都实现，减少代码量
        TextWatcher textWatcher = new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                //更新状态，检查是否显示删除按钮
                updateCleanable(length(),true);
                //如果有在Activity中设置回调，则此处可以出发
                if (mCallback != null){
                    mCallback.handleMoreTextChanged();
                }
            }
        };

        this.addTextChangedListener(textWatcher);
        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //更新状态，检查是否显示删除按钮
                updateCleanable(length(),hasFocus);
            }
        });
    }

    //当内容不为空，而且获得焦点，才显示右侧删除按钮
    private void updateCleanable(int length, boolean hasFocus) {
        /**
         * 可以为EditText设置上下左右的图标
         */
        if (length > 0 && hasFocus){
            setCompoundDrawablesWithIntrinsicBounds(null,null,mDrawable,null);
        }else{
            setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;
        //可以获得上下左右四个drawable，右侧排第二。图标没有设置则为空
        Drawable rightIcon = getCompoundDrawables()[DRAWABLE_RIGHT];
        if (rightIcon != null && event.getAction() == MotionEvent.ACTION_UP){
            //检查点击的位置是否是右侧的删除图标
            //注意，使用getRowX()是获取相对屏幕的位置，getX()可能获取相对父组件的位置
            int leftEdgeOfRightDrawable = getRight()  - getPaddingRight() - rightIcon.getBounds().width();
            if (event.getRawX() >= leftEdgeOfRightDrawable){
                setText("");
            }
        }

        /**
         * 在onTouchEvent()方法中，如果消耗事件（依据情况返回true或者false），则会出现一个问题，可以点击EditText，如果设置日志输出，可以发现action_down，action_move，action_up都输出了，代表点击事件正常，但是依然无法获得焦点。所以不难猜测EditText获得焦点可能和点击事件有关。如果强制调用requestFocus()方法，则可以“解决”这个问题，但是存在不稳定现象，有时会出bug，其中原因还没细究。于是，此处我不处理点击事件，直接返回super.onTouchEvent(event)
         */
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        mDrawable = null;
        super.finalize();
    }
}
