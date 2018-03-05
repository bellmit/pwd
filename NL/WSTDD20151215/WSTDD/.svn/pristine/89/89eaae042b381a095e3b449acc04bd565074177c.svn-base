package com.newland.wstdd.find.hotlist;

import java.util.List;

import org.w3c.dom.Text;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.updownloadimg.ImageDownLoad;
import com.newland.wstdd.common.widget.PengTextView;
import com.newland.wstdd.find.categorylist.bean.FindCategoryListViewInfo;
import com.newland.wstdd.find.hotlist.bean.HotListInfo;

/**发现-热门列表适配器
 * @author H81 2015-11-19
 *
 */
public class HotListListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context context;
	private List<HotListInfo> findListViewDatas;// 数据

	public HotListListAdapter(Context context, List<HotListInfo> findListViewDatas) {
		this.context = context;
		this.findListViewDatas = findListViewDatas;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return findListViewDatas == null ? 0 : findListViewDatas.size();
	}

	@Override
	public HotListInfo getItem(int position) {
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

		HotListInfo hotListInfo = getItem(position);
		holder.find_hot_logoname_tv.setText(StringUtil.noNull(StringUtil.intType2Str(hotListInfo.getActivityType())));
		ImageDownLoad.getDownLoadImg(StringUtil.noNull(hotListInfo.getImgUrl()), holder.logoImageView);
		holder.hotTimeTextView.setText(StringUtil.noNull(hotListInfo.getFriendActivityTime()));
		holder.hotCityTextView.setText(StringUtil.noNull(hotListInfo.getActivityAddress()));
		holder.peopleTextView.setText(StringUtil.noNull(hotListInfo.getSignCount()));
		holder.titleTextView.setText(StringUtil.noNull(hotListInfo.getActivityTitle()));
		if ("0".equals(hotListInfo.getNeedPay())) {
			holder.isChargeImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.test11));
		} else {
			holder.isChargeImageView.setVisibility(View.GONE);
		}

		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public TextView find_hot_logoname_tv;// 类型
		public ImageView logoImageView;// 内容图片
		public TextView hotTimeTextView;// 热门标题
		public TextView hotCityTextView;// 热门城市
		public TextView peopleTextView;// 报名人数
		public TextView titleTextView;// 热门标题
		public ImageView isChargeImageView;// 是否收费
	}

	public List<HotListInfo> getHotFragmentListInfo() {
		return findListViewDatas;
	}

	public void setHotFragmentListInfo(List<HotListInfo> findListViewDatas) {
		this.findListViewDatas = findListViewDatas;
	}

}
