package com.tastebychance.sonchance.startup;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tastebychance.sonchance.MyApplication;
import com.tastebychance.sonchance.MyBaseActivity;
import com.tastebychance.sonchance.R;
import com.tastebychance.sonchance.TabHostMainActivity;
import com.tastebychance.sonchance.loginandregister.LoginActivity;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;

/**启动页
 * @author shenbh
 *
 * 2017年8月9日
 */
public class StartUpActivity extends MyBaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.startup);
	}

	/**跳转到tabhost
	 * 
	 */
	public void intentToTabHost(){
		Intent intent;
		// 当前版本号
		int versionCode = SystemUtil.getInstance().getVersionCode(MyApplication.getContext());
		int oldVersonCode = SharedPreferencesUtils.getConfigInt(MyApplication.getContext(), Constants.TEMP, "versionCode");//已存储版本号
		if (oldVersonCode == 0 || versionCode > oldVersonCode) {//版本号为空代表未使用过，版本更新判断版本号比当前的高需要进入引导页
			intent = new Intent(this, GuideActivity.class);
		} else {
			intent = new Intent(this, TabHostMainActivity.class);
		}
		SharedPreferencesUtils.setConfigInt(MyApplication.getContext(), Constants.TEMP, "versionCode", versionCode);
		startActivity(intent);
		this.finish();
	}
	
	/**跳转到注册界面
	 * 
	 */
	public void intentToLogin(){
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
		this.finish();
	}
	
	/**
	 * 跳转到引导页
	 */
	public void intentToGuide(){
		
	}

	@Override
	protected void onStart() {
		hideNavigation();
		super.onStart();

		//几秒后跳转
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean flag = true;
				while (flag) {
					try {
						Thread.sleep(2 * 1000L);
						flag = false;
						intentToTabHost();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("StartUp----------------------------------------------------"+getTaskId());
	}
}
