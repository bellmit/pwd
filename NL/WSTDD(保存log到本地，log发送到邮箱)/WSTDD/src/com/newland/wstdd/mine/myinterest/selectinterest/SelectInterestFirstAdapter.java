package com.newland.wstdd.mine.myinterest.selectinterest;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.newland.wstdd.R;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.mine.myinterest.beanrequest.InterestFirstItemInfo;
import com.newland.wstdd.mine.myinterest.selectinterest.SelectInterestGridView.OnChanageListener;

/**

 * 
 */
public class SelectInterestFirstAdapter extends BaseAdapter implements SelectInterestGridBaseAdapter  {
	private List<InterestFirstItemInfo> firstItemInfos;
	private LayoutInflater mInflater;
	private Context context;
	private ViewHolder holder;
	private boolean isShowDelete=false;// 是否可以删除
	public SelectInterestFirstAdapter(Context context,
			List<InterestFirstItemInfo> firstItemInfos) {
		this.firstItemInfos = firstItemInfos;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return firstItemInfos == null ? 0 : firstItemInfos.size();
	}

	@Override
	public InterestFirstItemInfo getItem(int position) {
		// TODO Auto-generated method stub
		if (firstItemInfos != null && firstItemInfos.size() != 0) {
			return firstItemInfos.get(position);
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
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.select_interest_items,
						parent, false);// 这里有区别？？？
				holder.typeName = (TextView) convertView
						.findViewById(R.id.select_interest_items_type);
				holder.gridView = (SelectInterestGridView) convertView.findViewById(R.id.select_interest_items_item);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final InterestFirstItemInfo interestItem = getItem(position);
			holder.typeName.setText(interestItem.getTypeName());
			final SelectInterestSecondAdapter secondAdapter=new SelectInterestSecondAdapter(context, firstItemInfos,position);
			secondAdapter.setIsShowDelete(isShowDelete);
			holder.gridView.setAdapter(secondAdapter);
			holder.gridView.setOnChangeListener(new OnChanageListener() {

				@Override
				public void onChange(int from, int to) {}

				@Override
				public void updatePosition(int from, int to) {
					// TODO Auto-generated method stub
					try {

						secondAdapter.notifyDataSetChanged();
						/**
						 * 切记对于gridview需要进行动态设置宽高  只有这样才会适配
						 */
						LayoutParams params = new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT);
						holder.gridView.setLayoutParams(params);
						holder.gridView.setStretchMode(GridView.NO_STRETCH);
						holder.gridView.setColumnWidth((int) (AppContext
								.getAppContext().getScreenWidth() / 4.5));
						holder.gridView.setHorizontalSpacing(AppContext
								.getAppContext().getScreenWidth() / 50);
						holder.gridView.setVerticalSpacing(AppContext
								.getAppContext().getScreenWidth() / 500);//高度设置要特别小心
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

			});

			return convertView;
		} catch (Exception e) {
			// TODO: handle exception

		}
		return null;

	}

	// ViewHolder静态类
	class ViewHolder {
		public TextView typeName;// 分类标签的名称
		public SelectInterestGridView gridView;// 动态添加分类中的兴趣标签
	}

	public List<InterestFirstItemInfo> getFirstItemInfos() {
		return firstItemInfos;
	}

	public void setFirstItemInfos(List<InterestFirstItemInfo> firstItemInfos) {
		this.firstItemInfos = firstItemInfos;
	}

	public boolean isShowDelete() {
		return isShowDelete;
	}

	public void setShowDelete(boolean isShowDelete) {
		this.isShowDelete = isShowDelete;
	}

	@Override
	public void reorderItems(int oldPosition, int newPosition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHideItem(int hidePosition) {
		// TODO Auto-generated method stub
		
	}

	
//	private void changeEditState(Boolean isEditState) {
//
//		// TODO Auto-generated method stub
//		// 设置可以编辑的状态
//		if (isEditState) {// 刪除
//
//			if (!isShowEdit) {
//				isShowEdit = true;
//				allGridView.setDrag(true);
//			}
//		} else {
//		
//			if (isShowEdit) {
//			
//				isShowEdit = false;
//				allGridView.setDrag(false);
//			} else {
//				interestName = "";
//
//			}
//		}
//		allAdapter.setShowDelete(isShowEdit);
//	}
}
