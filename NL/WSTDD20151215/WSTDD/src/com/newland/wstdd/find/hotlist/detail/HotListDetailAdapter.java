/**
 * 
 */
package com.newland.wstdd.find.hotlist.detail;

import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


/**发现-热门列表具体详情界面适配器
 * @author H81 2015-11-19
 *
 */
public class HotListDetailAdapter extends BaseAdapter {
	LayoutInflater inflater;
//	List<FilingDetailData> list;
	List<String> list;
	Context context;

	public HotListDetailAdapter(FragmentActivity myAttendanceLeaveActivity, List<String> group_list) {
		this.context = myAttendanceLeaveActivity;
		list = group_list;
		inflater = LayoutInflater.from(myAttendanceLeaveActivity);
	}

	
	public int getCount() {
		return list.size();
	}

	
	public Object getItem(int position) {
		return list.get(position);
	}

	
	public long getItemId(int position) {
		return position;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
//			convertView = inflater.inflate(R.layout.my_attendance_statistics_detail_belate_item, null);
			viewHolder = new ViewHolder();
//			viewHolder. mTime = (TextView) convertView.findViewById(R.id.time);
//			viewHolder.    mStyle = (TextView) convertView.findViewById(R.id.style);
//			viewHolder.  mType = (TextView) convertView.findViewById(R.id.type);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
//		viewHolder. mTime.setText(StringUtil.noNull(list.get(position).getDur_time()));
//		viewHolder.mStyle.setText(StringUtil.noNull(list.get(position).getKq_time()));
//		viewHolder.mType.setText(StringUtil.noNull(list.get(position).getKq_type()));
		return convertView;
	}

	class ViewHolder {
//		private TextView mTime;//迟到日期
//	    private TextView mStyle;//迟到时长
//	    private TextView mType;//考勤过失级别
	}
}