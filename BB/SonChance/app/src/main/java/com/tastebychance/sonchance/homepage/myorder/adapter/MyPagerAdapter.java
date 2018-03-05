package com.tastebychance.sonchance.homepage.myorder.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
/**
 * 类描述：MyPagerAdapter viewpager的adapter
 * 创建人: shenbh Email:shenbh@qq.com
 * 创建时间： 2017/9/21 8:56
 * 修改人：
 * 修改时间：2017/9/21 8:56
 * 修改备注：
 * @version 1.0
 */
public class MyPagerAdapter extends PagerAdapter {
	List<View> mViewList;
	public MyPagerAdapter(List<View> viewlist){
		mViewList = viewlist;
	}
	
	@Override
	public int getCount() {
		return mViewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViewList.get(position));
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mViewList.get(position));
		return mViewList.get(position);
	}

	public List<View> getViewList() {
		return mViewList;
	}
	/*public void setmViewList(List<View> mViewList) {
		this.mViewList = mViewList;
	}*/
	
}
