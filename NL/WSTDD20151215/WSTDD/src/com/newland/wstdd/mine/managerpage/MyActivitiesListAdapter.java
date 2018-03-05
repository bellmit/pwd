package com.newland.wstdd.mine.managerpage;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.updownloadimg.ImageDownLoad;
/**
 * 活动列表
 * @author Administrator
 *
 */
public class MyActivitiesListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context context;
	private List<MyActivityListInfo> myactivityListViewDatas;// 数据

	public MyActivitiesListAdapter(Context context, List<MyActivityListInfo> myactivityListViewDatas) {
		this.context = context;
		this.myactivityListViewDatas = myactivityListViewDatas;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return myactivityListViewDatas == null ? 0 : myactivityListViewDatas.size();
	}

	@Override
	public MyActivityListInfo getItem(int position) {
		if (myactivityListViewDatas != null && myactivityListViewDatas.size() != 0) {
			return myactivityListViewDatas.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// 根据自定义的Item布局加载布局
			convertView = mInflater.inflate(R.layout.find_category_listview_item, null);

			holder.find_hot_logoname_tv = (TextView) convertView.findViewById(R.id.find_hot_logoname_tv);
			holder.logoImageView = (ImageView) convertView.findViewById(R.id.find_hot_logo);
			holder.hotTimeTextView = (TextView) convertView.findViewById(R.id.find_hot_time);

			holder.hotCityTextView = (TextView) convertView.findViewById(R.id.find_hot_city);

			holder.titleTextView = (TextView) convertView.findViewById(R.id.find_hot_title);
			holder.peopleTextView = (TextView) convertView.findViewById(R.id.find_hot_people);
			holder.isChargeImageView = (ImageView) convertView.findViewById(R.id.find_hot_ischarge);
			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 先设置死的 后面根据屏幕大小进行设置
		// convertView.setLayoutParams(new GridView.LayoutParams(
		// /180, 100));

		convertView.setLayoutParams(new ListView.LayoutParams((int) (ListView.LayoutParams.FILL_PARENT), (int) (AppContext.getAppContext().getScreenHeight() / 6)));// 动态设置item的高度

		MyActivityListInfo myactivityListData = getItem(position);
		holder.find_hot_logoname_tv .setText(StringUtil.noNull(StringUtil.intType2Str(myactivityListData.getActivityType())));
		ImageDownLoad.getDownLoadSmallImg(StringUtil.noNull(myactivityListData.getImage1()), holder.logoImageView);
		holder.hotTimeTextView.setText(StringUtil.noNull(myactivityListData.getFriendActivityTime()));
		holder.hotCityTextView.setText(StringUtil.noNull(myactivityListData.getActivityAddress()));
		holder.peopleTextView.setText(StringUtil.noNull(myactivityListData.getSignCount()));
		holder.titleTextView.setText(StringUtil.noNull(myactivityListData.getActivityTitle()));
		if ("0".equals(myactivityListData.getNeedPay())) {
			holder.isChargeImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.test11));
		} else {
			holder.isChargeImageView.setVisibility(View.GONE);
		}
		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public TextView find_hot_logoname_tv;//类型
		public ImageView logoImageView;// 内容图片
		public TextView hotTimeTextView;// 热门标题
		public TextView hotCityTextView;// 热门城市
		public TextView peopleTextView;// 报名人数
		public TextView titleTextView;// 热门标题
		public ImageView isChargeImageView;// 是否收费
	}

	public List<MyActivityListInfo> getMyactivityListViewDatas() {
		return myactivityListViewDatas;
	}

	public void setMyactivityListViewDatas(
			List<MyActivityListInfo> myactivityListViewDatas) {
		this.myactivityListViewDatas = myactivityListViewDatas;
	}

	

}
