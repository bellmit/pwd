package com.tastebychance.sonchance.loginandregister;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.UrlManager;

/**
 * 注册界面
 * 
 * @author shenbh
 *
 *         2017年8月9日
 */
public class RegisterActivity extends MyBaseActivity {

	private EditText register_phoneno;
	private EditText register_pwd;
	private EditText register_confirmpwd;
	private EditText register_verifycode;
	private CheckBox register_readcheckbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		bindViews();

	}

	private void bindViews() {

		register_phoneno = (EditText) findViewById(R.id.register_phoneno);
		register_pwd = (EditText) findViewById(R.id.register_pwd);
		register_confirmpwd = (EditText) findViewById(R.id.register_confirmpwd);
		register_verifycode = (EditText) findViewById(R.id.register_verifycode);
		register_readcheckbox = (CheckBox) findViewById(R.id.register_readcheckbox);
	}

	/**
	 * 点击“发送验证码”
	 * 
	 * @param view
	 */
	public void getVerifycode(View view) {
		String phoneno = register_phoneno.getText().toString();
		String pwd = register_pwd.getText().toString();
		String confirmPwd = register_confirmpwd.getText().toString();

		if (StringUtil.isEmpty(phoneno)) {
			Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if (StringUtil.isEmpty(pwd)) {
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}
		if (StringUtil.isEmpty(confirmPwd)) {
			Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_LONG).show();
			return;
		}

		// 校验手机号码
		if (!StringUtil.isMobileNO(phoneno)) {
			Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_LONG).show();
			return;
		}

		// 校验密码
		if (!pwd.equals(confirmPwd)) {
			Toast.makeText(this, "两次密码不一致", Toast.LENGTH_LONG).show();
			return;
		}
		// TODO:获取验证码

	}

	/**
	 * 阅读客户需知
	 * 
	 * @param view
	 */
	public void readKHXZ(View view) {
		Intent intent = new Intent(this, KHXZActivity.class);
		startActivity(intent);
	}

	/**
	 * 取消
	 * 
	 * @param view
	 */
	public void cancel(View view) {
		this.finish();
	}

	/**
	 * 注册
	 * 
	 * @param view
	 */
	public void register(View view) {
		networkRequest();
	}
	
	@Override
	public void networkRequest() {
		super.networkRequest();


		String url =  UrlManager.URL_REGISTER;
		OkHttpClient mOkHttpClient = new OkHttpClient();
		RequestBody formBody = new FormBody.Builder().add("user_name", "name")
				.add("mobile", register_phoneno.getText().toString())
				.add("password", register_pwd.getText().toString())
				.add("verify", register_verifycode.getText().toString()).build();
		Request request = new Request.Builder().url(url).post(formBody).build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String str = response.body().string();
				Log.i("wangshu", str);

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
					}
				});
			}

		});

	
	}
}
