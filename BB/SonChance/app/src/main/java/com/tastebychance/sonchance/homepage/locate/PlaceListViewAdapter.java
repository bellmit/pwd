package com.tastebychance.sonchance.homepage.locate;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.homepage.locate.bean.CityInfo;
/**
 * 地址列表
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class PlaceListViewAdapter extends BaseAdapter {
	private Context context;
	private List<CityInfo> list;

	public PlaceListViewAdapter(Context context, List<CityInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 *
	 * @param list
	 */
	public void updateListView(List<CityInfo> list){
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.home_placechoose_list_item, null);
		}

		ViewHolder holder = ViewHolder.getHolder(convertView);
		// 设置数据
		CityInfo cityInfo = list.get(position);
		holder.name.setText(cityInfo.getCityName());
		String currentWord = cityInfo.getCityFirstWord();
		
		if(position>0){
			//获取上一个item的首字母
			String lastWord = list.get(position - 1).getCityFirstWord() ;
			//拿当前的首字母和上一个首字母比较
			if(currentWord.equals(lastWord)){
				//说明首字母相同，需要隐藏当前item的first_word
				holder.first_word.setVisibility(View.GONE);
//				holder.first_word_border.setVisibility(View.GONE);
			}else{
				//不一样，需要显示当前的首字母
				//由于布局是复用的，所以在需要显示的时候，再次将first_word设置为可见
				holder.first_word.setVisibility(View.VISIBLE);
//				holder.first_word_border.setVisibility(View.VISIBLE);
				holder.first_word.setText(currentWord);
			}
		}else{
			holder.first_word.setVisibility(View.VISIBLE);
//			holder.first_word_border.setVisibility(View.VISIBLE);
			holder.first_word.setText(currentWord);
		}
		
		
		
		// 设置数据
/*		QQ qq = list.rows.get(position);
		holder.name.setText(qq.prodName);
		String currentWord = qq.initChar+"";*/
		
		
		/*if(position>0){
			//获取上一个item的首字母
			String lastWord = list.rows.get(position-1).initChar+"";
			//拿当前的首字母和上一个首字母比较
			if(currentWord.equals(lastWord)){
				//说明首字母相同，需要隐藏当前item的first_word
				holder.first_word.setVisibility(View.GONE);
				holder.first_word_border.setVisibility(View.GONE);
			}else {
				//不一样，需要显示当前的首字母
				//由于布局是复用的，所以在需要显示的时候，再次将first_word设置为可见
				holder.first_word.setVisibility(View.VISIBLE);
				holder.first_word_border.setVisibility(View.VISIBLE);
				holder.first_word.setText(currentWord);
			}
		}else {
			holder.first_word.setVisibility(View.VISIBLE);
			holder.first_word_border.setVisibility(View.VISIBLE);
			holder.first_word.setText(currentWord);
		}*/
		return convertView;
	}

	static class ViewHolder {
		// 所有控件对象引用
		public TextView first_word, name;
//		public View first_word_border;

		public ViewHolder(View convertView) {
			first_word = (TextView) convertView.findViewById(R.id.qqservice_first_word_tv);	
//			first_word_border= convertView.findViewById(R.id.qqservice_first_word_view);
			name = (TextView) convertView.findViewById(R.id.qqservice_item_name_tv);
		}
		public static ViewHolder getHolder(View convertView) {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			if (holder == null) {
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			return holder;
		}
	}
}
