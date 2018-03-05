package com.newland.wstdd.mine.myinterest.selectinterest;

import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.mine.myinterest.DragGridBaseAdapter;
import com.newland.wstdd.mine.myinterest.MyInterestActivity;
import com.newland.wstdd.mine.myinterest.beanrequest.InterestFirstItemInfo;
import com.newland.wstdd.mine.myinterest.beanrequest.InterestSecondItemInfo;

/**

 * 
 */
public class SelectInterestSecondAdapter extends BaseAdapter implements SelectInterestGridBaseAdapter {
	private List<InterestFirstItemInfo> interestItemInfos;//一级列表的数据。直接传过来
	private List<InterestSecondItemInfo> pluInfoList;//这里才是二级列表的数据
	private LayoutInflater mInflater;
	private int mHidePosition = -1;
	
	private Context context;
	private boolean isShowDelete=false;// 是否可以删除
	private int parentPosition;//一级控件传过来的值
	/** 控制的postion */
	private int holdPosition;
	/** 是否改变 */
	private boolean isChanged = false;

	public SelectInterestSecondAdapter(Context context, List<InterestFirstItemInfo> interestItemInfos,int parentPosition) {
		this.interestItemInfos = interestItemInfos;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.parentPosition=parentPosition;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return interestItemInfos.get(parentPosition).getItemsList() == null ? 0 : interestItemInfos.get(parentPosition).getItemsList().size();
	}

	@Override
	public InterestSecondItemInfo getItem(int position) {
		// TODO Auto-generated method stub
		if (interestItemInfos.get(parentPosition).getItemsList() != null && interestItemInfos.get(parentPosition).getItemsList().size() != 0) {
			return interestItemInfos.get(parentPosition).getItemsList().get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * 由于复用convertView导致某些item消失了，所以这里不复用item，
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		try {

		convertView = mInflater.inflate(R.layout.select_interest_item, parent, false);// 这里有区别？？？	
		FrameLayout fl = (FrameLayout) convertView.findViewById(R.id.fl);
		TextView interestName = (TextView) convertView
				.findViewById(R.id.interest_name);
		final ImageView selectIcon = (ImageView) convertView
				.findViewById(R.id.interest_select_icon);
		final InterestSecondItemInfo interestItem = getItem(position);
		interestName.setText(interestItem.getInterestName());	
		if(interestItem.isSelect()){
			selectIcon.setVisibility(View.VISIBLE);
		}else{
			selectIcon.setVisibility(View.INVISIBLE);
		}
//		selectIcon.setVisibility(isShowDelete ? View.VISIBLE : View.INVISIBLE);// 设置删除按钮是否显示
//		if(isShowDelete){
			fl.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
						if(selectIcon.getVisibility()==View.INVISIBLE||selectIcon.getVisibility()==View.GONE){
						selectIcon.setVisibility(View.VISIBLE);
						interestItem.setSelect(true);
						
						}
						else {
							selectIcon.setVisibility(View.INVISIBLE);
							interestItem.setSelect(false);
						}
						interestItemInfos.get(parentPosition).getItemsList().set(position, interestItem);
						SelectInterestSecondAdapter.this.notifyDataSetChanged();
					
				}
			});
			fl.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					return true;
				}
			});
//	}	
		//这里是设置每个gridview分的高度   但是记住可不是item.xml中的高度
//		convertView.setLayoutParams(new GridView.LayoutParams((int) (AppContext.getAppContext().getScreenWidth()/4-10 ) , (int)(AppContext.getAppContext().getScreenHeight()/15)));// 动态设置item的高度		
		return convertView;
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		return null;
		
	}



	// 标记删除item
	public void setIsShowDelete(boolean isShowDelete) {
		this.isShowDelete = isShowDelete;
		notifyDataSetChanged();
	}

	

	/** 设置列表 */
	public void setListDate(List<InterestSecondItemInfo> list) {
		pluInfoList = list;
	}

	@Override
	public void reorderItems(int oldPosition, int newPosition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHideItem(int hidePosition) {
		// TODO Auto-generated method stub
		
	}
}
