package com.newland.wstdd.originate.search;

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
import com.newland.wstdd.find.categorylist.bean.FindCategoryListViewInfo;

/**搜索结果适配器
 * @author Administrator
 *
 */
public class OriginateSearchResultAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context context;
	private List<FindCategoryListViewInfo> findListViewDatas;// 数据

	public OriginateSearchResultAdapter(Context context, List<FindCategoryListViewInfo> findListViewDatas) {
		this.context = context;
		this.findListViewDatas = findListViewDatas;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return findListViewDatas == null ? 0 : findListViewDatas.size();
	}

	@Override
	public FindCategoryListViewInfo getItem(int position) {
		if (findListViewDatas != null && findListViewDatas.size() != 0) {
			return findListViewDatas.get(position);
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

		FindCategoryListViewInfo findFragmentListViewData = getItem(position);
		holder.find_hot_logoname_tv .setText(StringUtil.noNull(StringUtil.intType2Str(findFragmentListViewData.getActivityType())));
		ImageDownLoad.getDownLoadSmallImg(StringUtil.noNull(findFragmentListViewData.getImage1()), holder.logoImageView);
		holder.hotTimeTextView.setText(StringUtil.noNull(findFragmentListViewData.getFriendActivityTime()));
		holder.hotCityTextView.setText(StringUtil.noNull(findFragmentListViewData.getActivityAddress()));
		holder.peopleTextView.setText(StringUtil.noNull(findFragmentListViewData.getSignCount()));
		holder.titleTextView.setText(StringUtil.noNull(findFragmentListViewData.getActivityTitle()));
		if ("0".equals(findFragmentListViewData.getNeedPay())) {
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

	public List<FindCategoryListViewInfo> getHotFragmentListInfo() {
		return findListViewDatas;
	}

	public void setHotFragmentListInfo(List<FindCategoryListViewInfo> findListViewDatas) {
		this.findListViewDatas = findListViewDatas;
	}
}