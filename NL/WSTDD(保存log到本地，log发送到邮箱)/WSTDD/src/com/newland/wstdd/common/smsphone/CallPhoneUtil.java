package com.newland.wstdd.common.smsphone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.newland.wstdd.common.tools.EditTextUtil;
import com.newland.wstdd.common.tools.UiHelper;

public class CallPhoneUtil {

	public static void callPhone(String phoneString,Context context) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction("android.intent.action.CALL");//动作名称
		intent.addCategory("android.intent.category.DEFAULT");//类别   注意这里不是set而是add，但是默认可省
//		intent.setData(Uri.parse("tel:" + "18094007487"));//数据   传给对方，根据"tell"+号码的数据格式匹配好，变成一个Uri对象发送出去（发送给系统）
		if(phoneString!=null){
			intent.setData(Uri.parse("tel:" + phoneString));//数据   传给对方，根据"tell"+号码的数据格式匹配好，变成一个Uri对象发送出去（发送给系统）
			context.startActivity(intent);// 通过意图去调用系统的打电话的功能，方法内部会自动为Intent添加类别：android.intent.category.DEFAULT
		}else {
			UiHelper.ShowOneToast(context, "电话号码格式错误");
		}		
	}
}
