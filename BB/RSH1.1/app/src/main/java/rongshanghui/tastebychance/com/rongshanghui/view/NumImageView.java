package rongshanghui.tastebychance.com.rongshanghui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class NumImageView extends ImageView {

    // 要显示的数量
    private int num = 0;
    // 红色圆圈的半径
    private float radius;
    // 圆圈内数字的半径
    private float textSize;
    // 左边和上边内边距
    private int paddingLeft;
    private int paddingTop;

    public NumImageView(Context context) {
        super(context);
    }

    public NumImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // 设置显示的数量
    public void setNum(int num) {
        this.num = num;
        // 重新绘制画布
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (num > 0) {
            // 初始化半径
            radius = getWidth() / 12;
            // 初始化字体大小
            textSize = num < 10 ? +15 : radius;
            // 初始化边距
            paddingLeft = getPaddingLeft();
            paddingTop = getPaddingTop();
            // 初始化画笔
            Paint paint = new Paint();
            // 设置抗锯齿
            paint.setAntiAlias(true);
            // 设置颜色为红色
            paint.setColor(0xffff4444);
            // 设置填充样式为充满
            paint.setStyle(Paint.Style.FILL);
            // 画圆
            canvas.drawCircle(0 + radius + paddingLeft / 2, radius + paddingTop / 2, radius, paint);
            // 设置颜色为白色
            paint.setColor(0xffffffff);
            // 设置字体大小
            paint.setTextSize(textSize);
            // 画数字
            canvas.drawText("" + (num < 99 ? num : 99), num < 10 ? radius + textSize / 4 + paddingLeft
                            / 2 : radius + textSize / 2 + paddingLeft / 2,
                    radius + textSize / 3 + paddingTop / 2, paint);

        }

    }
}
