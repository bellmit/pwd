package com.newland.wstdd.mine.managerpage;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragment;

/**
 * 我的活动-我发起-列表
 * 
 * @author Administrator
 * 
 */
public class MyActivitiesOriginateListFragment extends BaseFragment implements OnItemClickListener {

	private View mView;
	MyActivitiesOriginateListFragmentAdapter activitiesOriginateListFragmentAdapter;
	Context context;
	ListView listView;
	List<String> list;

	private View no_data_layout;// 无数据布局

	public MyActivitiesOriginateListFragment(Context context) {
		this.context = context;

	}

	public static MyActivitiesOriginateListFragment newInstance(Context context) {
		return new MyActivitiesOriginateListFragment(context);
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	protected View createAndInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		list = new ArrayList<String>();
		list.add("哈哈");
		list.add("哈哈");
		// mView = inflater.inflate(R.layout.activity_myjoinactivity, container,
		// false);
		mView = inflater.inflate(R.layout.activity_myactivity_list, container, false);
		listView = (ListView) mView.findViewById(R.id.fragment_myactivities_originate_list);
		activitiesOriginateListFragmentAdapter = new MyActivitiesOriginateListFragmentAdapter(context, list);
		listView.setAdapter(activitiesOriginateListFragmentAdapter);
		listView.setOnItemClickListener(this);
		refresh();
		return mView;
	}

	@Override
	public void refresh() {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg0.getId() == R.id.fragment_myactivities_originate_list) {
			Intent intent = new Intent(getActivity(), ManagerPageActivity.class);
			startActivity(intent);
		}

	}
}
