/**
 * @fields NoPassListFragment.java
 */
package com.newland.wstdd.mine.applyList;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragment;
import com.newland.wstdd.common.view.CustomListViews.IXListViewListener;
import com.newland.wstdd.mine.applyList.bean.TddActivitySignVoInfo;

/**
 * 未通过名单
 * 
 * @author H81 2015-11-28
 * 
 */
@SuppressLint("ValidFragment")
public class NoPassListFragment extends BaseFragment implements IXListViewListener, OnClickListener,OnCheckedChangeListener {
	final String FLAG = "right_tv_change";

	private TextView mNopass_tv;
	private BasePassListView mNopasslistview;
	private static LinearLayout bottomlayout;
//	private CheckBox allchose_checkbox;// 全选
	private TextView allchose_tv;//全选
	private TextView pass_tv;// 通过

	private ManagerApplyListActivity activity;
	private List<TddActivitySignVoInfo> signNoPassList = new ArrayList<TddActivitySignVoInfo>();// 未通过
	private NoPassAdapter noPassAdapter;

	IntentFilter intentFilter;

	private boolean isInit = false; 
	@SuppressLint("ValidFragment")
	public static NoPassListFragment newInstance(Context context) {
		return new NoPassListFragment(context);
	}

	@SuppressLint("ValidFragment")
	public NoPassListFragment(Context context) {
		activity = (ManagerApplyListActivity)context;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected View createAndInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		signNoPassList.clear();
		signNoPassList = (List<TddActivitySignVoInfo>) bundle.getSerializable("signNoPassList");

		View mView = inflater.inflate(R.layout.activity_applylist_nopass, container, false);
		bindViews(mView);
		intentFilter = new IntentFilter(FLAG);
		getActivity().registerReceiver(broadcastReceiver, intentFilter);
		refresh();
		return mView;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		intentFilter = new IntentFilter(FLAG);
		getActivity().registerReceiver(broadcastReceiver, intentFilter);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		getActivity().unregisterReceiver(broadcastReceiver);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		isInit = true;
	}

