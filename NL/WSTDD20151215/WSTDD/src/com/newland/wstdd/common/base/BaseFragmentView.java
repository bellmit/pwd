package com.newland.wstdd.common.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.ScrollView;

public abstract class BaseFragmentView extends ScrollView implements OnClickListener{

	public boolean initflag = false;
	public Context context = null;
	
	public BaseFragmentView(Context context) {
		super(context);
		this.context = context;
		this.setVerticalScrollBarEnabled(false);//隐藏listView的滚动条
	}
	public BaseFragmentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.setVerticalScrollBarEnabled(false);//隐藏listView的滚动条
	}
	
	abstract public void refresh();

}
