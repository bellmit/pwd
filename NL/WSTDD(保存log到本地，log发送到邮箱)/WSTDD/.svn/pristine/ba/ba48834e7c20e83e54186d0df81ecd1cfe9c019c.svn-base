package com.newland.wstdd.mine.managerpage.multitext;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Picture;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.updownloadimg.ImageDownLoad;
import com.newland.wstdd.common.widget.RoundImageView;
import com.newland.wstdd.mine.applyList.bean.TddActivitySignVoInfo;

/**群发消息 Adapter
 * @author Administrator
 * 2015-12-11
 */
public class MultiTextAdapter extends BaseAdapter{

	private LayoutInflater layoutInflater;
	private List<TddActivitySignVoInfo> tddActivitySignVoInfos;
	
	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> isSelected;
		
	@SuppressLint("UseSparseArrays")
	public MultiTextAdapter(FragmentActivity fragmentActivity,List<TddActivitySignVoInfo> tddActivitySignVoInfos) {
		layoutInflater = LayoutInflater.from(fragmentActivity);
		this.tddActivitySignVoInfos = tddActivitySignVoInfos;
		isSelected = new HashMap<Integer, Boolean>();
	}
	
	@Override
	public int getCount() {
		return tddActivitySignVoInfos == null ? 0 : tddActivitySignVoInfos.size();
	}

	@Override
	public Object getItem(int position) {
		if (tddActivitySignVoInfos != null && tddActivitySignVoInfos.size() > 0) {
			return tddActivitySignVoInfos.get(position);
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
		if (convertView == null ) {
			convertView = layoutInflater.inflate(R.layout.activity_manager_multitext_item, null);
			viewHolder = new ViewHolder();
			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.item_checkBox);
			viewHolder.headimg_iv = (RoundImageView)convertView. findViewById(R.id.headimg_iv);
			viewHolder. nickname_tv = (TextView)convertView. findViewById(R.id.nickname_tv);
			viewHolder. truename_tv = (TextView)convertView. findViewById(R.id.truename_tv);
			viewHolder. num_tv = (TextView) convertView.findViewById(R.id.num_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		boolean temp = tddActivitySignVoInfos.get(position).isSelected();
		viewHolder.checkBox.setChecked(temp);
		viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				tddActivitySignVoInfos.get(position).setSelected(isChecked);
			}
		});
		ImageDownLoad.getDownLoadCircleImg(tddActivitySignVoInfos.get(position).getTddActivitySignVo().getSignHeadimgurl(), 
				viewHolder.headimg_iv);
		viewHolder.nickname_tv.setText(tddActivitySignVoInfos.get(position).getTddActivitySignVo().getSignNickName());
		viewHolder.truename_tv.setText(tddActivitySignVoInfos.get(position).getTddActivitySignVo().getSignUserName());
		viewHolder.num_tv.setText(tddActivitySignVoInfos.get(position).getTddActivitySignVo().getAdultNum()+"");
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		MultiTextAdapter.isSelected = isSelected;
	}

	class ViewHolder {
		private CheckBox checkBox;
		private RoundImageView headimg_iv;// 头像
		private TextView nickname_tv;// 昵称
		private TextView truename_tv;// 真实姓名
		private TextView num_tv;// 随行人数
	}

	public List<TddActivitySignVoInfo> getTddActivitySignVoInfos() {
		return tddActivitySignVoInfos;
	}

	public void setTddActivitySignVoInfos(List<TddActivitySignVoInfo> tddActivitySignVoInfos) {
		this.tddActivitySignVoInfos = tddActivitySignVoInfos;
	}
	
	
}
