package rongshanghui.tastebychance.com.rongshanghui.view.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by shenbinghong on 2018/1/17.
 */

public class PullableExpandableListView extends ExpandableListView implements Pullable {
    public PullableExpandableListView(Context context) {
        super(context);
    }

    public PullableExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        if (getCount() == 0) {
            //没有item的时候也可以下拉刷新
            return true;
        } else if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() >= 0) {
            //滑到顶部了
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getCount() == 0) {//没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            //滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight()) {//滑到底部了
                return true;
            }
        }
        return false;
    }
}
