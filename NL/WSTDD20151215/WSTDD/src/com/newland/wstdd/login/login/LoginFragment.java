package com.newland.wstdd.login.login;

import test.TestData;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.SharePreferenceUtil.SharePreferenceUtil;
import com.newland.wstdd.common.activity.HomeActivity;
import com.newland.wstdd.common.base.BaseFragment;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.login.beanrequest.LoginReq;
import com.newland.wstdd.login.beanrequest.ThirdLoginReq;
import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.ThirdLoginRes;
import com.newland.wstdd.login.handle.LoginFragmentHandle;
import com.newland.wstdd.login.qqlogin.QQLoginActivity;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
import com.newland.wstdd.wxapi.WXEntryActivity;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 登录界面
 * 
 * @author H81 2015-11-9
 * 
 */
@SuppressLint("ValidFragment")
public class LoginFragment extends BaseFragment implements OnPostListenerInterface {
	/**
	 * 第三方登录
	 */
	// 第三方的平台
	private String platFrom;// 0 qq 1微信 2新浪微博
	// 微信登录
	private ImageView weixinImageView;// 测试 微信测试
	private IWXAPI api;
	private static final String WEIXIN_SCOPE = "snsapi_userinfo";// 用于请求用户信息的作用域
	private static final String WEIXIN_STATE = "login_state"; // 自定义
	private Context context;
	private EditText usernameTextView, passTextView;
	private TextView createUserTextView;//创建用户
	private Button loginButton;
	private LoginRes loginResInfo;// 正常登录的时候
	private ThirdLoginRes thirdLoginRes;// 第三方登录的返回信息
	private LoginFragmentHandle handler = new LoginFragmentHandle(this);
	private ImageView mFragment_login_qq_iv;//qq测试
	private AppContext appContext;
	/**
	 * 广播
	 * 
	 * @param context
	 */
	private IntentFilter filter;// 定一个广播接收过滤器

	public LoginFragment(){
		
	}
	@SuppressLint("ValidFragment")
	public LoginFragment(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	protected View createAndInitView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		initView(view);
		appContext = AppContext.getAppContext();
		// 注册广播
		filter = new IntentFilter("GET_THIRD_MESS");// 注册第三方登录的广播
		getActivity().registerReceiver(broadcastReceiver, filter);// 在对象broadcastReceiver中进行接收的相应处理
		return view;
	}

