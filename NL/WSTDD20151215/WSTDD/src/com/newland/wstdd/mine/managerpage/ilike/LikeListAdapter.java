package com.newland.wstdd.mine.managerpage.ilike;

import java.util.List;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.bean.TddLikeVo;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.updownloadimg.ImageDownLoad;

/**
 * 发现-listview 动态生成的 子适配器
 * 
 * @author H81 2015-11-6
 * 
 */
public class LikeListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context context;
	List<TddLikeVo> tddLikeVos;
	public LikeListAdapter(Context context,
			List<TddLikeVo> tddLikeVos) {
		this.context = context;
		this.tddLikeVos = tddLikeVos;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return tddLikeVos == null ? 0 : tddLikeVos.size();
	}

	@Override
	public TddLikeVo getItem(int position) {
		if (tddLikeVos.get(position) != null && tddLikeVos.size() != 0) {
			return tddLikeVos.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		if (convertView == null) {
	
			// 根据自定义的Item布局加载布局
			convertView = mInflater.inflate(R.layout.listview_like_list_item,parent, false);
			holder = new ViewHolder();
			holder.headIconIv=(ImageView) convertView.findViewById(R.id.listview_like_list_item_headicon);
			holder.nickNameTv=(TextView) convertView.findViewById(R.id.listview_like_list_item_nickname);
			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		    TddLikeVo data = getItem(position);
		    if(data!=null){
			holder.nickNameTv.setText(data.getNickname());
			ImageDownLoad.getDownLoadCircleImg(data.getHeadimgurl(), holder.headIconIv);
		    }

		   return convertView;
	}

	// ViewHolder静态类
	 class ViewHolder {
		 ImageView headIconIv;// 动态生成
		 TextView nickNameTv;//标签
	}

	public List<TddLikeVo> getRegistrationData() {
		return tddLikeVos;
	}

	public void setRegistrationData(List<TddLikeVo> tddLikeVos) {
		this.tddLikeVos = tddLikeVos;
	}

	
	 


	
	
}
