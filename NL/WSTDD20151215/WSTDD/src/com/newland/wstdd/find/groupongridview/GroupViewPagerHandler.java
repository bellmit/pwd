package com.newland.wstdd.find.groupongridview;

import android.content.Context;
import android.os.Handler;

/**
 * 为了防止内存泄漏，定义外部类，防止内部类对外部类的引用
 */
public class GroupViewPagerHandler extends Handler {
	 Context context;

	public GroupViewPagerHandler(Context context) {
		this.context = context;
	}
};