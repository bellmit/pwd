package com.tastebychance.sonchance.loginandregister;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.ResInfo;
import com.tastebychance.sonchance.loginandregister.view.IdentifyingCodeView;
import com.tastebychance.sonchance.personal.bean.UserInfo;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.CountDownTimerUtil;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;
import com.tastebychance.sonchance.util.TextClickable;
import com.tastebychance.sonchance.util.UiHelper;
import com.tastebychance.sonchance.util.UrlManager;

/**
 * 登陆界面
 *
 * @author shenbh
 *
 *         2017年8月9日
 */
public class LoginActivity extends MyBaseActivity {
	private RelativeLayout root_layout;

	private ImageView login_logo_iv;
	private ImageView login_close_iv;
	private ImageView weixinLogin;
	private ImageView qqLogin;
	private ImageView weiboLogin;
	private TextView login_prompt;//提示
	private EditText login_phone_edt;
	private View login_phone_underline;
	private Button login_next_btn;

	private TextView login_phone_countdown_tv;//倒计时
	private IdentifyingCodeView login_verifycode_ll;//验证码输入框布局
	private ImageView login_success_iv ;

	private TextView login_error_tv;//验证码错误提示

	private float touchX = -1,touchY = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		Intent intent = getIntent();
		if(null != intent){
			toActivity = intent.getStringExtra("toActivity");
		}

		bindViews();
	}

	private void bindViews() {
		root_layout =(RelativeLayout) findViewById(R.id.root_layout);
		login_logo_iv = (ImageView) findViewById(R.id.login_logo_iv);
		login_close_iv = (ImageView) findViewById(R.id.login_close_iv);
		weixinLogin = (ImageView) findViewById(R.id.weixinLogin);
		qqLogin = (ImageView) findViewById(R.id.qqLogin);
		weiboLogin = (ImageView) findViewById(R.id.weiboLogin);
		login_phone_edt = (EditText) findViewById(R.id.login_phone_edt);
		login_phone_underline = (View) findViewById(R.id.login_phone_underline);
		login_next_btn = (Button) findViewById(R.id.login_next_btn);
		login_prompt = (TextView) findViewById(R.id.login_prompt);
		login_success_iv = (ImageView) LoginActivity.this.findViewById(R.id.login_success_iv);
		login_phone_countdown_tv = (TextView) findViewById(R.id.login_phone_countdown_tv);
		login_verifycode_ll = (IdentifyingCodeView) findViewById(R.id.login_verifycode_ll);
		login_error_tv = (TextView) findViewById(R.id.login_error_tv);
		login_error_tv.setText("");
		login_error_tv.setBackgroundColor(Color.WHITE);
		//客户需知
		String khxzStr = "温馨提示：未注册膳闯账号的手机号，登录时\n将自动注册，且代表您已同意《客户需知》";
		SpannableString spannableInfo = new SpannableString(khxzStr);
		spannableInfo.setSpan(new TextClickable(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this,KHXZActivity.class));
			}
		}),34,40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		login_prompt.setText(spannableInfo);
		login_prompt.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
		login_prompt.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明

		//绑定软键盘到EditText
