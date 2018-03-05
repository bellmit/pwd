package rongshanghui.tastebychance.com.rongshanghui.view.pullableview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by shenbinghong on 2018/1/17.
 */

public class PullableImageView extends ImageView implements Pullable {
    public PullableImageView(Context context) {
        super(context);
    }

    public PullableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        return true;
    }

    @Override
    public boolean canPullUp() {
        return true;
    }
}
