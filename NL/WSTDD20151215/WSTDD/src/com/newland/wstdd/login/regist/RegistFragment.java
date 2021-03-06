package com.newland.wstdd.login.regist;

import test.TestData;
import junit.framework.Test;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragment;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.EditTextUtil;
import com.newland.wstdd.common.tools.StringUtil;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.view.LoadingDialog;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.login.beanrequest.CheckCodeReq;
import com.newland.wstdd.login.beanrequest.RegistFirstReq;
import com.newland.wstdd.login.beanresponse.CheckCodeRes;
import com.newland.wstdd.login.beanresponse.RegistFirstRes;
import com.newland.wstdd.login.handle.CheckCodeHandle;
import com.newland.wstdd.login.handle.RegistFragmentHandle;
import com.newland.wstdd.mine.minesetting.about.ProtocolActivity;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;

/**注册界面 
 * @author H81 2015-11-9
 *
 */
@SuppressLint("ValidFragment")
public class RegistFragment extends BaseFragment implements OnPostListenerInterface {
	private CheckCodeRes checkCodeRes;//服务器返回的信息
	int recLen = 0;//计时大小   0--59
	private boolean checkcodeJudge=true;//验证码倒计时  判断
	private Button nextButton;//下一步
	private Context context;//上下文
	private boolean isOk;//验证首次登入是否成功
	private RegistFirstRes registFirstRes;//从服务器返回的信息
	private RegistFragmentHandle handler = new RegistFragmentHandle(this);
	private EditText phoneEditText,checkCodeEditText;
	private TextView getCheckCodeTextView;
	private TextView protocol_tv;//服务协议
	@SuppressLint("ValidFragment")
	public RegistFragment(Context context){
		this.context=context;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//下一步
		case R.id.regist_next:
			//进行第一步验证   看看是否成功只有成功了才会跳转到二个注册页页面
			registFirst();//发送到服务器上去，验证第一次是否成功
			break;
		case R.id.protocol_tv://服务协议
			Intent intent = new Intent(getActivity(), ProtocolActivity.class);
			startActivity(intent);
			break;
		case R.id.fragment_regist_checkcode_tv:
			//获取验证码
			if (!EditTextUtil.checkMobileNumber(phoneEditText.getText().toString())) {
				UiHelper.ShowOneToast(getActivity(), "手机号格式不对或者有误");
			}else {
			getCheckCodeTextView.setClickable(false); 
			recLen=60;checkcodeJudge = true;
	        new Thread(new MyThread()).start();// start thread	
			postPhoneNumber();
			}
			break;
		default:
			break;
		}
	}

	
	//获取验证码
	//发送手机获取验证码
		private void postPhoneNumber() {
			super.refresh();
			try {
				new Thread() {
					public void run() {
						String phone = phoneEditText.getText().toString();//手机号码
						if (!EditTextUtil.checkMobileNumber(phone)) {
							UiHelper.ShowOneToast(getActivity(), "手机号格式不对或者有误");
						}else {
							// 需要发送一个request的对象进行请求
							CheckCodeReq reqInfo = new CheckCodeReq();
							reqInfo.setPhoneNum(phone);

							BaseMessageMgr mgr = new HandleNetMessageMgr();
							RetMsg<CheckCodeRes> ret = mgr.getCheckCodeIndo(reqInfo);// 泛型类，																
							Message message = new Message();
							message.what = RegistFragmentHandle.CHECK_CODE;// 设置死
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
	//验证
	private void registFirst() {
		// TODO Auto-generated method stub
		refresh();//发送服务器请求
	}
	@Override
	protected View createAndInitView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_regist, container,false);	
		nextButton = (Button) view.findViewById(R.id.regist_next);
		protocol_tv = (TextView) view.findViewById(R.id.protocol_tv);
		phoneEditText = (EditText) view.findViewById(R.id.fragment_regist_phone_et);
		checkCodeEditText =(EditText) view.findViewById(R.id.fragment_regist_checkcode_et);
		getCheckCodeTextView = (TextView) view.findViewById(R.id.fragment_regist_checkcode_tv);
		getCheckCodeTextView.setOnClickListener(this);
		nextButton.setOnClickListener(this);
		protocol_tv.setOnClickListener(this);
		return view;
	}
	
	
	@Override
	public void refresh() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					String phone = phoneEditText.getText().toString();//手机号码
					String veriCode = checkCodeEditText.getText().toString();
					if (!EditTextUtil.checkMobileNumber(phone)) {
						UiHelper.ShowOneToast(getActivity(), "手机号格式不对或者有误");
					}else {
					// 需要发送一个request的对象进行请求
					RegistFirstReq reqInfo = new RegistFirstReq();
					reqInfo.setPhoneNum(phone);
					reqInfo.setVeriCode(veriCode);
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<RegistFirstRes> ret = mgr.getRegistInfo(reqInfo);// 泛型类，																
					Message message = new Message();
					message.what = RegistFragmentHandle.REGIST_FIRST;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						registFirstRes = (RegistFirstRes) ret.getObj();
						message.obj = registFirstRes;
					} else {
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
	
	
	
	
	
	//处理服务器返回的内容  刷新在界面上
	@Override
	public void OnHandleResultListener(Object obj,int responseId) {
		// TODO Auto-generated method stub
		switch (responseId) {
		//第一次注册的处理
		case RegistFragmentHandle.REGIST_FIRST:
			if (dialog != null) {
				dialog.dismiss();
			}
			try {
			registFirstRes = (RegistFirstRes) obj;
			if(registFirstRes!=null){
				String userIdString=registFirstRes.getUserId();//用户id
				String headImgUrlString=registFirstRes.getHeadImgUrl();//头像地址
				String nickNameString=registFirstRes.getNickName();//昵称
				Bundle bundle=new Bundle();
				bundle.putString("userIdString", userIdString);
				bundle.putString("headImgUrlString", headImgUrlString);
				bundle.putString("nickNameString", nickNameString);
				if(!userIdString.equals("")&&userIdString!=null){
					UiHelper.showRegistFinishActivity(getActivity(), bundle);
				}else{
					UiHelper.ShowOneToast(getActivity(), "验证码有误,需重新发送验证码！");
				}
			
			}
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;

			//短信验证码
		case RegistFragmentHandle.CHECK_CODE:
			if (dialog != null) {
				dialog.dismiss();
			}
			checkCodeRes = (CheckCodeRes) obj;
			if (checkCodeRes!=null) {
						
			}
			break;
		default:
			break;
		}
		
		
	}
	//请求失败后的操作
	@Override
	public void OnFailResultListener(String mess) {if (dialog != null) {dialog.dismiss();}
		// TODO Auto-generated method stub
	if (dialog != null) {
		dialog.dismiss();
	}
		UiHelper.ShowOneToast(getActivity(), mess);
	}
	

	/**
	 * 获取短信验证码的相关操作
	 */
	 final Handler handlers = new Handler(){          // handle 
	        public void handleMessage(Message msg){ 
	            switch (msg.what) { 
	            case 1: 
	            	
	                recLen--; 
	                getCheckCodeTextView.setText(recLen+"秒"); 
	             
	            break;
	            case 0:
	            	getCheckCodeTextView.setClickable(true);
            		getCheckCodeTextView.setText("获取验证码");
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
