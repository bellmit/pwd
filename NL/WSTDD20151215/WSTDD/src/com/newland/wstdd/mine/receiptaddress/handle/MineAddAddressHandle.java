/**
 * @fields MineAddAddressHandle.java
 */
package com.newland.wstdd.mine.receiptaddress.handle;

import com.newland.wstdd.mine.receiptaddress.MineAddReceiveAddressActivity;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineAddAddressRes;

import android.os.Handler;
import android.os.Message;

/**新增地址 
 * @author H81 2015-11-26
 *
 */
public class MineAddAddressHandle extends Handler{
	public static final int GET_ADDRESS_LIST = 0;//获取收获地址(13.	收货地址新增/列表)
	MineAddReceiveAddressActivity context;

	/**
	 * @param mineAddReceiveAddressActivity
	 */
	public MineAddAddressHandle(MineAddReceiveAddressActivity mineAddReceiveAddressActivity) {
		this.context = mineAddReceiveAddressActivity;
	}

	@Override
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
			default:
				break;
		}
		super.handleMessage(msg);
	}
	
	
}
