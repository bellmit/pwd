package com.newland.wstdd.find.categorylist.registrationedit.editregistration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Delayed;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.tools.UiHelper;

/**
 * 发现-listview适配器
 * 
 * @author H81 2015-11-6
 * 
 */
public class EditSxRegistrationEditAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	
	private Context context;
	private EditSxRegistrationEditChildAdapter adapter;
	private List<EditSxRegistrationEditAdapterData> registrationData;// 数据
	private boolean showRl1 = true;// 第一个rl布局需要显示出来 姓名 手机号 删除
	private boolean showRl2 = true;// 第二个rl布局需要显示出来 编辑信息 完成

	public EditSxRegistrationEditAdapter(Context context, List<EditSxRegistrationEditAdapterData> registrationData) {
		this.context = context;
		this.registrationData = registrationData;
		mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		return registrationData == null ? 0 : registrationData.size();
	}

	@Override
	public EditSxRegistrationEditAdapterData getItem(int position) {
		if (registrationData != null && registrationData.size() != 0) {
			return registrationData.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;//
		if (convertView == null) {
			holder = new ViewHolder();
			// 根据自定义的Item布局加载布局
			convertView = mInflater.inflate(R.layout.registration_edit_registration_listview_item, parent, false);// 这里有区别？？？
			holder.name = (TextView) convertView.findViewById(R.id.registration_edit_sxtop_name);
			holder.phone = (TextView) convertView.findViewById(R.id.registration_edit_sxtop_phone);
			holder.delete = (TextView) convertView.findViewById(R.id.registration_edit_sxtop_delete);
			holder.edit = (TextView) convertView.findViewById(R.id.registration_edit_sxtop_edit);
			holder.listView = (EditSxRegistrationEditListViews) convertView.findViewById(R.id.registration_sx_listview);
			holder.rl1 = (RelativeLayout) convertView.findViewById(R.id.registration_edit_listview_item_toprl1);
			holder.rl2 = (RelativeLayout) convertView.findViewById(R.id.registration_edit_listview_item_toprl2);
			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final EditSxRegistrationEditAdapterData data = getItem(position);
		holder.delete.setText("删除");
		holder.edit.setText("完成");
		adapter = new EditSxRegistrationEditChildAdapter(context, registrationData, position);
		holder.listView.setAdapter(adapter);
		// 点击红色的信息布局 隐藏跟显示出可以进行编辑的界面
		holder.rl1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 显示跟隐藏之前都需要先判断下是否为空 只有点击完成了才会把数据添加进去，而不是修改的时候就进行添加数据的
			
				if (data.isShowRl2()) {
					data.setShowRl2(false);
				} else {
					data.setShowRl2(true);
				}
				if (data.isShowListView()) {
					data.setShowListView(false);
				} else {
					data.setShowListView(true);
				}
				List<Map<String, String>> maps = data.getMap();
				List<Map<String, String>> lMaps = data.getLastTempList();
				for (int i = 0; i < maps.size(); i++) {
					Map<String, String> map = maps.get(i);
					for (Entry<String, String> entry : lMaps.get(i).entrySet()) {
						map.put(entry.getKey(), entry.getValue());
					}
				}
				notifyDataSetChanged();
				adapter.notifyDataSetChanged();

			}
		});

		holder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				data.setShowRl1(false);
				data.setShowRl2(false);
				data.setShowListView(false);
				registrationData.remove(data);	
				notifyDataSetChanged();
				
			}
		});
		
		// 完成的编辑事件
		holder.edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				List<Map<String, String>> maps = data.getMap();
				List<Map<String, String>> iMaps = data.getInputTempList();
				List<Map<String, String>> lMaps = data.getLastTempList();
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < maps.size(); i++) {
					Map<String, String> map = maps.get(i);
					Map<String, String> lMap = lMaps.get(i);
					for (Entry<String, String> entry : iMaps.get(i).entrySet()) {
						map.put(entry.getKey(), entry.getValue());
						lMap.put(entry.getKey(), entry.getValue());
						builder.append(entry.getValue() + " ");
					}
				}
				String nameString = null;
				String phoneString = null;
				List<Map<String, String>> values = data.getMap();
				boolean isEmpty = false;// 判断是否所有选项都不为空或者“”
				for (Map<String, String> map : values) {
					for (Entry<String, String> entry : map.entrySet()) {
						Object key = entry.getKey();
						Object val = entry.getValue();
						if ("姓名".equals((String) key)) {
							nameString = (String) val;
						}
						if ("手机".equals((String) key)) {
							phoneString = (String) val;
						}
						if (val == null || "".equals(val)) {
							UiHelper.ShowOneToast(context, "不能有空信息");
							isEmpty = true;
							break;
						}
					}
				}
				if (!isEmpty) {
					UiHelper.ShowOneToast(context, "有信息");
					data.setInParent(position);
					data.setShowRl1(true);
					data.setShowRl2(false);
					data.setShowListView(false);
					data.setName(nameString);
					data.setPhone(phoneString);
				}
				notifyDataSetChanged();
				adapter.notifyDataSetChanged();

			}
		});

		// 隐藏与显示的方法

		if (data.isShowRl1()) {
			holder.rl1.setVisibility(View.VISIBLE);
		} else {
			holder.rl1.setVisibility(View.GONE);
		}
		// 第二个rl布局
		if (data.isShowRl2()) {
			holder.rl2.setVisibility(View.VISIBLE);
		} else {
			holder.rl2.setVisibility(View.GONE);
		}

		// 第三个listview布局
		if (data.isShowListView()) {
			holder.listView.setVisibility(View.VISIBLE);
		} else {
			holder.listView.setVisibility(View.GONE);
		}
		String nameString = data.getName();
		String phoneString = data.getPhone();
		holder.name.setText(data.getName());
		holder.phone.setText(data.getPhone());
		return convertView;
	}

	// ViewHolder静态类
	class ViewHolder {
		public TextView name;// 姓名
		public TextView phone;// 电话
		public TextView delete;// 删除
		public TextView edit;// 编辑
		public EditSxRegistrationEditListViews listView;// 动态添加
		// 需要进行显示隐藏的
		public RelativeLayout rl1, rl2;// 需要进行显示隐藏的布局控件
	}

	
	public List<EditSxRegistrationEditAdapterData> getRegistrationData() {
		return registrationData;
	}

	public void setRegistrationData(List<EditSxRegistrationEditAdapterData> registrationData) {
		this.registrationData = registrationData;
	}

	public boolean isShowRl2() {
		return showRl2;
	}

	public void setShowRl2(boolean showRl2) {
		this.showRl2 = showRl2;
	}

}
