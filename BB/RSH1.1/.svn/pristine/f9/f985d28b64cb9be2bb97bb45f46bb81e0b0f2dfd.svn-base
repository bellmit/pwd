package rongshanghui.tastebychance.com.rongshanghui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import rongshanghui.tastebychance.com.rongshanghui.R;


/**
 * 自定义的文本控件
 * <p>
 * 作用：用来设置文本的左右上下的图片的大小
 */
public class PengTextView extends AppCompatTextView {
    // 申明上下左右对应的图片
    private Drawable drawableTop, drawableBottom, drawableLeft, drawableRight;
    // 上下左右图片对应长和宽
    private int mTopWith, mTopHeight, mBottomWith, mBottomHeight, mRightWith, mRightHeight, mLeftWith, mLeftHeight;

    public PengTextView(Context context) {
        super(context);
        initView(context, null);
    }

    public PengTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    public PengTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        // 设置为可点击的
        setClickable(true);
        if (attrs != null) {
            float scale = context.getResources().getDisplayMetrics().density;
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PengRadioButton);
            int count = a.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = a.getIndex(i);

                switch (attr) {
                    case R.styleable.PengRadioButton_peng_drawableBottom:
                        drawableBottom = a.getDrawable(attr);
                        break;

                    case R.styleable.PengRadioButton_peng_drawableTop:
                        drawableTop = a.getDrawable(attr);
                        break;

                    case R.styleable.PengRadioButton_peng_drawableLeft:
                        drawableLeft = a.getDrawable(attr);
                        break;

                    case R.styleable.PengRadioButton_peng_drawableRight:
                        drawableRight = a.getDrawable(attr);
                        break;

                    case R.styleable.PengRadioButton_peng_drawableTopWidth:
                        mTopWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;

                    case R.styleable.PengRadioButton_peng_drawableTopHeight:
                        mTopHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;

                    case R.styleable.PengRadioButton_peng_drawableBottomWidth:
                        mBottomWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;

                    case R.styleable.PengRadioButton_peng_drawableBottomHeight:
                        mBottomHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;

                    case R.styleable.PengRadioButton_peng_drawableRightWidth:
                        mRightWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;

                    case R.styleable.PengRadioButton_peng_drawableRightHeight:
                        mRightHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;

                    case R.styleable.PengRadioButton_peng_drawableLeftWidth:
                        mLeftWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;

                    case R.styleable.PengRadioButton_peng_drawableLeftHeight:
                        mLeftHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
                        break;

                    default:
                        break;

                }
            }
            a.recycle();
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
        }

    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (left != null) {
            left.setBounds(0, 0, mLeftWith <= 0 ? left.getIntrinsicWidth() : mLeftWith,
                    mLeftHeight <= 0 ? left.getMinimumHeight() : mLeftHeight);
        }
        if (right != null) {
            right.setBounds(0, 0, mRightWith <= 0 ? right.getIntrinsicWidth() : mRightWith,
                    mRightHeight <= 0 ? right.getMinimumHeight() : mRightHeight);
        }
        if (top != null) {
            top.setBounds(0, 0, mTopWith <= 0 ? top.getIntrinsicWidth() : mTopWith,
                    mTopHeight <= 0 ? top.getMinimumHeight() : mTopHeight);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, mBottomWith <= 0 ? bottom.getIntrinsicWidth() : mBottomWith,
                    mBottomHeight <= 0 ? bottom.getMinimumHeight() : mBottomHeight);
        }

        setCompoundDrawables(left, top, right, bottom);

    }

    @Override
    public void setCompoundDrawablePadding(int pad) {
        super.setCompoundDrawablePadding(pad);
    }

    public Drawable getDrawableTop() {
        return drawableTop;
    }

    public void setDrawableTop(Drawable drawableTop) {
        this.drawableTop = drawableTop;
    }

    public Drawable getDrawableLeft() {
        return drawableLeft;
    }

    public void setDrawableLeft(Drawable drawableLeft) {
        this.drawableLeft = drawableLeft;
    }

}
