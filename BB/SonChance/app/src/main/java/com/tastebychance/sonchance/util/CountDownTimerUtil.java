package com.tastebychance.sonchance.util;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tastebychance.sonchance.R;

import java.sql.Time;

/**倒计时（验证码倒计时、普通倒计时（包含结束显示内容、结束触发事件））
 * Created by shenbh on 2017/8/22.
 */
public class CountDownTimerUtil extends CountDownTimer {

    private TextView mTextView;
    private SpannableStringBuilder textStyle ;
    private String timeType;
    private OnFinishListener listener;
    private int countDownDrawableId = R.drawable.rectangle_grayedge_style;//倒计时时的背景
    private int normalDrawableId = R.drawable.rectangle_blackedge_style;//恢复时的背景

    /**
     * @param  textView
     *@param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.   */
    public CountDownTimerUtil(TextView textView, long millisInFuture, long countDownInterval,SpannableStringBuilder textStyle) {
        super(millisInFuture, countDownInterval);
        mTextView = textView;
        this.textStyle = textStyle;
    }

    /**
     *
     * @param mTextView
     * @param millisInFuture 总的秒数
     * @param countDownInterval 时间间隔，隔几秒刷新一下
     * @param textStyle  结束提示内容
     * @param countDownDrawableId 倒计时时背景色
     * @param normalDrawableId 恢复时背景色
     * @param timeType 时间格式
     * @param listener 倒计时结束时触发的事件
     */
    public CountDownTimerUtil(TextView mTextView, long millisInFuture, long countDownInterval,SpannableStringBuilder textStyle,
                              int countDownDrawableId, int normalDrawableId , String timeType, OnFinishListener listener) {
        super(millisInFuture, countDownInterval);
        this.mTextView = mTextView;
        this.timeType = timeType;
        if (0 != countDownDrawableId){
            this.countDownDrawableId = countDownDrawableId;
        }
        if (0 != normalDrawableId){
            this.normalDrawableId = normalDrawableId;
        }
        this.textStyle = textStyle;
        this.listener = listener;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false);//设置不可点击

        if (null != timeType) {//传时间类型 比如timeType="mm:ss"
            mTextView.setText(TimeOrDateUtil.long2FormatTime(millisUntilFinished, timeType));//long2FormatTime为long型转成timeType格式的String型
        } else {

            //适用60s验证码倒计时
            mTextView.setText(millisUntilFinished / 1000 + "秒"); //设置倒计时时间
            mTextView.setBackgroundResource(countDownDrawableId);  //倒计时背景色
            mTextView.setClickable(false);
            mTextView.setPadding(10,5,10,5);

            /**
             * 超链接 URLSpan
             * 文字背景颜色 BackgroundColorSpan
             * 文字颜色 ForegroundColorSpan
             * 字体大小 AbsoluteSizeSpan
             * 粗体、斜体 StyleSpan
             * 删除线 StrikethroughSpan
             * 下划线 UnderlineSpan
             * 图片 ImageSpan
             * http://blog.csdn.net/ah200614435/article/details/7914459
             */
            /*SpannableString spannableString = new SpannableString(mTextView.getText().toString());  //获取按钮上的文字
            ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);*/
            /**
             * public void setSpan(Object what, int start, int end, int flags) {
             * 主要是start跟end，start是起始位置,无论中英文，都算一个。
             * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
             */
            /*spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
            mTextView.setText(spannableString);*/
        }
    }

    @Override
    public void onFinish() {
        mTextView.setText(textStyle);
        mTextView.setClickable(true);//重新获得点击
        mTextView.setTextColor(Color.BLACK);
        mTextView.setBackgroundResource(normalDrawableId);  //还原背景色
        mTextView.setPadding(10,5,10,5);
        if (null != listener) {//写在倒计时结束的方法内，在倒计时结束的时候才会触发
            listener.onFinishListener();
        }
    }

    /**倒计时结束的时候触发事件
     */
    public interface OnFinishListener {
        void onFinishListener();
    }
}