	private void bindViews(View view) {
		mNopass_tv = (TextView) view.findViewById(R.id.nopass_tv);
		mNopasslistview = (BasePassListView) view.findViewById(R.id.nopass_listview);
		bottomlayout = (LinearLayout) view.findViewById(R.id.bottomlayout);
//		allchose_checkbox = (CheckBox) view.findViewById(R.id.allchose_checkbox);
		allchose_tv = (TextView) view.findViewById(R.id.allchose_tv);
		pass_tv = (TextView) view.findViewById(R.id.pass_tv);
//		allchose_checkbox.setOnCheckedChangeListener(this);
		allchose_tv.setOnClickListener(this);
		pass_tv.setOnClickListener(this);
		
		
		
		mNopasslistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				CheckBox mCheckBox = (CheckBox) view.findViewById(R.id.checkBox);
				if (mCheckBox.isChecked()) {
					mCheckBox.setChecked(false);
				}else {
					mCheckBox.setChecked(true);
				}
				signNoPassList.get(position).setSelected(mCheckBox.isChecked());
			}
		});
	}

	@Override
	public void refresh() {
		if (getActivity() == null) // 说明上层activity 已经被销毁了
		{
			System.out.println("getActivity() 拦截");
			return;
		}
		signNoPassList=sortNoPassList(signNoPassList);
		noPassAdapter = new NoPassAdapter(getActivity(),signNoPassList);
		mNopasslistview.setAdapter(noPassAdapter);
		noPassAdapter.notifyDataSetChanged();
//		bottomlayout.setVisibility(View.GONE);
	}

	@Override
	public void onListViewRefresh() {

	}

	@Override
	public void onListViewLoadMore() {

	}

	// 刷新listview和TextView的显示
	private void dataChanged() {
		// 通知listView刷新
		noPassAdapter.notifyDataSetChanged();
	};

	public static LinearLayout getBottomlayout() {
		return bottomlayout;
	}

	public static void setBottomlayout(LinearLayout bottomlayout) {
		NoPassListFragment.bottomlayout = bottomlayout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pass_tv:// 通过
			for (int j = 0, size = ManagerApplyListActivity.allList.size(); j < size; j++) {
				for (int i = 0; i < signNoPassList.size(); i++) {
					boolean isSelected = signNoPassList.get(i).isSelected();
					System.out.println("isSelected----------------------------" + isSelected);
					if (isSelected) {
						if (signNoPassList.get(i).getTddActivitySignVo().getSignId().equals(ManagerApplyListActivity.allList.get(j).getTddActivitySignVo().getSignId())) {
							ManagerApplyListActivity.allList.get(j).getTddActivitySignVo().setAuditStatus(2);// 审核状态 1.未审核2.已通过 3.未通过
							ManagerApplyListActivity.allList.get(j).getTddActivitySignVo().setSignRole(9);
							signNoPassList.remove(i);
						}
					}
				}
			}
			
			activity.noPassNum = signNoPassList.size();
			activity.passNum = ManagerApplyListActivity.allList.size() - activity.noPassNum;
			if (activity.hasTD) {
				activity.passNum ++ ;
			}
			activity.setPassTextViewData(activity.passNum);
			activity.setNoPassTextViewData(activity.noPassNum);
			
			allchose_tv.setText("全选");
			
			noPassAdapter.notifyDataSetChanged();
			break;
		case R.id.allchose_tv://全选
			boolean isChecked = false;
			if ("全选".equals(allchose_tv.getText().toString())) {
				allchose_tv.setText("取消");
				isChecked = true;
			}else if("取消".equals(allchose_tv.getText().toString())){
				allchose_tv.setText("全选");
				isChecked = false;
			}
			for (int i = 0; i < signNoPassList.size(); i++) {
//				NoPassAdapter.getIsSelected().put(i, isChecked);
				signNoPassList.get(i).setSelected(isChecked);
			}
			// 刷新listview和TextView的显示
			dataChanged();
			break;
		default:
			break;
		}
	}


	/**
	 * 全选
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		for (int i = 0; i < signNoPassList.size(); i++) {
			NoPassAdapter.getIsSelected().put(i, isChecked);
		}
		// 刷新listview和TextView的显示
		dataChanged();
	}

	/**
	 * 接收到广播之后
	 */
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			TextView rightTv = (TextView) getActivity().findViewById(R.id.head_right_tv);
			if ("编辑".equals(rightTv.getText().toString())) {
				bottomlayout.setVisibility(View.GONE);
			} else if ("完成".equals(rightTv.getText().toString())) {
				bottomlayout.setVisibility(View.VISIBLE);
			}
			noPassAdapter.notifyDataSetChanged();
		}
	};

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser&& isInit) {
			signNoPassList.clear();
			for (int i = 0,size = ManagerApplyListActivity.allList.size(); i < size; i++) {
				if (ManagerApplyListActivity.allList.get(i).getTddActivitySignVo().getAuditStatus()==1||ManagerApplyListActivity.allList.get(i).getTddActivitySignVo().getAuditStatus()==3) {
					ManagerApplyListActivity.allList.get(i).setSelected(false);
					signNoPassList.add(ManagerApplyListActivity.allList.get(i));
				}
			}
//			allchose_checkbox.setChecked(false);
			signNoPassList=sortNoPassList(signNoPassList);
			noPassAdapter = new NoPassAdapter(getActivity(),signNoPassList);
			mNopasslistview.setAdapter(noPassAdapter);
			noPassAdapter.notifyDataSetChanged();
		}
	}

	/**对未通过的名单进行排序（按先“未审核”后“已拒绝”排序）
	 * 
	 * auditStatus审核状态  1.未审核  2.已通过  3.未通过	int
	 * @param tddActivitySignVoInfos
	 * @return
	 */
	private List<TddActivitySignVoInfo> sortNoPassList(List<TddActivitySignVoInfo> tddActivitySignVoInfos) {
		List<TddActivitySignVoInfo> sortedList = new ArrayList<TddActivitySignVoInfo>();
		List<TddActivitySignVoInfo> saveUnCheckList = new ArrayList<TddActivitySignVoInfo>();// 保存未审核
		List<TddActivitySignVoInfo> saveRejectList = new ArrayList<TddActivitySignVoInfo>();// 保存已拒绝
		for (int i = 0, size = tddActivitySignVoInfos.size(); i < size; i++) {
			if (tddActivitySignVoInfos.get(i).getTddActivitySignVo().getAuditStatus() == 1) {
				saveUnCheckList.add(tddActivitySignVoInfos.get(i));
			} else if (tddActivitySignVoInfos.get(i).getTddActivitySignVo().getAuditStatus() == 3) {
				saveRejectList.add(tddActivitySignVoInfos.get(i));
			}
		}
		for (int i = 0, size = saveUnCheckList.size(); i < size; i++) {
			sortedList.add(saveUnCheckList.get(i));
		}
		for (int i = 0, size = saveRejectList.size(); i < size; i++) {
			sortedList.add(saveRejectList.get(i));
		}
		return sortedList;
	}

}