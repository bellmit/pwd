package com.newland.wstdd.find.categorylist.registrationedit.tests;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.find.categorylist.registrationedit.registration.SxRegistrationEditListViews;

public class CustomImageButton extends RelativeLayout {
	public TextView name;// 姓名
	public TextView phone;// 电话
	public TextView delete;// 删除
	public TextView edit;// 编辑
	public TextView childTextView;
	public EditText chileEditText;
	// 需要进行显示隐藏的
	public RelativeLayout rl1, rl2;// 需要进行显示隐藏的布局控件
	public LinearLayout ll1;// 子元素

	public CustomImageButton(Context context) {
		super(context);
	}

	public CustomImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.custom_image_button, this);

		name = (TextView) findViewById(R.id.registration_edit_sxtop_name);
		phone = (TextView) findViewById(R.id.registration_edit_sxtop_phone);
		delete = (TextView) findViewById(R.id.registration_edit_sxtop_name);
		edit = (TextView) findViewById(R.id.registration_edit_sxtop_phone);
		rl1 = (RelativeLayout) findViewById(R.id.registration_edit_listview_item_toprl1);
		rl2 = (RelativeLayout) findViewById(R.id.registration_edit_listview_item_toprl2);
		ll1 = (LinearLayout) findViewById(R.id.registration_edit_listview_item_childll1);
		childTextView = (TextView) findViewById(R.id.registration_item_child_textview);
		chileEditText = (EditText) findViewById(R.id.registration_item_child_editext);
	}

	// 设置显示的名字
	public void setName(String text) {
		name.setText(text);
	}

	// 设置显示的手机
	public void setPhone(String text) {
		phone.setText(text);
	}

	// 子元素的设置显示的名字
	public void setText(String text) {
		childTextView.setText(text);
	}

	// 设置显示的文字
	public void setEdit(String text) {
		chileEditText.setText(text);
	}

	// rl1显示隐藏的控件
	public void setRl1Gone(boolean bool) {
		if (bool) {
			rl1.setVisibility(GONE);
		} else {
			rl1.setVisibility(VISIBLE);
		}
	}

	// rl2显示隐藏的控件
	public void setRl2Gone(boolean bool) {
		if (bool) {
			rl2.setVisibility(GONE);
		} else {
			rl2.setVisibility(VISIBLE);
		}
	}

	// ll1显示隐藏的控件
	public void setLl1Gone(boolean bool) {
		if (bool) {
			ll1.setVisibility(GONE);
		} else {
			ll1.setVisibility(VISIBLE);
		}
	}
}
