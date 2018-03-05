package com.tastebychance.sonchance.util;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * 项目名称：SonChance 使文本可以部分点击
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/28 18:14
 * 修改人：Administrator
 * 修改时间：2017/9/28 18:14
 * 修改备注：
 */

public class TextClickable extends ClickableSpan {
    private View.OnClickListener listener;

    public TextClickable(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View widget) {
            listener.onClick(widget);
    }

    /**
     * 重写父类updateDrawState方法  我们可以给TextView设置字体颜色,背景颜色等等...
     */
    @Override
    public void updateDrawState(TextPaint ds) {
        //			ds.setColor(getResources().getColor(R.color.bg_gray));
//			ds.setFlags(Paint.UNDERLINE_TEXT_FLAG);
        ds.setUnderlineText(true);            //设置该文本部分是否显示超链接形式的下划线
    }

}
