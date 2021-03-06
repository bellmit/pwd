package com.newland.wstdd.login.login;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.newland.wstdd.R;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.widget.PengRadioButton;
import com.newland.wstdd.login.regist.RegistFragment;

/**
 * 登录注册fragment切换界面
 * 
 * @author H81 2015-11-12
 * 
 */
public class LoginActivity extends FragmentActivity  {
	private RadioGroup radiogroup;// 组中放两个单选按钮
	private FragmentManager fragmentmanager;// 管理显示的Fragment							// 通过这个管理器进行Fragement的切换
	private AppContext appContext;
	private PengRadioButton registRadioButton;//注册界面的按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		AppManager.getAppManager().addActivity(this);// 添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮
		setContentView(R.layout.activity_login);
		appContext = AppContext.getAppContext();
		fragmentmanager = getSupportFragmentManager();// 通过FragementActivity的方式获取FragementManager管理器对象
		initView();// 界面布局的初始化
		// 更改当前的Fragement受理业务的fragement 第二个参数是容器 第三个参数是内容
		appContext.replaceFragment(fragmentmanager, R.id.login_regist_details, new LoginFragment(LoginActivity.this));
	}

	private void initView() {
		registRadioButton = (PengRadioButton) findViewById(R.id.regist_label_icon);
		radiogroup = (RadioGroup) findViewById(R.id.login_regist_radiogroup);
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				/*
				 * 
				 * 对于replaceFragment这个方法来说，需要记住的是三个参数 1.Fragmentmanager对象
				 * 2.容器FrameLayout(xml中，一般使用的是FrameLayout) 3.Fragment对象
				 * 
				 * 也就是将Fragment 放入xml布局中的一个FrameLayout容器中
				 */
				if (checkedId == R.id.login_label_icon) {// 受理业务

					appContext.replaceFragment(fragmentmanager, R.id.login_regist_details, new LoginFragment(LoginActivity.this));
				} else if (checkedId == R.id.regist_label_icon) {// 计费系统

					appContext.replaceFragment(fragmentmanager, R.id.login_regist_details, new RegistFragment(LoginActivity.this));
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);

		return true;
	}
	//显示出注册的界面
	public void showRegistFragment(){
		appContext.replaceFragment(fragmentmanager, R.id.login_regist_details, new RegistFragment(LoginActivity.this));
		registRadioButton.setChecked(true);
	}
}
