package com.newland.wstdd.find.categorylist.detail.registration;

import java.util.List;

import com.newland.wstdd.R;
import com.newland.wstdd.find.categorylist.detail.registration.bean.CustomPayBill;
import com.newland.wstdd.find.categorylist.detail.registration.bean.PayBill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author H81 2015-11-23
 *
 */
public class CustomExpendListViewAdapter extends BaseExpandableListAdapter {
	private LayoutInflater layoutInflater;
	List<CustomPayBill> childPayBills;
	Context context;
	public CustomExpendListViewAdapter(Context context,List<CustomPayBill> childPayBills) {
		this.childPayBills = childPayBills;
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
	}

	// 获取指定组位置、指定子列表项处的子列表项数据
	public Object getChild(int groupPosition, int childPosition) {
		return childPayBills.get(groupPosition).getChildPayBills().get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public int getChildrenCount(int groupPosition) {
	return childPayBills.get(groupPosition).getChildPayBills().size();
	}

	// 该方法决定每个子选项的外观
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.salary_detail_cell2, null);
			viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.tv_name);
			viewHolder.valueTextView = (TextView) convertView.findViewById(R.id.tv_value);
			viewHolder.ll_salarydetailcell2 = (LinearLayout) convertView.findViewById(R.id.ll_salarydetailcell2);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		PayBill payBill = (PayBill) getChild(groupPosition, childPosition);
		viewHolder.nameTextView.setText(payBill.getPay_value());
		viewHolder.valueTextView.setText(payBill.getPay_num());
		return convertView;
	}

	class ViewHolder {
		TextView nameTextView;
		TextView valueTextView;
		LinearLayout ll_salarydetailcell2;
	}

	// 获取指定组位置处的组数据
	public Object getGroup(int groupPosition) {
		return childPayBills.get(groupPosition);
	}

	public int getGroupCount() {
		return childPayBills.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// 该方法决定每个组选项的外观
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		CustomPayBill customPayBill = (CustomPayBill) getGroup(groupPosition);
		ViewHolderGroup viewHolderGroup = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.salary_detail_cell, null);
			viewHolderGroup = new ViewHolderGroup();
			viewHolderGroup.imageView = (ImageView) convertView.findViewById(R.id.iv_arrow);
			viewHolderGroup.nameTextView = (TextView) convertView.findViewById(R.id.tv_name);
			viewHolderGroup.valueTextView = (TextView) convertView.findViewById(R.id.tv_value);
			convertView.setTag(viewHolderGroup);
		} else {
			viewHolderGroup = (ViewHolderGroup) convertView.getTag();
		}
		viewHolderGroup.nameTextView.setText(customPayBill.getPayBill().getPay_value());
		viewHolderGroup.valueTextView.setText(customPayBill.getPayBill().getPay_num());
		if (customPayBill.getChildPayBills() == null || customPayBill.getChildPayBills().size() == 0) {
			viewHolderGroup.imageView.setVisibility(View.INVISIBLE);
		} else {
			viewHolderGroup.imageView.setVisibility(View.VISIBLE);
			if (isExpanded) {
				viewHolderGroup.imageView.setBackgroundResource(R.drawable.down_expansion);
			} else {
				viewHolderGroup.imageView.setBackgroundResource(R.drawable.right_expansion);
			}
		}
		return convertView;
	}

	class ViewHolderGroup {
		ImageView imageView;
		TextView nameTextView;
		TextView valueTextView;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	public boolean hasStableIds() {
		return false;
	}

}
