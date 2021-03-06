package com.newland.wstdd.originate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.find.handle.SingleActivityHandle;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;
import com.newland.wstdd.originate.beanrequest.SingleActivityReq;
import com.newland.wstdd.originate.beanresponse.SingleActivityRes;
/**
 * 单个活动获取详细信息
 * @author Administrator
 *
 */
public class SingleActivityActivity extends BaseFragmentActivity implements OnPostListenerInterface {
	Intent intent;
	String activityId;//活动的id
	SingleActivityRes singleActivityRes;//服务器的返回信息
	SingleActivityHandle handler=new SingleActivityHandle(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_activity);
		intent =getIntent();
		activityId= intent.getStringExtra("activityId");
		refresh();//请求服务器
		
	}



	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		switch (responseId) {
		//单个活动详情
		case SingleActivityHandle.SINGLE_ACTIVITY:
			if (dialog != null) {
				dialog.dismiss();
			}
			singleActivityRes=(SingleActivityRes) obj;
			if(singleActivityRes!=null)
			{
//				UiHelper.ShowOneToast(this, "获取单个活动信息成功");
			}
			break;

		default:
			break;
		}
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
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					SingleActivityReq reqInfo = new SingleActivityReq();
					reqInfo.setActivityId(activityId);
				
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<SingleActivityRes> ret = mgr.getSingleActivityInfo(reqInfo);// 泛型类，																
					Message message = new Message();
					message.what = SingleActivityHandle.SINGLE_ACTIVITY;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						singleActivityRes = (SingleActivityRes) ret.getObj();
						message.obj = singleActivityRes;
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
	public void initView() {
		// TODO Auto-generated method stub
		
	}
	

}
