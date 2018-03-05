package com.newland.wstdd.common.base;

import java.util.LinkedList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * @author 作者 E-mail: WangJC
 * @version 创建时间：2013-11-11 上午10:49:10 类说明
 */
public abstract class BaseDialog extends Dialog implements OnClickListener {

	public static LinkedList<BaseDialog> dialogList = new LinkedList<BaseDialog>();
	protected LayoutInflater inflater = null;
	protected Context context = null;
	protected View view;

	public BaseDialog(Context context) {
		super(context);
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
		initData(context,theme);
	}

	protected BaseDialog(Context context, boolean cancelable,int theme,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initData(context,theme);
	}

	private void addDialog() {
		if (!dialogList.contains(this)) {
			dialogList.add(this);
		}
	}

	private void initData(Context context,int theme) {
		inflater = LayoutInflater.from(context);
		Context themeContext = getContext();
		LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(theme, null);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addDialog();
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (dialogList.size() > 0)
			dialogList.removeLast();
	}

	public static void closeAllDialog() {
		for (int i = 0; i < BaseDialog.dialogList.size(); i++) {
			BaseDialog.dialogList.get(i).dismiss();
		}
	}

	public void myShow(){
		show();		
		setContentView(view);
		
	};

}
