package rongshanghui.tastebychance.com.rongshanghui.view.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by shenbinghong on 2018/1/17.
 */

public class PullableScrollView extends ScrollView implements Pullable {
    public PullableScrollView(Context context) {
        super(context);
    }

    public PullableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        System.out.println("canPullDown---------:getScrollY():" + getScrollY());
        if (getScrollY() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPullUp() {
        System.out.println("canPullUp-----------:getScrollY():" + getScrollY() + "\ngetChildAt(0).getHeight():" + getChildAt(0).getHeight() + "\ngetMeasuredHeight():" + getMeasuredHeight());
        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight())) {
            return true;
        } else {
            return false;
        }
    }
}
