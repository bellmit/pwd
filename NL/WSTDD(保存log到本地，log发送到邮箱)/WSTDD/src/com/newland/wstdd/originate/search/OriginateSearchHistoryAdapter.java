/**
 * 
 */
package com.newland.wstdd.originate.search;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newland.wstdd.R;

/**
 * 发起-搜索适配器
 * 
 * @author H81 2015-11-6
 * 
 */
public class OriginateSearchHistoryAdapter extends BaseAdapter {
	Context context;
	List<HashMap<String, String>> list;
	LayoutInflater layoutInflater;
	
	private onItemClickListener itemClickListener = null;

	public void setOnItemClickListener(onItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

	public interface onItemClickListener{
		void onItemClick(View v, int position);
	}
	
	public OriginateSearchHistoryAdapter(Context context, List<HashMap<String, String>> list) {
		this.context = context;
		this.list = list;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
//		return list == null ? 0 : (list.size() >20 ? 20:list.size());
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		if (list!=null && list.size() > 0 ) {
			return list.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.fragment_originate_search_item, null);
			viewHolder.mItem_tv = (TextView) convertView.findViewById(R.id.item_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mItem_tv.setText(getStringFromHashMap(list.get(list.size() - position - 1), "historyitem"+(list.size() - position)));
		viewHolder.mItem_tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (itemClickListener != null) {
					itemClickListener.onItemClick(v, position);
				}
			}
		});
		return convertView;
	}

	class ViewHolder {
		private TextView mItem_tv;

	}
	
	public String getStringFromHashMap(HashMap<String, String> map,String key) {
		String finalStr = null;
		finalStr = map.get(key);
		return finalStr;
	}

	public List<HashMap<String, String>> getList() {
		return list;
	}

	public void setList(List<HashMap<String, String>> list) {
		this.list = list;
	}
}
