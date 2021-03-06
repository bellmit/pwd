package com.newland.wstdd.common.base;

import com.newland.wstdd.common.view.LoadingDialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements OnClickListener {

	protected FragmentActivity fragmentActivity = null;

	public BaseFragment(){}
	// 相当于无参构造函数一样
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.err.println("执行顺序是++++++++++++++++++1");
		fragmentActivity = getActivity();

	}

	/**
	 * 当该类的子类创建对象的时候 会执行这个方法，好好理解一下类似于：
	 * 子类调用构造函数的时候，会调用父类的无参构造函数一样，而这个类比较特殊，相当于Activtiy 会先去执行OnCreateView这个方法
	 * 必须使用onCreateView()定义他的layout布局文件
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 调用子类的这个方法
		System.err.println("执行顺序是++++++++++++++++++2");
		View view = createAndInitView(inflater, container, savedInstanceState);

		return view;
	}

	// 子类会重载这个方法
	protected abstract View createAndInitView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	public void refresh(){
		showDialog();
	}
	
	
	public LoadingDialog dialog;
	public void showDialog(){
		if (dialog == null) {
			initDialog(fragmentActivity);
		}else {
			dialog.dismiss();
			initDialog(fragmentActivity);
		}
	}
	public void initDialog(FragmentActivity fragmentActivity) {
		dialog = new LoadingDialog(fragmentActivity);
		dialog.setTvMessage("正在加载...");
		if (!fragmentActivity.isFinishing()) {
			dialog.show(true);
		}else {
			dialog.show(false);
		}
	}

}
