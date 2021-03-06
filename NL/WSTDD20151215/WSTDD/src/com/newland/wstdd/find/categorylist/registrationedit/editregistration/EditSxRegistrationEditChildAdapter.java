package com.newland.wstdd.find.categorylist.registrationedit.editregistration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.R.integer;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.tools.UiHelper;

/**
 * 发现-listview 动态生成的 子适配器
 * 
 * @author H81 2015-11-6
 * 
 */
public class EditSxRegistrationEditChildAdapter extends BaseAdapter {
	private boolean isDataChange=false;//内容是否被改变了
	private LayoutInflater mInflater;
	private Context context;
	List<EditSxRegistrationEditAdapterData> registrationData;
	private int parentPosition;
	private int focusPosition = -1;
	// private List<String> registrationData.get(position).getMap();// 数据比如手机，地址，爱好，qq，性别，姓名等等
	
	public EditSxRegistrationEditChildAdapter(Context context,
			List<EditSxRegistrationEditAdapterData> registrationData,int positions) {
		this.context = context;
		this.registrationData = registrationData;
		this.parentPosition=positions;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return registrationData.get(parentPosition).getMap() == null ? 0 : registrationData.get(parentPosition).getMap().size();
	}

	@Override
	public Map<String, String> getItem(int position) {
		if (registrationData.get(parentPosition).getMap() != null && registrationData.get(parentPosition).getMap().size() != 0) {
			return registrationData.get(parentPosition).getMap().get(position);
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
			convertView = mInflater.inflate(R.layout.registration_edit_listview_childitem,parent, false);
			holder = new ViewHolder();
			holder.editText=(EditText) convertView.findViewById(R.id.registration_item_child_editext);
			holder.textView=(TextView) convertView.findViewById(R.id.registration_item_child_textview);
			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Map<String, String> data = getItem(position);
		for (Entry<String, String> entry : data.entrySet()) {
			holder.textView.setText(entry.getKey());
			holder.editText.setText(entry.getValue());
			holder.editText.requestFocus();
			
		}
		holder.editText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					focusPosition = position;
				} else {
					focusPosition = -1;
				}
			}
		});
		holder.editText.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.requestFocus();
				}
				return false;
			}
		});
		holder.editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (position == focusPosition) {					
					Map<String, String> inputTemp = registrationData.get(parentPosition).getInputTempList().get(position);
					for (Entry<String, String> entry : inputTemp.entrySet()) {
						inputTemp.put(entry.getKey(), s.toString());
					}

					Map<String, String> value = getItem(position);
					for (Entry<String, String> entry : value.entrySet()) {
						value.put(entry.getKey(), s.toString());
					}
				}
			}
		});
		
		return convertView;
	}

	// ViewHolder静态类
	 class ViewHolder {
		 EditText editText;// 动态生成
		 TextView textView;//标签
	}

	public List<EditSxRegistrationEditAdapterData> getRegistrationData() {
		return registrationData;
	}

	public void setRegistrationData(
			List<EditSxRegistrationEditAdapterData> registrationData) {
		this.registrationData = registrationData;
		
	}

	public boolean isDataChange() {
		return isDataChange;
	}

	public void setDataChange(boolean isDataChange) {
		this.isDataChange = isDataChange;
	}

	public int getParentPosition() {
		return parentPosition;
	}

	public void setParentPosition(int parentPosition) {
		this.parentPosition = parentPosition;
	}


	

	
	
}
