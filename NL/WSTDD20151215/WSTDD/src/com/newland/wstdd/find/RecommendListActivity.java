package com.newland.wstdd.find;

import android.os.Bundle;
import android.os.Message;
import android.view.Menu;

import com.newland.wstdd.R;
import com.newland.wstdd.common.base.BaseFragmentActivity;
import com.newland.wstdd.common.resultlisterer.OnPostListenerInterface;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.find.bean.FindRecommendReq;
import com.newland.wstdd.find.bean.FindRecommendRes;
import com.newland.wstdd.find.handle.FindRecommendListHandle;
import com.newland.wstdd.login.RetMsg;
import com.newland.wstdd.netutils.BaseMessageMgr;
import com.newland.wstdd.netutils.HandleNetMessageMgr;

public class RecommendListActivity extends BaseFragmentActivity implements OnPostListenerInterface {

	//服务器返回
	FindRecommendRes findRecommendRes;
	//异步消息
	FindRecommendListHandle handler = new FindRecommendListHandle(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recommend_list, menu);
		return true;
	}

	@Override
	public void OnHandleResultListener(Object obj, int responseId) {
		// TODO Auto-generated method stub
		switch (responseId) {
		//推荐活动列表
		case FindRecommendListHandle.FIND_RECOMMEND_LIST:
			if (dialog != null) {dialog.dismiss();}
			findRecommendRes = (FindRecommendRes) obj;
			if(findRecommendRes!=null){
//				UiHelper.ShowOneToast(this, "获取推荐活动列表成功");
				
			}
			break;

		default:
			break;
		}
		
	}

	@Override
	public void OnFailResultListener(String mess) {if (dialog != null) {dialog.dismiss();}
		// TODO Auto-generated method stub
		UiHelper.ShowOneToast(this, mess);
	}

	@Override
	protected void processMessage(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh() {
		super.refresh();
		try {
			new Thread() {
				public void run() {
					// 需要发送一个request的对象进行请求
					FindRecommendReq reqInfo = new FindRecommendReq();
					reqInfo.setCity("全国");
					reqInfo.setPage("1");
					reqInfo.setProvince("");
					reqInfo.setRow("3");
					reqInfo.setSearchText("娱乐");
				
					BaseMessageMgr mgr = new HandleNetMessageMgr();
					RetMsg<FindRecommendRes> ret = mgr.getFindRecommendListInfo(reqInfo);// 泛型类，																
					Message message = new Message();
					message.what = FindRecommendListHandle.FIND_RECOMMEND_LIST;// 设置死
					// 访问服务器成功 1 否则访问服务器失败
					if (ret.getCode() == 1) {
						findRecommendRes = (FindRecommendRes) ret.getObj();
						message.obj = findRecommendRes;
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
