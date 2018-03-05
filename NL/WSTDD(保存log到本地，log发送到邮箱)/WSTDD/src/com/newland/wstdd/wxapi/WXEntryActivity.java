package com.newland.wstdd.wxapi;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
import com.newland.wstdd.login.weixinlogin.JsonUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


/**
 * 
 * @description 微信第三方登录，分享demo,更多移动开发内容请关注： http://blog.csdn.net/xiong_it
 * @charset UTF-8
 * @author xiong_it 
 * @date 2015-9-9下午2:50:14
 * @version
 */
/*
 * 微信登录，分享应用中必须有这个名字叫WXEntryActivity，并且必须在wxapi包名下，腾讯官方文档中有要求
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private static final String TAG = "WXEntryActivity";//收集异常日志tag
	String nickname;//昵称
	String unionid;//唯一的openid
	String headUrl ;//头像
	
	private static final String WXTAG = "WXEntryActivity";
	public static final String APP_ID = "wx1b84c30d9f380c89";// 微信开放平台申请到的app_id
	public static final String APP_SECRET = "d4624c36b6795d1d99dcf0547af5443d";// 微信开放平台申请到的app_id对应的app_secret
	private static final String WEIXIN_SCOPE = "snsapi_userinfo";// 用于请求用户信息的作用域
	private static final String WEIXIN_STATE = "login_state"; // 自定义
	protected static final int RETURN_OPENID_ACCESSTOKEN = 0;// 返回openid，accessToken消息码
	protected static final int RETURN_NICKNAME_UID = 1; // 返回昵称，uid消息码	
	private IWXAPI api;
	private SendAuth.Req req;
	private IntentFilter filter;// 定一个广播接收过滤器
	 String weixinType;//类型  是登录还是分享
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RETURN_OPENID_ACCESSTOKEN:
				Bundle bundle1 = (Bundle) msg.obj;
				String accessToken = bundle1.getString("access_token");
				String openId = bundle1.getString("open_id");
				AppContext.getAppContext().setOpenId(openId);
				getUID(openId, accessToken);
				break;

			case RETURN_NICKNAME_UID:
				Bundle bundle2 = (Bundle) msg.obj;
				String nickname = bundle2.getString("nickname");
				String uid = bundle2.getString("unionid");
				//发送广播  通知  前台已经得到第三方的相关信息
				if("login".equals(WeiXinString.weixintypeString)){
					Intent intent0 = new Intent();//切记  这里的Action参数与IntentFilter添加的Action要一样才可以
			    	intent0.setAction("GET_THIRD_MESS");
					AppContext.getAppContext().setNickName(nickname);
					AppContext.getAppContext().setHeadImgUrl(headUrl);
			    	sendBroadcast(intent0);//发送广播了
				}else if("bind".equals(WeiXinString.weixintypeString)){
					Intent intent0 = new Intent();//切记  这里的Action参数与IntentFilter添加的Action要一样才可以
			    	intent0.setAction("GET_THIRD_MESS_BIND");
			    	sendBroadcast(intent0);//发送广播了
				}
				
				break;

			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		AppManager.getAppManager().addActivity(this);// 添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮
		
		/** 收集异常日志 */
		LogManager.getManager(getApplicationContext()).registerActivity(this);
		// 如果你要收集普通的日志到文件或者服务器，那么调用下面的方法即可。
		LogManager.getManager(getApplicationContext()).log(TAG, "onCreate", LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**收集异常日志*/
			        
		// 注册广播
		filter = new IntentFilter("WEIXIN_TYPE");// 注册第三方登录的广播
		registerReceiver(broadcastReceiver, filter);// 在对象broadcastReceiver中进行接收的相应处理
		Intent intent = getIntent();
		if(intent.getStringExtra("login")!=null){
			WeiXinString.weixintypeString = intent.getStringExtra("login");
		}
		
		api = WXAPIFactory.createWXAPI(this, APP_ID, false);
		api.registerApp(APP_ID);
		
		try {
			api.handleIntent(getIntent(), this);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		finish();
	
	}

	/**
	 * 构造一个用于请求的唯一标识
	 * @param type 分享的内容类型
	 * @return 
	 */
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	/**
	 * 申请授权
	 */
	private void sendAuth() {
		req = new SendAuth.Req();
		req.scope = WEIXIN_SCOPE;
		req.state = WEIXIN_STATE;
		api.sendReq(req);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.i(WXTAG, "onNewIntent");
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	/**
	 * 请求回调接口
	 */
	@Override
	public void onReq(BaseReq req) {
		Log.i(WXTAG, "onReq");
	}
	
    /**
     * 请求响应回调接口
     */
	@Override
	public void onResp(BaseResp resp) {
		
//		Log.i(TAG, "onResp");
//		SendAuth.Resp sendAuthResp = (Resp) resp;// 用于分享时不要有这个，不能强转
//		String code = sendAuthResp.code;
//		getResult(code);
//		int errCode = resp.errCode;		
		 switch (resp.errCode) {  
	        case BaseResp.ErrCode.ERR_OK:  
//	            Toast.makeText(getApplicationContext(), "分享成功", 2000).show();
	            System.out.println("success");
	            if("login".equals(WeiXinString.weixintypeString)||"bind".equals(WeiXinString.weixintypeString)){
	            SendAuth.Resp sendAuthResp = (SendAuth.Resp) resp;// 用于分享时不要有这个，不能强转
	    		String code = sendAuthResp.code;
	    		getResult(code);
	            }
	            this.finish();
	            //分享成功  
	            break;  
	        case BaseResp.ErrCode.ERR_USER_CANCEL:  
	            //分享取消  
//	            Toast.makeText(getApplicationContext(), "分享取消", 2000).show();
	            System.out.println("ERR_USER_CANCEL");
	            this.finish();
	            break;  
	        case BaseResp.ErrCode.ERR_AUTH_DENIED:  
	            Toast.makeText(getApplicationContext(), "分享失败", 2000).show();
	            System.out.println("ERR_AUTH_DENIED");
	            this.finish();
	            //分享拒绝  
	            break;  
	        }  
		
		
		
		
		
		
		
	}

	/**
	 * 获取openid accessToken值用于后期操作
	 * @param code 请求码
	 */
	private void getResult(final String code) {
		new Thread() {// 开启工作线程进行网络请求
			public void run() {
				String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
						+ APP_ID
						+ "&secret="
						+ APP_SECRET
						+ "&code="
						+ code
						+ "&grant_type=authorization_code";
				try {
					JSONObject jsonObject = JsonUtils
							.initSSLWithHttpClinet(path);// 请求https连接并得到json结果
					if (null != jsonObject) {
						String openid = jsonObject.getString("openid")
								.toString().trim();
						String access_token = jsonObject
								.getString("access_token").toString().trim();
						Log.i(WXTAG, "openid = " + openid);
						Log.i(WXTAG, "access_token = " + access_token);

						Message msg = handler.obtainMessage();
						msg.what = RETURN_OPENID_ACCESSTOKEN;
						Bundle bundle = new Bundle();
						bundle.putString("open_id", openid);
						bundle.putString("access_token", access_token);
						msg.obj = bundle;
						handler.sendMessage(msg);
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return;
			};
		}.start();
	}

	/**
	 * 获取用户唯一标识
	 * @param openId
	 * @param accessToken
	 */
	private void getUID(final String openId, final String accessToken) {
		new Thread() {
			@Override
			public synchronized void run() {
				String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
						+ accessToken + "&openid=" + openId;
				JSONObject jsonObject = null;
				try {
					jsonObject = JsonUtils.initSSLWithHttpClinet(path);
					nickname = jsonObject.getString("nickname");
					unionid = jsonObject.getString("unionid");
					headUrl = jsonObject.getString("headimgurl");
					AppContext.getAppContext().setOpenId(openId);

					
					Log.i(WXTAG, "nickname = " + nickname);
					Log.i(WXTAG, "unionid = " + unionid);
					Message msg = handler.obtainMessage();
					msg.what = RETURN_NICKNAME_UID;
					Bundle bundle = new Bundle();
					bundle.putString("nickname", nickname);
					bundle.putString("unionid", unionid);
					msg.obj = bundle;
					handler.sendMessage(msg);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {   

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {   

            finish();

            return true;   

        }   

        finish();//结束退出程序 

        return false;   

    } 
    
    /**
	 * 接收到广播之后 在这里要做三件事情 将第三方的数据发送给服务器 openId
	 */
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			WeiXinString.weixintypeString = intent.getStringExtra("login");
			
		}
	};
	
	protected void onDestroy() {
		 /**收集异常日志*/
    	LogManager.getManager(getApplicationContext()).unregisterActivity(this);
    	 /**收集异常日志*/
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();
	};
}
