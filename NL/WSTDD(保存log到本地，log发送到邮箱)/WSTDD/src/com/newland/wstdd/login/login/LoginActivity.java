package com.newland.wstdd.login.login;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.common.AppContext;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
import com.newland.wstdd.common.widget.PengRadioButton;
import com.newland.wstdd.common.widget.RelativeLayoutView;
import com.newland.wstdd.common.widget.RelativeLayoutView.KeyBordStateListener;
import com.newland.wstdd.login.regist.RegistFragment;

/**
 * 登录注册fragment切换界面
 * 
 * @author H81 2015-11-12
 * 
 */
public class LoginActivity extends FragmentActivity  implements KeyBordStateListener
/*implements KeyBordStateListener*/
{
	private static final String TAG = "LoginActivity";//收集异常日志tag
	private RelativeLayoutView adjustLayoutView;//解决软键盘遮挡问题;
	private View adjustView;//显示隐藏 控件   让 其他控件可以显示出来
	int  keyBoardHeight;
	private RadioGroup radiogroup;// 组中放两个单选按钮
	private FragmentManager fragmentmanager;// 管理显示的Fragment							// 通过这个管理器进行Fragement的切换
	private PengRadioButton registRadioButton;//注册界面的按钮
	private TextView loginRegistTv;//关闭界面
	ScrollView adjustScrollview;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		AppManager.getAppManager().addActivity(this);// 添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮
		setContentView(R.layout.activity_login);
		
		  /**收集异常日志*/
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        //如果你要收集普通的日志到文件或者服务器，那么调用下面的方法即可。
        LogManager.getManager(getApplicationContext()).log(TAG, "onCreate",LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**收集异常日志*/
        
		fragmentmanager = getSupportFragmentManager();// 通过FragementActivity的方式获取FragementManager管理器对象
		initView();// 界面布局的初始化
		// 更改当前的Fragement受理业务的fragement 第二个参数是容器 第三个参数是内容
		AppContext.getAppContext().replaceFragment(fragmentmanager, R.id.login_regist_details, new LoginFragment(LoginActivity.this));
	
		
	/*	//活得可根据软键盘的弹出/关闭而隐藏和显示某些区域的CustomLinearLayout组件
				resizeLayout = (CustomLinearLayout) findViewById(R.id.layout);
				//活得要控制隐藏和显示的区域
				linearLayout = (LinearLayout) findViewById(R.id.sublayout);
				resizeLayout.setKeyBordStateListener(this);//设置回调方法
*/	
	}
	
	@Override
	protected void onDestroy() {
		 /**收集异常日志*/
    	LogManager.getManager(getApplicationContext()).unregisterActivity(this);
    	 /**收集异常日志*/
    	super.onDestroy();
	}

	private void initView() {
		adjustLayoutView = (RelativeLayoutView) findViewById(R.id.adjust_rl_layout);
		adjustLayoutView.setKeyBordStateListener(this);
		adjustView = findViewById(R.id.adjust_view);
		loginRegistTv = (TextView) findViewById(R.id.login_regist_close_icon);
		loginRegistTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
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
					if(null != AppContext.getAppContext())
					AppContext.getAppContext().replaceFragment(fragmentmanager, R.id.login_regist_details, new LoginFragment(LoginActivity.this));
				} else if (checkedId == R.id.regist_label_icon) {// 计费系统
					if(null != AppContext.getAppContext())
					AppContext.getAppContext().replaceFragment(fragmentmanager, R.id.login_regist_details, new RegistFragment(LoginActivity.this));
				}
			}
		});

		adjustScrollview = (ScrollView) findViewById(R.id.adjust_scrollview);
		adjustLayoutView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			/**
			 * the result is pixels
			 */
			@Override
			public void onGlobalLayout() {

				Rect r = new Rect();
				// (0, 50 - 720, 1280)
				Rect aaRect = new Rect();
				adjustLayoutView.getWindowVisibleDisplayFrame(r);
				int height = AppContext.getAppContext().getScreenHeight();
				int screenHeight = adjustLayoutView.getRootView().getHeight();
				keyBoardHeight = screenHeight - (r.bottom - r.top);

				// boolean visible = heightDiff > screenHeight / 3;
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
		if(null != AppContext.getAppContext())
		AppContext.getAppContext().replaceFragment(fragmentmanager, R.id.login_regist_details, new RegistFragment(LoginActivity.this));
		registRadioButton.setChecked(true);
	}
	
	
	@Override
	public void stateChange(int state) {
		// TODO Auto-generated method stub
		switch (state) {
		case RelativeLayoutView.KEYBORAD_HIDE:
			adjustView.setVisibility(View.GONE);
			adjustScrollview.scrollTo(0, 0);  
			adjustScrollview.smoothScrollTo(0, 0); 
			break;
		case RelativeLayoutView.KEYBORAD_SHOW:
			adjustView.setVisibility(View.VISIBLE);
			adjustScrollview.scrollTo(0, keyBoardHeight/3);  
			adjustScrollview.smoothScrollTo(0, keyBoardHeight/3); 
			//使用post的方法
//			adjustScrollview.post(new Runnable() {  
//				    @Override  
//				    public void run() {  
//				    	adjustScrollview.scrollTo(0, -200);  
//				    }   
//				});  
			break;
		}
	}

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(null != AppContext.getAppContext())
		AppContext.getAppContext().setTempUserNameString("");
	}
}
