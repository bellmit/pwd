package com.newland.wstdd.mine.applyList;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class BasePassListView extends ListView {

	public BasePassListView(Context context) {
		super(context);
	}

	public BasePassListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BasePassListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
