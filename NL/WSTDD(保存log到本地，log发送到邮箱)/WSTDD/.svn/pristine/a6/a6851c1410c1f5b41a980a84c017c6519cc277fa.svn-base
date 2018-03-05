/**
 * @fields MineUpdateAddressHandle.java
 */
package com.newland.wstdd.mine.receiptaddress.handle;

import com.newland.wstdd.mine.receiptaddress.MineEditReceiptAddressActivity;
import com.newland.wstdd.mine.receiptaddress.beanresponse.MineAddAddressRes;

import android.os.Handler;
import android.os.Message;

/**更新地址
 * @author H81 2015-11-27
 *
 */
public class MineUpdateAddressHandle extends Handler{
	public static final int UPDATE_ADDRESS = 0;
	MineEditReceiptAddressActivity context;
	
	/**
	 * 
	 */
	public MineUpdateAddressHandle(MineEditReceiptAddressActivity mineEditReceiptAddressActivity) {
		this.context = mineEditReceiptAddressActivity;
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case UPDATE_ADDRESS:
			// 只有当msg.obj对象为空，表示访问服务器成功
						if (msg.obj != null) {
							if (msg.obj instanceof String) {
								context.OnFailResultListener((String)msg.obj);
							} else if (msg.obj instanceof MineAddAddressRes) {
								context.OnHandleResultListener(msg.obj, UPDATE_ADDRESS);
							}
						}
			break;

		default:
			break;
		}
		super.handleMessage(msg);
	}
	
}
