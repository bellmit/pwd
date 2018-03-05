/**
 * @fields PassAdapter.java
 */
package com.newland.wstdd.mine.applyList;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.smsphone.CallPhoneUtil;
import com.newland.wstdd.common.tools.Util;
import com.newland.wstdd.common.updownloadimg.ImageDownLoad;
import com.newland.wstdd.common.widget.RoundImageView;
import com.newland.wstdd.mine.applyList.bean.TddActivitySignVoInfo;

/**
 * 通过名单 团大团秘listView的 Adapter
 * 
 * @author H81 2015-11-29
 * 
 */
public class PassTMAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater layoutInflater;
	private List<TddActivitySignVoInfo> passTMList;
	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> isSelected;

	public PassTMAdapter(FragmentActivity fragmentActivity, List<TddActivitySignVoInfo> passTMList) {
		this.passTMList = passTMList;
		this.context = fragmentActivity;
		layoutInflater = LayoutInflater.from(fragmentActivity);
		isSelected = new HashMap<Integer, Boolean>();
		// 初始化数据
		initDate();
	}

	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < passTMList.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	@Override
	public int getCount() {
		return passTMList == null ? 0 : passTMList.size();
	}

	@Override
	public Object getItem(int position) {
		if (passTMList != null && passTMList.size() > 0) {
			return passTMList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		PassTMAdapter.isSelected = isSelected;
	}
	
	
	
	private onItemClickListener iListener = null;//点选事件
	

	public void setOnItemClickListener (onItemClickListener listener) {
		iListener = listener;
	}
	
	public interface onItemClickListener {
		void onItemClick(View v, int position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.activity_passtm_item, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (PassListFragment.getmBottomlayout().getVisibility() == View.GONE) {
			viewHolder.mNum_tv.setVisibility(View.VISIBLE);
			viewHolder.mPhone_iv.setVisibility(View.VISIBLE);
			viewHolder.mCanceltm_tv.setVisibility(View.GONE);
		} else if (PassListFragment.getmBottomlayout().getVisibility() == View.VISIBLE) {
			viewHolder.mNum_tv.setVisibility(View.GONE);
			viewHolder.mPhone_iv.setVisibility(View.GONE);
			viewHolder.mCanceltm_tv.setVisibility(View.VISIBLE);
		}
		ImageDownLoad.getDownLoadCircleImg(passTMList.get(position).getTddActivitySignVo().getSignHeadimgurl(), viewHolder.mHeadimg_iv);
		viewHolder.tuanda_iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.signlist_tm_bg));
		viewHolder.mNickname_tv.setText(passTMList.get(position).getTddActivitySignVo().getSignNickName());
		viewHolder.mTruename_tv.setText(passTMList.get(position).getTddActivitySignVo().getConnectUserName());
		// viewHolder.mNum_tv.setText((Integer.valueOf(signNoPassList.get(position).getKidNum())
		// + Integer
		// .valueOf(signNoPassList.get(position).getAdultNum())) + "");
		if (passTMList.get(position).getTddActivitySignVo().getTotalJoinNum() != 0) {
			viewHolder.mNum_tv.setText("+"+passTMList.get(position).getTddActivitySignVo().getTotalJoinNum());
		}else {
			viewHolder.mNum_tv.setVisibility(View.INVISIBLE);
		}
		
		// TODO 打电话
		if (passTMList != null) {
			viewHolder.mPhone_iv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Util.showCustomConfirmCancelDialog(context, "温馨提示", "是否拨打电话"+passTMList.get(position).getTddActivitySignVo().getSignUserPhone(), new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							CallPhoneUtil.callPhone(passTMList.get(position).getTddActivitySignVo().getSignUserPhone(), context);
						}});
				}
			});
		}
		// 取消团秘
		viewHolder.mCanceltm_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (iListener != null) {
					 iListener.onItemClick(v,position);
				}
//				ManagerApplyListActivity.allList.get(position).setAuditStatus("2");// 审核状态
//																					// 1.未审核2.已通过
//																					// 3.未通过
//				ManagerApplyListActivity.allList.get(position).setSignRole("9");// 报名用户角色
//																				// 1.团大2.团秘
//																				// 9.团员
//				passTMList.remove(position);
//				notifyDataSetChanged();
//				
////				Intent intent = new Intent();
////				intent.setAction("updatePassTYList");
////				context.sendBroadcast(intent);
			}
		});
		return convertView;
	}

//	private PassTYAdapter passTYAdapter;// 团员
//	private List<TddActivitySignVo> passTYList = new ArrayList<TddActivitySignVo>();// 团员
	
	
	class ViewHolder {
		private ImageView tuanda_iv;//团大||团秘 图片
		private RoundImageView mHeadimg_iv;// 头像
		private TextView mNickname_tv;// 昵称
		private TextView mTruename_tv;// 真实姓名
		private ImageView mPhone_iv;// 电话
		private TextView mNum_tv;// 随行人数
		private TextView mCanceltm_tv;// 取消团秘

		public ViewHolder(View convertView) {
			tuanda_iv = (ImageView) convertView.findViewById(R.id.tuanda_iv);
			mHeadimg_iv = (RoundImageView) convertView.findViewById(R.id.headimg_iv);
			mNickname_tv = (TextView) convertView.findViewById(R.id.nickname_tv);
			mTruename_tv = (TextView) convertView.findViewById(R.id.truename_tv);
			mPhone_iv = (ImageView) convertView.findViewById(R.id.phone_iv);
			mNum_tv = (TextView) convertView.findViewById(R.id.num_tv);
			mCanceltm_tv = (TextView) convertView.findViewById(R.id.canceltm_tv);
		}
	}

	public List<TddActivitySignVoInfo> getPassTYList() {
		return passTMList;
	}

	public void setPassTYList(List<TddActivitySignVoInfo> passTYList) {
		this.passTMList = passTYList;
	}

}
