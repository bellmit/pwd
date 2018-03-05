package com.newland.wstdd.common.SharePreferenceUtil;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LoginPreferencesService {
	private Context context;
	private final String USER_INFO="login_info";
	public LoginPreferencesService(Context context) {
		this.context = context;
	}
	/**
	 * 保存参数
	 * @param name 账户名
	 * @param age 密码
	 */
	public void save(String username, String  password) {
		SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.remove("username");
		editor.remove("password");
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	}
	/**
	 * 获取各项配置参数,相当于读取文件中的数据
	 * 集合都是键-值对的形式，可以通过key找到相应的值
	 * @return
	 */
	public Map<String, String> getPreferences(){
		Map<String, String> params = new HashMap<String, String>();
		SharedPreferences preferences = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		/**
		 * 理解一下这里的put方法只是为了封装该类，以便在另外一个类中可以去调用
		 * 
		 * preferences.getString("name", "1");如果没有参数name的话，这时候呢默认为1，否则就是按name对应的值
		 */
		params.put("username", preferences.getString("username", ""));//1表示的是如果不存在name键时候，默认为1
		params.put("password", preferences.getString("password", ""));//
		return params;
	}
	
	 /**
	  *  清空记录
	  */
	public void clear() {
      SharedPreferences sp = context.getSharedPreferences(USER_INFO,
             Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sp.edit();
      editor.clear();
      editor.commit();
	      }
}
