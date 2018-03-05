package com.newland.wstdd.originate.origateactivity;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.newland.wstdd.R;
import com.newland.wstdd.R.color;
import com.newland.wstdd.common.selectphoto.PhotoUpImageItem;
import com.newland.wstdd.originate.beanrequest.SelectMustItemInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
/**
 * 活动的时候必填项
 * @author Administrator
 *
 */
public class SelectedMustAdapter extends BaseAdapter {

	private ArrayList<SelectMustItemInfo> arrayList;
	private LayoutInflater layoutInflater;
	private Context context;
	public SelectedMustAdapter(Context context,ArrayList<SelectMustItemInfo> arrayList){
		this.arrayList = arrayList;
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
	
	}
	@Override
	public int getCount() {
		return arrayList==null ? 0 :arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		if (arrayList != null && arrayList.size() != 0) {
		return arrayList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.selected_must_adapter_item, parent, false);
			holder = new Holder();
			holder.button = (TextView) convertView.findViewById(R.id.selected_must_item);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		if(arrayList.get(position).getSelectItem()==null){
			holder.button.setText("+");
		}else {
			holder.button.setText(arrayList.get(position).getSelectItem());
		}
		if(arrayList.get(position).isSelect()){
			holder.button.setBackground(context.getResources().getDrawable(R.drawable.text_originate_chair_mustselect_style));
			holder.button.setTextColor(context.getResources().getColor(R.color.text_red));;
			
		}else{
			holder.button.setTextColor(context.getResources().getColor(R.color.black));;
			holder.button.setBackground(context.getResources().getDrawable(R.drawable.text_originate_chair_mustnormal_style));
		}
		return convertView;
	}
	class Holder{
		TextView button;
	}
	public ArrayList<SelectMustItemInfo> getArrayList() {
		return arrayList;
	}
	public void setArrayList(ArrayList<SelectMustItemInfo> arrayList) {
		this.arrayList = arrayList;
	}
	
}
