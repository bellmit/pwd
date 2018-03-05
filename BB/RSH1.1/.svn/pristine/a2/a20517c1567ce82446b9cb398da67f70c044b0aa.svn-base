package rongshanghui.tastebychance.com.rongshanghui.view.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by shenbinghong on 2018/1/17.
 */

public class PullableListView extends ListView implements Pullable {

    public PullableListView(Context context) {
        super(context);
    }

    public PullableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        if (getCount() == 0) {//没有item的时候也可以下拉
            return true;
        } else if (getFirstVisiblePosition() == 0) {//滑到listview的顶部了
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPullUp() {
        if (getCount() == 0) {//没有item的时候也可以上拉
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {//滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight()) {
                return true;
            }
        }
        return false;
    }
}
