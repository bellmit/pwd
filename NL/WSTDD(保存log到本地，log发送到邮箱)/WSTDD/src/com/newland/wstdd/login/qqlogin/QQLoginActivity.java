package com.newland.wstdd.login.qqlogin;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class QQLoginActivity extends Activity {
	private static final String TAG = "QQLoginActivity";//收集异常日志tag
	private static final String QQTAG = QQLoginActivity.class.getName();
	public static String mAppid;
//	private Button mNewLoginButton;
//	private TextView mUserInfo;
//	private ImageView mUserLogo;
	public static QQAuth mQQAuth;
	private UserInfo mInfo;
	private Tencent mTencent;
	private final String APP_ID = "1104957952";// 测试时使用，真正发布的时候要换成自己的APP_ID

	private String nickName;//昵称
	private String headImg;//头像名称
	private String openId;//用户的openId
	private String actionType;//动作  比如是第三方登录  还是绑定操作
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		AppManager.getAppManager().addActivity(this);//添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮	
		
		 /**收集异常日志*/
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        //如果你要收集普通的日志到文件或者服务器，那么调用下面的方法即可。
        LogManager.getManager(getApplicationContext()).log(TAG, "onCreate",LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**收集异常日志*/
        
		Intent intent=getIntent();
		actionType=intent.getStringExtra("QQLOGIN");
		Log.d(QQTAG, "-->onCreate");
		// 固定竖屏
			Log.d(QQTAG, "-->onStart");
		final Context context = QQLoginActivity.this;
		final Context ctxContext = context.getApplicationContext();
		mAppid = APP_ID;
		mQQAuth = QQAuth.createInstance(mAppid, ctxContext);
		mTencent = Tencent.createInstance(mAppid, QQLoginActivity.this);
		//如果该账号已经登入登录之后，记住先退出，然后才可以继续登录
		if (mQQAuth.isSessionValid()) {
			mQQAuth.logout(this);
		}
		initViews();
	}

	@Override
	protected void onStart() {
	
		super.onStart();
	}



	@SuppressWarnings("unused")
	private void initViews() {
		
		
		onClickLogin();

		
	}

	
	//获取到了用户信息后进行更新信息
	private void updateUserInfo() {
		if (mQQAuth != null && mQQAuth.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onComplete(final Object response) {
					Message msg = new Message();
					msg.obj = response;
					msg.what = 0;
					mHandler.sendMessage(msg);
				}

				@Override
				public void onCancel() {
				}
			};
			mInfo = new UserInfo(this, mQQAuth.getQQToken());
			mInfo.getUserInfo(listener);

		} 
	}

	
	
	
	//处理得到数据之后的信息
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				JSONObject response = (JSONObject) msg.obj;
				if (response.has("nickname")) {
					try {
						nickName=response.getString("nickname");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (response.has("figureurl")) {
					try {
						headImg=response.getString("figureurl_qq_2");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
					AppContext.getAppContext().setOpenId(openId);
					
				if("login".equals(actionType)){
					Intent intent0 = new Intent();//切记  这里的Action参数与IntentFilter添加的Action要一样才可以
			    	intent0.setAction("GET_THIRD_MESS");
			    	sendBroadcast(intent0);//发送广播了
			    	AppContext.getAppContext().setNickName(nickName);
					AppContext.getAppContext().setHeadImgUrl(headImg);
			    	QQLoginActivity.this.finish();
				}else if("bind".equals(actionType)){
					Intent intent0 = new Intent();//切记  这里的Action参数与IntentFilter添加的Action要一样才可以
			    	intent0.setAction("GET_THIRD_MESS_BIND");
			    	sendBroadcast(intent0);//发送广播了
			    	QQLoginActivity.this.finish();
				}
					
				
			} 
		}

	};

	//先执行
	private void onClickLogin() {
		if (!mQQAuth.isSessionValid()) {
			IUiListener listener = new BaseUiListener() {
				@Override
				protected void doComplete(JSONObject values) {
					updateUserInfo();		
				}
			};
			mQQAuth.login(this, "all", listener);
			// mTencent.loginWithOEM(this, "all",
			// listener,"10000144","10000144","xxxx");
			mTencent.login(this, "all", listener);
		} 
		else {
			mQQAuth.logout(this);
			finish();
//			IUiListener listener = new BaseUiListener() {
//				@Override
//				protected void doComplete(JSONObject values) {
//					updateUserInfo();		
//				}
//			};
//			mQQAuth.login(this, "all", listener);
//			// mTencent.loginWithOEM(this, "all",
//			// listener,"10000144","10000144","xxxx");
//			mTencent.login(this, "all", listener);
		}
	}

	public static boolean ready(Context context) {
		if (mQQAuth == null) {
			return false;
		}
		boolean ready = mQQAuth.isSessionValid()
				&& mQQAuth.getQQToken().getOpenId() != null;
		if (!ready)
			Toast.makeText(context, "login and get openId first, please!",
					Toast.LENGTH_SHORT).show();
		return ready;
	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			try {
				openId = ((JSONObject) response).getString("openid");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			doComplete((JSONObject) response);
			
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			Util.toastMessage(QQLoginActivity.this, "onError: " + e.errorDetail);
			Util.dismissDialog();
		}

		@Override
		public void onCancel() {
//			Util.toastMessage(QQLoginActivity.this, "onCancel: ");
//			Util.dismissDialog();
			finish();
		}
		
	}

	@Override
	protected void onDestroy() {
		 /**收集异常日志*/
    	LogManager.getManager(getApplicationContext()).unregisterActivity(this);
    	 /**收集异常日志*/
		finish();
		super.onDestroy();
	}

}
