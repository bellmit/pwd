/**
 * 
 */
package com.newland.wstdd.mine.managerpage;

import java.util.List;

import com.newland.wstdd.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的活动-我发起/我参与/我收藏-列表 适配器
 * 
 * @author H81 2015-11-10
 * 
 */
public class MyActivitiesOriginateListFragmentAdapter extends BaseAdapter {
	Context context;
	LayoutInflater layoutInflater;
	List<String> list;

	public MyActivitiesOriginateListFragmentAdapter(Context context,List<String>list) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.list= list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.activity_find_chairdetail_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mLogo = (ImageView) convertView.findViewById(R.id.logo);
			viewHolder.mTitle = (TextView) convertView.findViewById(R.id.title);
		}
		return convertView;
	}

	class ViewHolder {
		private ImageView mLogo;
		private TextView mTitle;
	}
}
