package com.tastebychance.sfj.util;

import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.tastebychance.sfj.MyApplication;
import com.tastebychance.sfj.R;


/**
 * 倒计时（验证码倒计时、普通倒计时（包含结束显示内容、结束触发事件））
 * Created by shenbh on 2017/8/22.
 */
public class CountDownTimerUtil extends CountDownTimer {

    public static class CountDownBean {
        private TextView mTextView;//倒计时控件
        private long millisInFuture;//倒计时持续时间 The number of millis in the future from the call to {@link #start()} until the countdown is done and {@link #onFinish()} is called.
        private long countDownInterval;//倒计时时间间隔 The interval along the way to receive {@link #onTick(long)} callbacks.

        private SpannableStringBuilder textStyle;//文字格式
        private String timeType;//倒计时时钟格式(如果要显示“60秒”样式，则直接传null)

        private int countDownDrawableId = R.drawable.rectangle_darkblueedge_style;//倒计时时的背景
        private int countDownTextColor = R.color.textgray;//倒计时时的文字的颜色

        private String finishStr;//倒计时结束时显示的内容
        private int finishDrawableId = R.drawable.rectangle_darkbluebg_style;//恢复时的背景
        private int finishTextColor = R.color.white;//恢复时的文字的颜色
        private OnFinishListener listener;//倒计时结束触发

        public TextView getmTextView() {
            return mTextView;
        }

        public void setmTextView(TextView mTextView) {
            this.mTextView = mTextView;
        }

        public long getMillisInFuture() {
            return millisInFuture;
        }

        public void setMillisInFuture(long millisInFuture) {
            this.millisInFuture = millisInFuture;
        }

        public long getCountDownInterval() {
            return countDownInterval;
        }

        public void setCountDownInterval(long countDownInterval) {
            this.countDownInterval = countDownInterval;
        }

        public SpannableStringBuilder getTextStyle() {
            return textStyle;
        }

        public void setTextStyle(SpannableStringBuilder textStyle) {
            this.textStyle = textStyle;
        }

        public String getTimeType() {
            return timeType;
        }

        public void setTimeType(String timeType) {
            this.timeType = timeType;
        }

        public int getCountDownDrawableId() {
            return countDownDrawableId;
        }

        public void setCountDownDrawableId(int countDownDrawableId) {
            this.countDownDrawableId = countDownDrawableId;
        }

        public int getCountDownTextColor() {
            return countDownTextColor;
        }

        public void setCountDownTextColor(int countDownTextColor) {
            this.countDownTextColor = countDownTextColor;
        }

        public int getFinishDrawableId() {
            return finishDrawableId;
        }

        public void setFinishDrawableId(int finishDrawableId) {
            this.finishDrawableId = finishDrawableId;
        }

        public int getFinishTextColor() {
            return finishTextColor;
        }

        public void setFinishTextColor(int finishTextColor) {
            this.finishTextColor = finishTextColor;
        }

        public OnFinishListener getListener() {
            return listener;
        }

        public void setListener(OnFinishListener listener) {
            this.listener = listener;
        }

        public String getFinishStr() {
            return finishStr;
        }

        public void setFinishStr(String finishStr) {
            this.finishStr = finishStr;
        }
    }

    private CountDownBean countDownBean;

    /**
     * @param countDownBean
     */
    public CountDownTimerUtil(CountDownBean countDownBean) {
        super(countDownBean.getMillisInFuture(), countDownBean.getCountDownInterval());
        this.countDownBean = countDownBean;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        countDownBean.getmTextView().setClickable(false);//设置不可点击

        if (null != countDownBean.getTimeType()) {//传时间类型 比如timeType="mm:ss"
            countDownBean.getmTextView().setText(TimeOrDateUtil.long2FormatTime(millisUntilFinished, countDownBean.getTimeType()));//long2FormatTime为long型转成timeType格式的String型
        } else {

            //适用60s验证码倒计时
            countDownBean.getmTextView().setText(millisUntilFinished / 1000 + "秒"); //设置倒计时时间
            countDownBean.getmTextView().setTextColor(MyApplication.getContext().getResources().getColor(countDownBean.getCountDownTextColor()));
            countDownBean.getmTextView().setBackgroundResource(countDownBean.getCountDownDrawableId());  //倒计时背景色
            countDownBean.getmTextView().setClickable(false);
            countDownBean.getmTextView().setPadding(10, 5, 10, 5);

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
            /*SpannableString spannableString = new SpannableString(countDownBean.getmTextView().getText().toString());  //获取按钮上的文字
            ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);*/
            /**
             * public void setSpan(Object what, int start, int end, int flags) {
             * 主要是start跟end，start是起始位置,无论中英文，都算一个。
             * 从0开始计算起。end是结束位置，所以处理的文字，包含开始位置，但不包含结束位置。
             */
            /*spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
            countDownBean.getmTextView().setText(spannableString);*/
        }
    }

    @Override
    public void onFinish() {
        countDownBean.getmTextView().setText(countDownBean.getFinishStr());
        countDownBean.getmTextView().setClickable(true);//重新获得点击
        countDownBean.getmTextView().setTextColor(MyApplication.getContext().getResources().getColor(countDownBean.getFinishTextColor()));
        countDownBean.getmTextView().setBackgroundResource(countDownBean.getFinishDrawableId());  //还原背景色
        countDownBean.getmTextView().setPadding(10, 5, 10, 5);
        if (null != countDownBean.getListener()) {//写在倒计时结束的方法内，在倒计时结束的时候才会触发
            countDownBean.getListener().onFinishListener();
        }
    }

    /**
     * 倒计时结束的时候触发事件
     */
    public interface OnFinishListener {
        void onFinishListener();
    }
}

