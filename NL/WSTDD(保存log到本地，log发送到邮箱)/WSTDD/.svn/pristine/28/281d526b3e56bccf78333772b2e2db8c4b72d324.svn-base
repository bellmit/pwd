package com.newland.wstdd.find.categorylist.registrationedit.registration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.R.integer;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
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
import com.newland.wstdd.common.tools.EditTextUtil;
import com.newland.wstdd.common.tools.UiHelper;

/**
 * 发现-listview 动态生成的 子适配器
 * 
 * @author H81 2015-11-6
 * 
 */
public class SxRegistrationSubmitChildAdapter extends BaseAdapter {
	private boolean isDataChange=false;//内容是否被改变了
	private LayoutInflater mInflater;
	private Context context;
	List<SxRegistrationSubmitAdapterData> registrationData;
	private int parentPosition;
	private int index = -1;
	private EditText tempeditText;
	// private List<String> registrationData.get(position).getMap();// 数据比如手机，地址，爱好，qq，性别，姓名等等
	
	public SxRegistrationSubmitChildAdapter(Context context,
			List<SxRegistrationSubmitAdapterData> registrationData,int positions) {
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
//		final ViewHolder holder;
		
//		if (convertView == null) {
	
			// 根据自定义的Item布局加载布局
			convertView = mInflater.inflate(R.layout.registration_edit_listview_childitem,parent, false);
//			holder = new ViewHolder();
			EditText	editText=(EditText) convertView.findViewById(R.id.registration_item_child_editext);
			TextView    textView=(TextView) convertView.findViewById(R.id.registration_item_child_textview);
			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
//			convertView.setTag(holder);
			
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
		if(tempeditText!=null){
			//newnumber这里为一个全局变量
			tempeditText.requestFocus();
	}
		
		//设置一些字符串格式    满足邮箱  手机  身份证等
		if("手机".equals(textView)){
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			
		}
		if("邮箱".equals(textView)){
			editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		}
		if("身份证".equals(textView)){
			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
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
		
		editText.addTextChangedListener(new TextWatcher() {

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
				if (position == index) {						
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
		if (index!=-1&&(index==position)) {
			editText.requestFocus();
			editText.setSelection(editText .getText().length());
			}
			// //重新定位的解决方案
			if (tempeditText!=null) {
				tempeditText.requestFocus();
			}
			Map<String, String> data = getItem(position);
			for (Entry<String, String> entry : data.entrySet()) {
				textView.setText(entry.getKey());
				editText.setText(entry.getValue());
				
			}
		return convertView;
	}

	// ViewHolder静态类
//	 class ViewHolder {
//		 EditText editText;// 动态生成
//		 TextView textView;//标签
//	}

	public List<SxRegistrationSubmitAdapterData> getRegistrationData() {
		return registrationData;
	}

	public void setRegistrationData(
			List<SxRegistrationSubmitAdapterData> registrationData) {
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
