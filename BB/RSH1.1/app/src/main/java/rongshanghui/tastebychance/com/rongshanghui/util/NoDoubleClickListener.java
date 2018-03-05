package rongshanghui.tastebychance.com.rongshanghui.util;

import android.view.View;

import java.util.Calendar;

/**
 * 项目名称：SonChance
 * 类描述：防止过快的双击
 * 创建人：Administrator
 * 创建时间： 2017/10/17 9:22
 * 修改人：Administrator
 * 修改时间：2017/10/17 9:22
 * 修改备注：
 */

public abstract class NoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    public abstract void onNoDoubleClick(View v);
}
