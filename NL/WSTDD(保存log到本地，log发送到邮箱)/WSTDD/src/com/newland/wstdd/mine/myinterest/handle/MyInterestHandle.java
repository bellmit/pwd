package com.newland.wstdd.mine.myinterest.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.R;
import com.newland.wstdd.mine.myinterest.DownloadShopFruitResponse;
import com.newland.wstdd.mine.myinterest.MyInterestActivity;
import com.newland.wstdd.mine.myinterest.beanresponse.MyInterestTagsRes;

public class MyInterestHandle extends Handler {
	public static final int MY_INTEREST = 0;
	private MyInterestActivity context;
	public MyInterestHandle(MyInterestActivity context) {
		this.context = context;
	}
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case MY_INTEREST:	
			//只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj!= null) {
				if(msg.obj instanceof String){
					context.OnFailResultListener((String) msg.obj);
					}
				else if(msg.obj instanceof MyInterestTagsRes) {	
					//看看是否有要更新的，有的话先更新的保存到本地的数据库中,更新完成后，然后用广播的形式通知另一外一个界面表更新好了，可以直接获取本地的数据
					context.OnHandleResultListener(msg.obj, MY_INTEREST);
				}
			} 
			break;
			
			
			
	
		}
		
		super.handleMessage(msg);
	}
	
	

	
	
}
