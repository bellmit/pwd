package rongshanghui.tastebychance.com.rongshanghui.common;

import android.os.Handler;
import android.support.annotation.NonNull;

import name.quanke.app.libs.emptylayout.EmptyLayout;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;

/**
 * Created by shenbinghong on 2018/2/11.
 */

public class MyBaseHandler extends Handler {

    public void emptyLayoutShowOrHide(@NonNull EmptyLayout emptyLayout, @NonNull boolean toShow) {
        try {
            if (null == emptyLayout) {
                return;
            }
            if (toShow) {
                emptyLayout.showEmpty(Constants.EMPTY_NULL_PIC, Constants.EMPTY_NULL_STR);
            } else {
                emptyLayout.hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