/*		login_phone_edt.setFocusable(true);
		login_phone_edt.setFocusableInTouchMode(true);
		login_phone_edt.requestFocus();
		InputMethodManager inputManager = (InputMethodManager)login_phone_edt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(login_phone_edt, 0);
		root_layout.getRootView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				if(isKeyboardShown(root_layout.getRootView())){
					System.out.println();
				}else {
					login_phone_edt.clearFocus();
					login_phone_edt.setFocusable(true);
					login_phone_edt.setFocusableInTouchMode(true);
					login_phone_edt.requestFocus();
					login_phone_edt.findFocus();

					View v = getCurrentFocus();
					if ( v instanceof EditText) {
						Rect outRect = new Rect();
						v.getGlobalVisibleRect(outRect);
						if (!outRect.contains((int) touchX, (int)touchY)) {
							v.clearFocus();
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
						}
					}
				}
			}
		});*/

		//监听号码输入
		login_phone_edt.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
				Editable editable = login_phone_edt.getText();
				int len = editable.length();

				if (len > 1){
					login_next_btn.setBackground(getResources().getDrawable(R.drawable.circle25_greenbg_style));
				}else{
					login_next_btn.setBackground(getResources().getDrawable(R.drawable.circle25_lightgreenbg_style));
				}

				String tempPhoneNo = editable.toString().replace(" ","");
				if (tempPhoneNo.length() >= 11 && !StringUtil.isMobileNO(tempPhoneNo)){
					Toast.makeText(LoginActivity.this,"手机号码格式不正确！",Toast.LENGTH_SHORT).show();
				}

				if(count == 1){
					int lenth = charSequence.toString().length();
					if (lenth == 3 || lenth == 8){
						login_phone_edt.setText(charSequence + " ");
						login_phone_edt.setSelection(login_phone_edt.getText().toString().length());
					}
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});

		login_next_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(StringUtil.isEmpty(login_phone_edt.getText().toString())){
					Toast.makeText(LoginActivity.this,"手机号码不能为空！",Toast.LENGTH_SHORT).show();
					return;
				}

				//判断手机号
				if(!StringUtil.isMobileNO(login_phone_edt.getText().toString().replace(" ",""))){
					Toast.makeText(LoginActivity.this,"手机号码格式错误！",Toast.LENGTH_SHORT).show();
					return;
				}

				login_phone_underline.setVisibility(View.GONE);
				login_next_btn.setVisibility(View.GONE);
				login_prompt.setVisibility(View.GONE);

				//给服务器发送手机号码，用于获取验证码
				sendPhoneNoToServer();

				login_verifycode_ll.requestFocus();

				login_phone_countdown_tv.setVisibility(View.VISIBLE);
				login_verifycode_ll.setVisibility(View.VISIBLE);
				login_verifycode_ll.setInputCompleteListener(new IdentifyingCodeView.InputCompleteListener() {
					@Override
					public void inputComplete() {
						Log.i("icv_input", login_verifycode_ll.getTextContent());

						if (null != login_verifycode_ll && login_verifycode_ll.getTextContent().length() == login_verifycode_ll.getTextCount()){
							LoginActivity.this.sendVerifyCodeToServer(login_verifycode_ll.getTextContent());
						}
					}

					@Override
					public void deleteContent() {
						Log.i("icv_delete", login_verifycode_ll.getTextContent());
					}
				});

				countDownTime();
			}
		});

		//重新获取验证码
		login_phone_countdown_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != login_verifycode_ll && 6 == login_verifycode_ll.getTextCount()){
							LoginActivity.this.sendPhoneNoToServer();
				}
				countDownTime();

				login_error_tv.setText("");
				login_error_tv.setBackgroundColor(Color.WHITE);
			}
		});

		login_close_iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LoginActivity.this.finish();
			}
		});
	}
	
	private void countDownTime(){
		//获取验证码倒计时
		CountDownTimerUtil countDownTimerUtil = new CountDownTimerUtil(login_phone_countdown_tv,60 * 1000,1000,
				StringUtil.setTextSizeColor("重新发送", Color.BLACK,0,"重新发送".length(),14));
		countDownTimerUtil.start();
	}

	//给服务器发送手机号码，用于获取验证码
	private void sendPhoneNoToServer(){
		String phoneNo = login_phone_edt.getText().toString().replace(" ","");
		SharedPreferencesUtils.setConfigStr(MyApplication.getContext(), Constants.TEMP, "phoneno", phoneNo);

		//采用okhttp3来进行网络请求
		String url = UrlManager.URL_SENDVIRIFY;
		OkHttpClient mOkHttpClient = new OkHttpClient();
		RequestBody formBody = new FormBody.Builder().add("mobile", phoneNo).build();
		Request request = new Request.Builder().url(url).post(formBody).build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try {
					String str = response.body().string();
					Log.i(Constants.TAG, str);

					ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);

					if (resInfo.getResult() == Constants.RESULT_SUCCESS) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = 3;
                        msg.obj = resInfo.getError_message();
                        mHandler.sendMessage(msg);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	//给服务器发送验证码进行验证
	public void sendVerifyCodeToServer(String verifyCode){
		final String phoneNo = SharedPreferencesUtils.getConfigStr(MyApplication.getContext(), Constants.TEMP, "phoneno");
		//采用okhttp3来进行网络请求
		String url =  UrlManager.URL_VERIFYLOGIN;
		OkHttpClient mOkHttpClient = new OkHttpClient();
		RequestBody formBody = new FormBody.Builder().add("mobile", phoneNo).add("verify",verifyCode).build();
		Request request = new Request.Builder().url(url).post(formBody).build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try {
					String str = response.body().string();
					Log.i(Constants.TAG, str);

					ResInfo resInfo = JSONObject.parseObject(str, ResInfo.class);
					if (resInfo.getResult() == Constants.RESULT_SUCCESS) {
                        //获取Data的JSONObject对象
                        JSONObject dataJsonObject = JSONObject.parseObject(resInfo.getData().toString());
                        //把字符串转成Bean对象
                        UserInfo userInfo = JSONObject.parseObject(dataJsonObject.get("user_info").toString(), UserInfo.class);
                        //通过bean对象取出相应的值
                        int id = userInfo.getId();
                        //通过jsonObject对象取出token值
                        String token = dataJsonObject.get("token").toString();

                        //token保存在本地
                        SystemUtil.getInstance().setToken(token);
                        SystemUtil.getInstance().setIsLogin(true);

                        //登录成功后保存用户的信息
                        SharedPreferencesUtils.saveToShared(MyApplication.getContext(),"userInfo",userInfo);

                        String name = Thread.currentThread().getName();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //登录成功显示成功图片
                                LoginActivity.this.showLoginSuccess();
                            }
                        });
                    } else {
                        Message msg = new Message();
                        msg.what = 3;
                        msg.obj = resInfo.getError_message();
                        mHandler.sendMessage(msg);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 1:
					if (Constants.IS_DEVELOPING){
						UiHelper.ShowOneToast(getApplicationContext(),(String) msg.obj);
					}
					break;
				case 2:

					break;
				case 3:
					login_error_tv.setText((String) msg.obj);
					login_error_tv.setVisibility(View.VISIBLE);
					login_error_tv.setBackgroundColor(getResources().getColor(R.color.bg_gray));
					UiHelper.ShowOneToast(getApplicationContext(), (String) msg.obj);
					break;
				default:
					break;
			}
		}
	};

	private String toActivity;
	private void showLoginSuccess(){
		//显示登录成功画面
		Context context = LoginActivity.this;
		final Dialog dia = new Dialog(context, R.style.edit_AlertDialog_style);
		dia.setContentView(R.layout.login_success);
		ImageView imageView = (ImageView) dia.findViewById(R.id.login_success_iv);
		imageView.setBackgroundResource(R.drawable.login_success);
		//选择true的话点击其他地方可以使dialog消失，为false的话不会消失
		dia.setCanceledOnTouchOutside(false); // Sets whether this dialog is
		Window w = dia.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		lp.y = 40;
		dia.onWindowAttributesChanged(lp);
		dia.show();

		new CountDownTimer(2 * 1000,1000) {
			@Override
			public void onTick(long millisUntilFinished) {

			}

			@Override
			public void onFinish() {
				dia.dismiss();
				LoginActivity.this.finish();

				Intent intent = new Intent();
				intent.setAction(Constants.LOGINSUCCESS_ACTION);
				intent.putExtra("toActivity",toActivity);
				sendBroadcast(intent);
			}
		}.start();
	}

	//（倒计时结束）重置控件状态（隐藏“验证码”；显示想“下一步”；隐藏倒计时控件）
	public void resetWidgetStatus(){
		login_verifycode_ll.setVisibility(View.GONE);
		login_next_btn.setVisibility(View.VISIBLE);
		login_phone_countdown_tv.setVisibility(View.GONE);
	}

	public void forgetPwd(View view) {
		Intent intent = new Intent(this, ForgetPwdActivity.class);
		startActivity(intent);
	}

	public void register(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

	public void login(View view) {
		networkRequest();
	}

	public void qqLogin(View view) {

	}

	public void weixinLogin(View view) {

	}

	public void weiboLogin(View view) {

	}

	private boolean isKeyboardShown(View rootView) {
		final int softKeyboardHeight = 100;
		Rect r = new Rect();
		rootView.getWindowVisibleDisplayFrame(r);
		DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
		int heightDiff = rootView.getBottom() - r.bottom;
		return heightDiff > softKeyboardHeight * dm.density;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			touchY = event.getX();
			touchY = event.getY();

			Rect outRect = new Rect();
			login_phone_edt.getGlobalVisibleRect(outRect);
			if (!outRect.contains((int) touchX, (int)touchY)) {
				login_phone_edt.clearFocus();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(login_phone_edt.getWindowToken(), 0);
			}
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}
}
