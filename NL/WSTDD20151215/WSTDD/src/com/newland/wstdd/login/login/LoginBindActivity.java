package com.newland.wstdd.login.login;

import test.TestData;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.SharePreferenceUtil.SharePreferenceUtil;
import com.newland.wstdd.common.activity.HomeActivity;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.EditTextUtil;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.login.beanrequest.CheckCodeReq;
import com.newland.wstdd.login.beanrequest.LoginBindReq;
import com.newland.wstdd.login.beanrequest.RegistFirstReq;
import com.newland.wstdd.login.beanrequest.ThirdLoginReq;
import com.newland.wstdd.login.beanresponse.CheckCodeRes;
import com.newland.wstdd.login.beanresponse.LoginBindRes;
import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.RegistFirstRes;
import com.newland.wstdd.login.beanresponse.ThirdLoginRes;
import com.newland.wstdd.login.handle.BindRegistFragmentHandle;
import com.newland.wstdd.login.handle.CheckCodeHandle;
import com.newland.wstdd.login.regist.RegistFragment.MyThread;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;

/**第三方登录（微信、qq）
 * @author Administrator
 * 2015-12-9
 */
public class LoginBindActivity extends BaseFragmentActivity implements OnPostListenerInterface {

	int recLen = 0;//计时大小   0--59
	private boolean checkcodeJudge=true;//验证码倒计时  判断
	
	private AppContext appContext;
	private TextView confirmTextView;//获取验证码
	
	private EditText phoneEditText,confirmEditText; //输入手机跟验证码
	
