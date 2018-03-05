package com.newland.wstdd.common.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**自定义LinearLayout布局，用于调整屏幕size来显示软键盘
 * @author Administrator
 * 2015-12-17
 */
public class CustomLinearLayout extends LinearLayout{
	public static final int KEYBORAD_HIDE = 0;
	public static final int KEYBORAD_SHOW = 1;
	private static final int SOFTKEYPAD_MIN_HEIGHT = 50;
	private Handler uiHandler = new Handler();
	
	public CustomLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public CustomLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public CustomLinearLayout(Context context) {
		super(context);
		
	}

	@Override
	protected void onSizeChanged(int w, final int h, int oldw, final int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		uiHandler.post(new Runnable() {
			
			@Override
			public void run() {
				if (oldh - h > SOFTKEYPAD_MIN_HEIGHT) {//软键盘关闭
					keyBordStateListener.stateChange(KEYBORAD_SHOW);//回调方法显示部分区域
				}else {//软键盘弹出
					if (keyBordStateListener != null) {
						keyBordStateListener.stateChange(KEYBORAD_HIDE);//回调方法隐藏部分区域
					}
				}
			}
		});
	}
	
	private KeyBordStateListener keyBordStateListener;
	public void setKeyBordStateListener(KeyBordStateListener keyBordStateListener){
		this.keyBordStateListener = keyBordStateListener;
	}
	public interface KeyBordStateListener{
		public void stateChange(int keyboradShow);
	}
}
