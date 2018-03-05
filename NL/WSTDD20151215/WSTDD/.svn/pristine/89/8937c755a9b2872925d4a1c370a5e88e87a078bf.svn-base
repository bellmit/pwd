package com.newland.wstdd.mine.receiptaddress.handle;

import android.os.Handler;
import android.os.Message;

import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.RegistFirstRes;
import com.newland.wstdd.login.login.LoginFragment;
import com.newland.wstdd.login.regist.RegistFragment;
import com.newland.wstdd.mine.beanresponse.MinePersonInfoGetRes;
import com.newland.wstdd.mine.personalcenter.MinePersonalCenterActivity;
import com.newland.wstdd.mine.receiptaddress.MineReceiptAddressListActivity;
import com.newland.wstdd.mine.receiptaddress.beanrequest.MineDefaultAddressReq;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineAddAddressRes;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineDefaultAddressRes;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineDeleteAddressRes;

public class MineReceiptAddressHandle extends Handler {
	public static final int GET_ADDRESS_LIST = 0;//获取收获地址(13.	收货地址新增/列表)
	public static final int SET_ADDRESS_DEFAULT=1;//设置默认地址
	public static final int SET_DELETE_ADDRESS=2;//设置删除地址的操作
	
	private MineReceiptAddressListActivity context;
	public MineReceiptAddressHandle(MineReceiptAddressListActivity context) {
		this.context = context;
	}

	@SuppressWarnings("static-access")
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case GET_ADDRESS_LIST:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof MineAddAddressRes) {
					context.OnHandleResultListener(msg.obj, GET_ADDRESS_LIST);
				}
			}
			break;
		case SET_ADDRESS_DEFAULT:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof MineDefaultAddressRes) {
					context.OnHandleResultListener(msg.obj, SET_ADDRESS_DEFAULT);
				}
			}
			break;
			
		case SET_DELETE_ADDRESS:
			// 只有当msg.obj对象为空，表示访问服务器成功
			if (msg.obj != null) {
				if (msg.obj instanceof String) {
					context.OnFailResultListener((String)msg.obj);
				} else if (msg.obj instanceof MineDeleteAddressRes) {
					context.OnHandleResultListener(msg.obj, SET_DELETE_ADDRESS);
				}
			}
			break;
		}
		super.handleMessage(msg);
	}

}