	private Button bindLogin;//绑定登录
	//服务器返回
	private CheckCodeRes checkCodeRes;//服务器返回的信息
	private CheckCodeHandle handler=new CheckCodeHandle(this);
	//进行注册
	private RegistFirstRes registFirstRes;//从服务器返回的信息
	private BindRegistFragmentHandle handler1 = new BindRegistFragmentHandle(this);
	//进行绑定
	private LoginBindRes loginBindRes;//从服务器返回的信息
	//第三方登录
	private ThirdLoginRes thirdLoginRes;//第三方登录的请求
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appContext=AppContext.getAppContext();
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		AppManager.getAppManager().addActivity(this);// 添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮
		setContentView(R.layout.activity_login_bind);
		initView();
	}

	public void initView() {
		confirmTextView = (TextView) findViewById(R.id.bindlogin_getconfirm);
		confirmTextView.setOnClickListener(this);//获取验证码
		
		phoneEditText = (EditText) findViewById(R.id.bindlogin_phone);
		confirmEditText = (EditText) findViewById(R.id.bindlogin_confirm);
		
		bindLogin = (Button) findViewById(R.id.bind_login_bt);
		bindLogin.setOnClickListener(this);
	}

	@Override
	public void OnFailResultListener(String mess) {if (dialog != null) {dialog.dismiss();}
		UiHelper.ShowOneToast(this, mess);
	}

	@Override
	protected void processMessage(Message msg) {
		
	}

	@Override
	public void refresh() {
		
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.bindlogin_getconfirm:
			if (!EditTextUtil.checkMobileNumber(phoneEditText.getText().toString())) {
				UiHelper.ShowOneToast(LoginBindActivity.this, "手机号格式不对或者有误");
			}else {
			//获取验证码
			confirmTextView.setClickable(false); 
			recLen=60;checkcodeJudge = true;
	        new Thread(new MyThread()).start();// start thread	
			//获取短信验证码
			postPhoneNumber();//短信验证
			}
			
			
			
			break;
			
		case R.id.bind_login_bt:
			RegistFirstReq();
			
			
			
			
			break;
		default:
			break;
		}
	}
	//发送手机获取验证码
	private void postPhoneNumber() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					String phone = phoneEditText.getText().toString();//手机号码
					if (!EditTextUtil.checkMobileNumber(phone)) {
						UiHelper.ShowOneToast(LoginBindActivity.this, "手机号格式不对或者有误");
					}else {
					// 需要发送一个request的对象进行请求
					CheckCodeReq reqInfo = new CheckCodeReq();
					reqInfo.setPhoneNum(phone);

					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<CheckCodeRes> ret = mgr.getCheckCodeIndo(reqInfo);// 泛型类，																
					Message message = new Message();
					message.what = CheckCodeHandle.CHECK_CODE;// 设置死
					// 访问服务器成功 1 否则访问服务器失败		
					if(ret.getCode()==1){
						checkCodeRes=new CheckCodeRes();
						checkCodeRes.setCheckFinishMess(StringUtil.noNull(ret.getMsg()));
						message.obj = checkCodeRes;	
					}else {
					
						message.obj = ret.getMsg();	
					}
							
					handler.sendMessage(message);
					}
				};
			}.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}

	
	//第三方登录的时候  未绑定的时候进行的第一次的请求
	
		private void RegistFirstReq() {
			super.refresh();
			try {
				new Thread() {
					public void run() {
						String phone = phoneEditText.getText().toString();//手机号码
						String veriCode = confirmEditText.getText().toString();
						if (!EditTextUtil.checkMobileNumber(phone)) {
							UiHelper.ShowOneToast(LoginBindActivity.this, "手机号格式不对或者有误");
						}else {
					
						// 需要发送一个request的对象进行请求
						RegistFirstReq reqInfo = new RegistFirstReq();
						reqInfo.setPhoneNum(phone);
						reqInfo.setVeriCode(veriCode);
						reqInfo.setPlatForm(AppContext.getAppContext().getPlatForm());
						reqInfo.setOpenId(AppContext.getAppContext().getOpenId());
						BaseMessageMgr mgr = new HandleNetMessageMgr();
						RetMsg<RegistFirstRes> ret = mgr.getRegistInfo(reqInfo);// 泛型类，																
						Message message = new Message();
						message.what = BindRegistFragmentHandle.BIND_REGIST_FIRST;// 设置死
						// 访问服务器成功 1 否则访问服务器失败
						if (ret.getCode() == 1) {
							registFirstRes = (RegistFirstRes) ret.getObj();
							message.obj = registFirstRes;
						} else {
							message.obj = ret.getMsg();
						}
						handler1.sendMessage(message);
						}
					};
				}.start();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}
	
	//发送请求绑定
	
		private void requestBind() {
			super.refresh();
			try {
				new Thread() {
					public void run() {
						String phone = TestData.nameString;//手机号码
						String veriCode = TestData.paswString;
					
						// 需要发送一个request的对象进行请求
						LoginBindReq reqInfo = new LoginBindReq();
						reqInfo.setPhoneNum(phone);
						reqInfo.setPlatForm(AppContext.getAppContext().getPlatForm());
						reqInfo.setOpenId(AppContext.getAppContext().getOpenId());
						BaseMessageMgr mgr = new HandleNetMessageMgr();
						RetMsg<LoginBindRes> ret = mgr.getReqLoginBindInfo(reqInfo);// 泛型类，																
						Message message = new Message();
						message.what = CheckCodeHandle.BING_LOGIN;// 设置死
						// 访问服务器成功 1 否则访问服务器失败
						if (ret.getCode() == 1) {
							loginBindRes =new LoginBindRes();
							loginBindRes.setLoginBindMess(StringUtil.noNull(ret.getMsg()));
							message.obj = loginBindRes;
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
		 * 利用绑定过的进行请求登录
		 * 
		 */
		private void getThirdLoginResMess() {
			super.refresh();

			try {
				new Thread() {
					public void run() {
						// 需要发送一个request的对象进行请求
						ThirdLoginReq reqInfo = new ThirdLoginReq();
						reqInfo.setPlatForm(AppContext.getAppContext().getPlatForm());
						reqInfo.setOpenId(AppContext.getAppContext().getOpenId());
						BaseMessageMgr mgr = new HandleNetMessageMgr();
						RetMsg<ThirdLoginRes> ret = mgr.getThirdLoginInfo(reqInfo);// 泛型类，
						Message message = new Message();
						message.what = CheckCodeHandle.LOGIN_BIND;// 设置死
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
		
	/**
	 * 处理服务器的数据
	 */
	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		try {
			switch (responseId) {
			//短信验证码
			case CheckCodeHandle.CHECK_CODE:
				if (dialog != null) {
					dialog.dismiss();
				}
				checkCodeRes = (CheckCodeRes) obj;
				if (checkCodeRes!=null) {
					UiHelper.ShowOneToast(this, checkCodeRes.getCheckFinishMess()+"请输入手机号跟验证码！");				
				}
				//跳转到注册的第一步
				RegistFirstReq();//进行注册的第一步请求
				
				break;
			//绑定之后第一次注册请求
			case BindRegistFragmentHandle.BIND_REGIST_FIRST:
				if (dialog != null) {
					dialog.dismiss();
				}
				try {
				registFirstRes = (RegistFirstRes) obj;
				if(registFirstRes!=null){
					String userIdString=registFirstRes.getUserId();//用户id
					AppContext.getAppContext().setUserId(userIdString);
					String headImgUrlString=registFirstRes.getHeadImgUrl();//头像地址
					String nickNameString=registFirstRes.getNickName();//昵称
					Bundle bundle=new Bundle();
					bundle.putString("userIdString", userIdString);
					bundle.putString("headImgUrlString", headImgUrlString);
					bundle.putString("nickNameString", nickNameString);
					//如果密码不为空的话,那么就是已经注册过的用户，这样就直接弹出是否绑定，要是绑定
					if (registFirstRes.getPwd()!=null&&!"".equals(registFirstRes.getPwd())) {
						if(!userIdString.equals("")&&userIdString!=null){
							showDownLoadDialog();
							
						}else{
							UiHelper.ShowOneToast(this, "无法获取用户ID,需重新发送验证码！");
						}
					}else{
						UiHelper.showRegistFinishActivity(this, bundle);
					}
				
				
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			//绑定成功之后  的处理	
			case CheckCodeHandle.BING_LOGIN:
				if (dialog != null) {
					dialog.dismiss();
				}
				loginBindRes = (LoginBindRes) obj;
				if (loginBindRes!=null) {
					//已经绑定成功了，可以自动进行根据第三方进行登录了
					getThirdLoginResMess();	
				}
				UiHelper.ShowOneToast(this, loginBindRes.getLoginBindMess());
				break;	
		
			case CheckCodeHandle.LOGIN_BIND:
				if (dialog != null) {
					dialog.dismiss();
				}
				thirdLoginRes = (ThirdLoginRes) obj;
				UiHelper.ShowOneToast(this, "注册完成");
				if (thirdLoginRes != null) {
					if ("true".equals(thirdLoginRes.getIfBind())) {
						(new SharePreferenceUtil(LoginBindActivity.this)).savesAppcntextInfo("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","true");
						
						LoginRes loginResInfo=new LoginRes();
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
						
						Intent intent = new Intent(this, HomeActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("loginres", loginResInfo);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					//该账号还没有绑定的用户，需要进行绑定或者登入
					else if("false".equals(thirdLoginRes.getIfBind())){
						//跳转到绑定界面
						UiHelper.ShowOneToast(this, "绑定失败！");
						finish();
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
	 /**
	 * 弹出对话看表示手机号已经注册过了，是否要进行绑定
	 */
	private void showDownLoadDialog() {
		
		AlertDialog dialog=new AlertDialog.Builder(this)
		.setTitle("是否使用当前手机号进行绑定").setPositiveButton("确认绑定", new android.content.DialogInterface.OnClickListener() {

			
			public void onClick(DialogInterface dialog, int which) {
				requestBind();//发送请求绑定
				
				
				
			}

		
		}).setNegativeButton("更换手机号", new android.content.DialogInterface.OnClickListener() {

			
			public void onClick(DialogInterface dialog, int which) {
				
				finish();
				
				
			}
		}).show();
		dialog.setCanceledOnTouchOutside(false);
	}
	
	/**
	 * 获取短信验证码的相关操作
	 */
	 final Handler handlers = new Handler(){          // handle 
	        public void handleMessage(Message msg){ 
	            switch (msg.what) { 
	            case 1: 
	            	
	                recLen--; 
	                confirmTextView.setText(recLen+"秒"); 
	             
	            break;
	            case 0:
	            	confirmTextView.setClickable(true);
	            	confirmTextView.setText("获取验证码");
	            break;
	            }
	            super.handleMessage(msg); 
	        } 
	    }; 
	  
	    public class MyThread implements Runnable{      // thread 
	        @Override 
	        public void run(){ 
	            while(checkcodeJudge){ 
	                try{ 
	                	if(recLen==0){
	                		checkcodeJudge=false;
	                	
	                		 Message message = new Message(); 
	 	                    message.what = 0; 
	 	                    handlers.sendMessage(message); 
	                	}else{
	                		Thread.sleep(1000);     // sleep 1000ms 
	                    Message message = new Message(); 
	                    message.what = 1; 
	                    handlers.sendMessage(message); 
	                	}
	                    
	                }catch (Exception e) { 
	                } 
	            }
	        }
	    }
}
