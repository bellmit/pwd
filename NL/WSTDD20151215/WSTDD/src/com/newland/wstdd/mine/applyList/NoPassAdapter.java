/**
 * @fields NoPassAdapter.java
 */
package com.newland.wstdd.mine.applyList;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.updownloadimg.ImageDownLoad;
import com.newland.wstdd.mine.applyList.bean.TddActivitySignVoInfo;

/**
 * 未通过名单 listView的 Adapter
 * 
 * @author H81 2015-11-27
 * 
 */
public class NoPassAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater layoutInflater;
	private List<TddActivitySignVoInfo> signNoPassList;
	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> isSelected;
	ViewHolder viewHolder = null;
	private int tempPosition;//临时存放的位置
	@SuppressLint("UseSparseArrays")
	public NoPassAdapter(FragmentActivity fragmentActivity, List<TddActivitySignVoInfo> signNoPassList) {
		this.signNoPassList = signNoPassList;
		this.context = fragmentActivity;
		layoutInflater = LayoutInflater.from(fragmentActivity);
		isSelected = new HashMap<Integer, Boolean>();
		// 初始化数据
		initDate();
	}

	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < signNoPassList.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	@Override
	public int getCount() {
		return signNoPassList == null ? 0 : signNoPassList.size();
	}

	@Override
	public Object getItem(int position) {
		if (signNoPassList != null && signNoPassList.size() > 0) {
			return signNoPassList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
//		 tempPosition = position ;
		if (convertView == null ) {
			convertView = layoutInflater.inflate(R.layout.activity_nopass_item, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (NoPassListFragment.getBottomlayout().getVisibility() == View.GONE) {
			viewHolder.mCheckBox.setVisibility(View.GONE);
		}else if (NoPassListFragment.getBottomlayout().getVisibility() == View.VISIBLE) {
			viewHolder.mCheckBox.setVisibility(View.VISIBLE);
		}
		viewHolder.mCheckBox.setChecked(getIsSelected().get(position)==null?true:getIsSelected().get(position));
//		if(signNoPassList.get(position).isSelected()){
//			viewHolder.mCheckBox.setChecked(true);
//		}else {
//			viewHolder.mCheckBox.setChecked(false);
//		}
		viewHolder.mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				System.out.println("position--------"+position);
				signNoPassList.get(position).setSelected(isChecked);
			}
		});
		
		ImageDownLoad.getDownLoadCircleImg(signNoPassList.get(position).getTddActivitySignVo().getSignHeadimgurl(), viewHolder.mHeadimg_iv);
		viewHolder.mNickname_tv.setText(signNoPassList.get(position).getTddActivitySignVo().getSignNickName());
		viewHolder.mTruename_tv.setText(signNoPassList.get(position).getTddActivitySignVo().getConnectUserName());
		String tempString = "未审核" ;
		if(signNoPassList.size()>0){
				if (1==(signNoPassList.get(position).getTddActivitySignVo().getAuditStatus())) {//审核状态  1.未审核  2.已通过  3.未通过
			tempString="未审核";
			viewHolder.mState_tv.setTextColor(context.getResources().getColor(R.color.green));
		}else if (3==(signNoPassList.get(position).getTddActivitySignVo().getAuditStatus())) {
			tempString="已拒绝";
			viewHolder.mState_tv.setTextColor(context.getResources().getColor(R.color.textgray));
		}
		}
	
		viewHolder.mState_tv.setText(tempString);
//		viewHolder.mNum_tv.setText((Integer.valueOf(signNoPassList.get(position).getKidNum()) + Integer
//				.valueOf(signNoPassList.get(position).getAdultNum())) + "");
		viewHolder.mNum_tv.setText("+1");
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		NoPassAdapter.isSelected = isSelected;
	}

	class ViewHolder {
		private CheckBox mCheckBox;//
		private ImageView mHeadimg_iv;// 头像
		private TextView mNickname_tv;// 昵称
		private TextView mTruename_tv;// 真实姓名
		private TextView mState_tv;// 状态
		private TextView mNum_tv;// 随行人数

		public ViewHolder(View convertView) {
			mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox);
			mHeadimg_iv = (ImageView) convertView.findViewById(R.id.headimg_iv);
			mNickname_tv = (TextView) convertView.findViewById(R.id.nickname_tv);
			mTruename_tv = (TextView) convertView.findViewById(R.id.truename_tv);
			mState_tv = (TextView) convertView.findViewById(R.id.state_tv);
			mNum_tv = (TextView) convertView.findViewById(R.id.num_tv);
		}
	}

	public List<TddActivitySignVoInfo> getSignNoPassList() {
		return signNoPassList;
	}

	public void setSignNoPassList(List<TddActivitySignVoInfo> signNoPassList) {
		this.signNoPassList = signNoPassList;
	}

}