	private void initView(View view) {
		createUserTextView = (TextView) view.findViewById(R.id.fragment_login_createuser);
		createUserTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((LoginActivity)context).showRegistFragment();
				
				
			}
		});
		usernameTextView = (EditText) view.findViewById(R.id.login_user_name);
		passTextView = (EditText) view.findViewById(R.id.login_user_password);
		loginButton = (Button) view.findViewById(R.id.login_login);
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 直接发送请求给服务器
				refresh();

				// getThirdLoginResMess();//从服务器获取到绑定登录信息，要是绑定的话，那么
				// Intent intent = new Intent(getActivity(),
				// HomeActivity.class);
				// startActivity(intent);

				// Intent intent = new Intent(getActivity(),
				// HomeActivity.class);
				// startActivity(intent);

			}

		});

		/**
		 * qq登录
		 */
		mFragment_login_qq_iv = (ImageView) view.findViewById(R.id.fragment_login_qq_iv);

		mFragment_login_qq_iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				platFrom="0";
				Intent intent =new Intent(getActivity(),QQLoginActivity.class);
				startActivity(intent);
			}
		});

		/**
		 * 微信登录
		 */
		weixinImageView = (ImageView) view.findViewById(R.id.weixinImg);
		weixinImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//发送广播  通知  前台已经得到第三方的相关信息
				Intent intent =new Intent(getActivity(),WXEntryActivity.class);
				intent.putExtra("login", "login");
		    	getActivity().startActivity(intent);
				Intent intent0 = new Intent();//切记  这里的Action参数与IntentFilter添加的Action要一样才可以
		    	intent0.setAction("WEIXIN_TYPE");
		    	intent0.putExtra("login", "login");
		    	getActivity().sendBroadcast(intent0);//发送广播了
		    	
				platFrom="1";
				SendAuth.Req req;
				api = WXAPIFactory.createWXAPI(getActivity(), "wx1b84c30d9f380c89", false);
				api.registerApp("wx1b84c30d9f380c89");
				req = new SendAuth.Req();
				req.scope = WEIXIN_SCOPE;
				req.state = WEIXIN_STATE;
				api.sendReq(req);
			
			}
		});

	}

	
	@Override
	public void refresh() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					String phone = TestData.nameString;
					String pasw = TestData.paswString;
					// 需要发送一个request的对象进行请求
					LoginReq loginReqInfo = new LoginReq();
					loginReqInfo.setPhoneNum(phone);
					loginReqInfo.setPwd(pasw);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<LoginRes> ret = mgr.getLoginInfo(loginReqInfo);// 泛型类，
					Message message = new Message();
					message.what = LoginFragmentHandle.USER_LOGIN;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						loginResInfo = (LoginRes) ret.getObj();
						message.obj = loginResInfo;
					} else {
						message.obj = ret.getMsg();
					}
					handler.sendMessage(message);
				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 网络请求第三方登录的 信息
	 */
	private void getThirdLoginResMess() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					ThirdLoginReq reqInfo = new ThirdLoginReq();
					reqInfo.setPlatForm(platFrom);
					reqInfo.setOpenId(appContext.getOpenId());
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<ThirdLoginRes> ret = mgr.getThirdLoginInfo(reqInfo);// 泛型类，
					Message message = new Message();
					message.what = LoginFragmentHandle.THIRD_LOGIN;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						thirdLoginRes = (ThirdLoginRes) ret.getObj();
						message.obj = thirdLoginRes;
					} else {
						message.obj = ret.getMsg();
					}
					handler.sendMessage(message);
				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}



	//服务器返回
	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		// TODO Auto-generated method stub
		try {
			
		
		switch (responseId) {
		case LoginFragmentHandle.USER_LOGIN:
			if (dialog != null) {
				dialog.dismiss();
			}
			loginResInfo = (LoginRes) obj;
			if (loginResInfo != null) {
				(new SharePreferenceUtil(getActivity())).savesAppcntextInfo("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","true");
				appContext.setUserId(loginResInfo.getUserId());
				appContext.setUserPwd(TestData.paswString);
				appContext.setHeadImgUrl(loginResInfo.getHeadImgUrl());
				appContext.setMyAcNum(loginResInfo.getMyAcNum());
				appContext.setMyFavAcNum(loginResInfo.getMyFavAcNum());
				appContext.setMySignAcNum(loginResInfo.getMySignAcNum());
				appContext.setTags(loginResInfo.getTags());
				appContext.setHomeAds(loginResInfo.getHomeAds());
				appContext.setNickName(loginResInfo.getNickName());
				appContext.setIsLogin("true");
				Intent intent = new Intent(getActivity(), HomeActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("loginres", loginResInfo);
				intent.putExtras(bundle);
				startActivity(intent);
			}
			break;
		case LoginFragmentHandle.THIRD_LOGIN:
			if (dialog != null) {
				dialog.dismiss();
			}
			thirdLoginRes = (ThirdLoginRes) obj;
			if (thirdLoginRes != null) {
				if ("true".equals(thirdLoginRes.getIfBind())) {
					//创建一下SharePreference
					(new SharePreferenceUtil(getActivity())).savesAppcntextInfo("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","true");
					
					loginResInfo=new LoginRes();
					loginResInfo.setHeadImgUrl(thirdLoginRes.getHeadImgUrl());
					loginResInfo.setHomeAds(thirdLoginRes.getHomeAds());
					loginResInfo.setMyAcNum(thirdLoginRes.getMyAcNum());
					loginResInfo.setMyFavAcNum(thirdLoginRes.getMyFavAcNum());
					loginResInfo.setMySignAcNum(thirdLoginRes.getMySignAcNum());
					loginResInfo.setNickName(thirdLoginRes.getNickName());
					loginResInfo.setTags(thirdLoginRes.getTags());
					loginResInfo.setUserId(thirdLoginRes.getUserId());

					appContext.setUserId(loginResInfo.getUserId());
					appContext.setHeadImgUrl(loginResInfo.getHeadImgUrl());
					appContext.setMyAcNum(loginResInfo.getMyAcNum());
					appContext.setMyFavAcNum(loginResInfo.getMyFavAcNum());
					appContext.setMySignAcNum(loginResInfo.getMySignAcNum());
					appContext.setTags(loginResInfo.getTags());
					appContext.setHomeAds(loginResInfo.getHomeAds());
					appContext.setNickName(loginResInfo.getNickName());
					appContext.setIsLogin("true");
					Intent intent = new Intent(getActivity(), HomeActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("loginres", loginResInfo);
					intent.putExtras(bundle);
					startActivity(intent);
				}
				//该账号还没有绑定的用户，需要进行绑定或者登入
				else if("false".equals(thirdLoginRes.getIfBind())){
					//跳转到绑定界面
					AppContext.getAppContext().setPlatForm(platFrom);
					Intent intent=new Intent(getActivity(),LoginBindActivity.class);
					getActivity().startActivity(intent);
				}

			}
			break;
		default:
			break;
		}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void OnFailResultListener(String mess) {if (dialog != null) {dialog.dismiss();}
		// TODO Auto-generated method stub
		UiHelper.ShowOneToast(getActivity(), mess);
	}

	/**
	 * 接收到广播之后 在这里要做三件事情 将第三方的数据发送给服务器 openId
	 */
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (appContext.getOpenId() != null && !"".equals(appContext.getOpenId())) {
				// 发送openId给第三方
				getThirdLoginResMess();

			} else {
				UiHelper.ShowOneToast(getActivity(), "异常原因获取第三方资料失败！");
			}

		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(broadcastReceiver);
		
	}

	
	
	
}
