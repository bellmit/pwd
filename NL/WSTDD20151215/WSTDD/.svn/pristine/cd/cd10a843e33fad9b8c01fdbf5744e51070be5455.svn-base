package com.newland.wstdd.find.categorylist.registrationedit.registration;

import java.util.List;

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
import com.newland.wstdd.find.categorylist.registrationedit.beaninfo.MainSignAttr;

/**
 * 发现-listview 动态生成的 子适配器
 * 
 * @author H81 2015-11-6
 * 
 */
public class RegistrationSubmitAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context context;
	List<MainSignAttr> registrationData;
	private int index = -1;
	private EditText tempeditText;

	public RegistrationSubmitAdapter(Context context, List<MainSignAttr> registrationData) {
		this.context = context;
		this.registrationData = registrationData;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return registrationData == null ? 0 : registrationData.size();
	}

	@Override
	public MainSignAttr getItem(int position) {
		if (registrationData.get(position) != null && registrationData.size() != 0) {
			return registrationData.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		 final ViewHolder holder;
		 EditText editText;// 动态生成
			TextView textView;// 标签
//		if (convertView == null) {

			// 根据自定义的Item布局加载布局
			convertView = mInflater.inflate(R.layout.registration_edit_listview_childitem, parent, false);
			holder = new ViewHolder();
			editText = (EditText) convertView.findViewById(R.id.registration_item_child_editext);
			textView = (TextView) convertView.findViewById(R.id.registration_item_child_textview);

			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);

//		} else {
//			holder = (ViewHolder) convertView.getTag();
			if(tempeditText!=null){
				//newnumber这里为一个全局变量
				tempeditText.requestFocus();
//			}
		}
		
		editText.setTag(position+"");//这里不多解释了，自己看吧
		editText.setOnTouchListener(new OnTouchListener() {


			public boolean onTouch(View view, MotionEvent event) {


			// 在TOUCH的UP事件中，要保存当前的行下标，因为弹出软键盘后，整个画面会被重画


			// 在getView方法的最后，要根据index和当前的行下标手动为EditText设置焦点


			if (event.getAction() == MotionEvent.ACTION_UP) {


			//index = Integer.parseInt(number.getTag().toString());//这个是错误的,更不要写成index = postion；那你就是2，我承认我2过了
			index = Integer.parseInt(view.getTag().toString());//这里一定要注意view.getTag().toString()是上面onTouch(View view, MotionEvent event)传进来的，不然是会有错乱的 这也就是我说的上面地址的错误所在，为了这个我白花了三个小时
			tempeditText = (EditText) view;//这里也最好这样去获取，如果直接number =newnumber ；应该也是可以的，但是个人觉得这样更合理，原因就是上面的一句


			}


			return false;


			}


			});
		
		
		
		MainSignAttr data = getItem(position);

		

//		editText.setOnFocusChangeListener(new OnFocusChangeListener() {

//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				if (hasFocus) {
//					index = position;
//					editText.requestFocus();
//				} else {
//					index = -1;
//				}
//			}
//		});
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (position == index) {
					if (s != null && !"".equals(s.toString())) {
						getItem(position).setValue(s.toString());
						registrationData.get(position).setValue(s.toString());
//						notifyDataSetChanged();
					}

				}
			}
		});
	
		if (index!=-1&&(index==position)) {
			editText.requestFocus();
			editText.setSelection(editText .getText().length());
			}
			// //重新定位的解决方案
			if (tempeditText!=null) {
				tempeditText.requestFocus();
			}
			textView.setText(data.getName());
			editText.setText(data.getValue());
		return convertView;
	}

	// ViewHolder静态类
	class ViewHolder {
		EditText editText;// 动态生成
		TextView textView;// 标签
	}

	public List<MainSignAttr> getRegistrationData() {
		return registrationData;
	}

	public void setRegistrationData(List<MainSignAttr> registrationData) {
		this.registrationData = registrationData;
	}

}
