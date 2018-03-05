package com.tastebychance.sonchance.loginandregister;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.util.StringUtil;
/**
 * 更改密码
 * @author shenbh
 * @date 2017/9/5
 *
 */
public class ChangePwdActivity extends MyBaseActivity {

	private EditText changepwd_originalpwd;
	private EditText changepwd_newpwd;
	private EditText changepwd_surepwd;
	private Button changepwd_save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepwd);

		bindViews();

		setTitle();
	}

	private void bindViews() {

		changepwd_originalpwd = (EditText) findViewById(R.id.changepwd_originalpwd);
		changepwd_newpwd = (EditText) findViewById(R.id.changepwd_newpwd);
		changepwd_surepwd = (EditText) findViewById(R.id.changepwd_surepwd);
		changepwd_save = (Button) findViewById(R.id.changepwd_save);

		changepwd_save.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				String original_pwd = changepwd_originalpwd.getText().toString();
				String new_pwd = changepwd_newpwd.getText().toString();
				String sure_pwd = changepwd_surepwd.getText().toString();
				if (StringUtil.isEmpty(original_pwd)) {
					Toast.makeText(ChangePwdActivity.this, "旧密码不能为空", 1000).show();
					return;
				}

				if (StringUtil.isEmpty(new_pwd)) {
					Toast.makeText(ChangePwdActivity.this, "新密码不能为空", 1000).show();

					return;
				} else if (StringUtil.isEmpty(sure_pwd)) {
					Toast.makeText(ChangePwdActivity.this, "确认新密码不能为空", 1000).show();
					return;
				}

				if (!new_pwd.equals(sure_pwd)) {
					Toast.makeText(ChangePwdActivity.this, "新密码不一致，请再次确认", 1000).show();
					return;
				}
				networkRequest();
			}
		});
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
			center_tv.setText("修改密码");
		if (left_btn != null) {
			left_btn.setVisibility(View.VISIBLE);
			left_btn.setImageDrawable(getResources().getDrawable(R.drawable.head_left));
			left_btn.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					ChangePwdActivity.this.finish();
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
	

	/**
	 * 请求网络修改密码
	 */
	@Override
	public void networkRequest() {
		/*
		String userid = SharedPreferencesUtils.getConfigStr(getApplication(), CASH_NAME, "userid");
		String userpwd = original_pwd_edit.getText().toString();
		dialog.setTvMessage("正在加载...");
		if (!isFinishing()) {dialog.show(true);}
		AjaxParams params = new AjaxParams();
		params.put("userid", userid);
		params.put("pwd", userpwd);
		params.put("pwd_new", new_pwd_edit.getText().toString());
		params.put("method", "edit_pwd");
		params.put("signid", MD5Utils.getInstance().getUserIdSignid(userid));
		System.out.println("params.toString()  =========   " + params.toString());
		FinalHttp fh = new FinalHttp();fh.configTimeout(HttpUtils.CONNECTION_TIMEOUT);;
		fh.post(HttpUtils.URL, params, new AjaxCallBack() {
			@Override
			public void onLoading(long count, long current) {
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {if(StringUtil.isNotEmpty(strMsg))strMsg=replaceErroStr(strMsg);
				strMsg="连接超时";Toast.makeText(getApplicationContext(), strMsg, 1000).show();
				if (ChangePwdActivity.this==null) {
					return;
				}
				dialog.dismiss();
			}

			@Override
			public void onSuccess(Object t) {
				if (ChangePwdActivity.this==null) {
					return;
				}
				dialog.dismiss();
				System.out.println(t.toString());
				JsonInfoV2 jsonInfov2 = null;
				try {
					jsonInfov2 = JSON.parseObject(t.toString(),JsonInfoV2.class);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "更新接口数据返回异常，请检查接口格式", 1000).show();
				}
				if (jsonInfov2 != null) {
					
					//兼容新接口
					if (JsonInfoV2.SUCCESS_CODE.equals(jsonInfov2.getResultCode())) // 正常返回数据
					{
						Toast.makeText(getApplicationContext(), "密码修改成功", 1000).show();
						System.out.println("修改成功================" + jsonInfov2.getResultDesc());
						finish();
					}
					 else {// 显示失败信息
							Toast.makeText(getApplicationContext(), jsonInfov2.getResultDesc(), 1000).show();
							System.out.println("修改失败================" + jsonInfov2.getResultDesc());
						}

				}
			}

		});
	*/
	}
}
