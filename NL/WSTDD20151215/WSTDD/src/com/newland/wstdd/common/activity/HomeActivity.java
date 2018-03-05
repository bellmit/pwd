package com.newland.wstdd.common.activity;

import com.newland.wstdd.R;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.fragment.MainFragment;
import com.newland.wstdd.login.beanresponse.LoginRes;
import com.newland.wstdd.login.beanresponse.ThirdLoginRes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;
/**
 * 这是存放fragment的FragmentActivity 
 * 放mainfragment
 * @author Administrator
 *
 */
public class HomeActivity extends FragmentActivity {
	private MainFragment mainFragment;
	private FragmentManager fm = getSupportFragmentManager();
	private FragmentTransaction fragmentTransaction = getSupportFragmentManager()
			.beginTransaction();
	private LoginRes loginRes;//登录获取到的所有信息
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		AppManager.getAppManager().addActivity(this);//添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮	
		setContentView(R.layout.activity_home);
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		loginRes=(LoginRes) bundle.getSerializable("loginres");
		mainFragment=(MainFragment) fm.findFragmentById(R.id.mainfragments);
		if(mainFragment==null){
			mainFragment=new MainFragment();
			fragmentTransaction.add(R.id.fragmentmain, mainFragment);
			fragmentTransaction.commit();
			fm.executePendingTransactions();
		}
		
	}

}
