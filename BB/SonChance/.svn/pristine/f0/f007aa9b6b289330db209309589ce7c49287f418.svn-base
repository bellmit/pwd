package com.tastebychance.sonchance.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
/**
 * 更改密码成功界面
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class ChangePwdSuccessActivity extends MyBaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepwdsuccess);
		
		setTitle();
	}
	
	public void reLogin(View view) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 设置标题
	 */
	private void setTitle() {
		ImageView left_btn = (ImageView) findViewById(R.id.head_left_iv);
		ImageButton right_btn = (ImageButton) findViewById(R.id.head_right_btn);
		TextView right_tv = (TextView) findViewById(R.id.head_right_tv);
		TextView center_tv = (TextView) findViewById(R.id.head_center_title_tv);
		if (center_tv != null)
			center_tv.setText("修改成功");
		if (left_btn != null) {
			left_btn.setVisibility(View.VISIBLE);
			left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
			left_btn.setOnClickListener(new android.view.View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					ChangePwdSuccessActivity.this.finish();
				}
			});
		}
		if (right_btn != null) {
			right_btn.setVisibility(View.GONE);
		}
		if (right_tv != null) {
			right_tv.setVisibility(View.GONE);
		}
	}

}
