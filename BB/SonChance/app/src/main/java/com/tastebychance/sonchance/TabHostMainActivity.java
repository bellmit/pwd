package com.tastebychance.sonchance;



import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.tastebychance.sonchance.about.AboutActivity;
import com.tastebychance.sonchance.find.FindActivity;
import com.tastebychance.sonchance.homepage.HomeActivity;
import com.tastebychance.sonchance.personal.PersonalActivity;
import com.tastebychance.sonchance.personal.balance.BalanceActivity;
import com.tastebychance.sonchance.personal.bankcard.BankCardActivity;
import com.tastebychance.sonchance.personal.locate.GoodsReceiptAddressManagerActivity;
import com.tastebychance.sonchance.personal.locate.util.ToastUtil;
import com.tastebychance.sonchance.personal.membercenter.MemberCenterActivity;
import com.tastebychance.sonchance.personal.myevaluate.MyEvaluateActivity;
import com.tastebychance.sonchance.personal.ordercoupon.PersonCouponActivity;
import com.tastebychance.sonchance.personal.personalcenter.PersonalInformationActivity;
import com.tastebychance.sonchance.personal.richscan.RichScanActivity;
import com.tastebychance.sonchance.util.Constants;
import com.tastebychance.sonchance.util.SharedPreferencesUtils;
import com.tastebychance.sonchance.util.StringUtil;
import com.tastebychance.sonchance.util.SystemUtil;

import java.util.Timer;
import java.util.TimerTask;

public class TabHostMainActivity extends TabActivity implements OnClickListener{
	public TabHost tabHost;

	private Context mContext = null;
	private View tabcontent;
	private String currentTag="3";

	private TabHost tabhost;
	private LinearLayout content;
	private TabWidget tabs;
	private LinearLayout tab_jc;
	private ImageView jc_iv;
	private TextView jc_tv;
	private LinearLayout tab_msg;
	private ImageView msg_iv;
	private TextView msg_tv;
	private LinearLayout tab_phone_btn2;
	private ImageView phone_iv;
	private TextView phone_tv;
	private LinearLayout tab_about_btn2;
	private ImageView about_iv;
	private TextView about_tv;


	private TextView tab_home;
	private TextView tab_find;
	private TextView tab_personal;
	private TextView tab_about;

	private IntentFilter intentfilter,changeToHomeFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MyApplication app = (MyApplication) getApplication();// 获取应用程序全局的实例引用
		app.activities.add(this); // 把当前Activity放入集合中

		setContentView(R.layout.tabhost_main);

		intentfilter = new IntentFilter();
		intentfilter.addAction(Constants.LOGINSUCCESS_ACTION);
		changeToHomeFilter = new IntentFilter();
		changeToHomeFilter.addAction(Constants.CHANGETOHOME_ACTION);

		bindViews();

//		x.view().inject(this);
		tabHost = this.getTabHost();
		mContext=this;
		tabcontent = findViewById(R.id.content);
		Intent intent1 = new Intent(this, HomeActivity.class);
		Intent intent2 = new Intent(this, FindActivity.class);
		Intent intent3 = new Intent(this, PersonalActivity.class);
		Intent intent4 = new Intent(this, AboutActivity.class);
//		Intent intent4 = new Intent(this, HomePageActivity.class);

		tabHost.addTab(tabHost.newTabSpec("1").setIndicator("1").setContent(intent1));
		tabHost.addTab(tabHost.newTabSpec("2").setIndicator("2").setContent(intent2));
		tabHost.addTab(tabHost.newTabSpec("3").setIndicator("3").setContent(intent3));
		tabHost.addTab(tabHost.newTabSpec("4").setIndicator("4").setContent(intent4));
		
		/*if (MyApplication.NEEDUNLOCK){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //应用运行时，保持屏幕高亮，不锁屏
		}*/
	}

	private void bindViews() {

		tabhost = (TabHost) findViewById(android.R.id.tabhost);
		content = (LinearLayout) findViewById(R.id.content);
		tabcontent = (FrameLayout) findViewById(android.R.id.tabcontent);
		tabs = (TabWidget) findViewById(android.R.id.tabs);
		
		tab_home = (TextView) findViewById(R.id.tab_home);
		tab_home.setOnClickListener(this);
		
		tab_find = (TextView) findViewById(R.id.tab_find);
		tab_find.setOnClickListener(this);
		
		tab_personal = (TextView) findViewById(R.id.tab_personal);
		tab_personal.setOnClickListener(this);
		
		tab_about = (TextView) findViewById(R.id.tab_about);
		tab_about.setOnClickListener(this);
	}

	@SuppressLint("NewApi")
	private void clearStyle()
	{
		tab_home.setCompoundDrawables(null, getTopDrawable(R.drawable.tab_home_unselected) , null, null);
		tab_find.setCompoundDrawables(null, getTopDrawable(R.drawable.tab_find_unselected), null, null);
		tab_personal.setCompoundDrawables(null, getTopDrawable(R.drawable.tab_person_unselected), null, null);
		tab_about.setCompoundDrawables(null, getTopDrawable(R.drawable.tab_about_unselected), null, null);
		
		tab_home.setTextColor(getResources().getColor(R.color.gray));
		tab_find.setTextColor(getResources().getColor(R.color.gray));
		tab_personal.setTextColor(getResources().getColor(R.color.gray));
		tab_about.setTextColor(getResources().getColor(R.color.gray));
	}
	
	private Drawable getTopDrawable(int resId){
		Drawable topUnSelected = getResources().getDrawable(resId);
		topUnSelected.setBounds(0, 0, topUnSelected.getMinimumWidth(), topUnSelected.getMinimumHeight());
		return topUnSelected;
	}
	BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (null != intent) {
				if (intent.getAction().equals(Constants.CHANGETOHOME_ACTION)){
					changeToHome();
				}else{
					intentTo(intent.getStringExtra("toActivity"));
				}
			}
		}
	};

	/**
	 * 跳转到某个界面
	 * @param toActivity
	 */
	private void intentTo(String toActivity){

		if (StringUtil.isNotEmpty(toActivity) && toActivity.equals(Constants.DO_NOTHING)){
			return;
		}

		switch (toActivity) {
			case Constants.TO_PERSON://我的
				clearStyle();
				tab_personal.setCompoundDrawables(null, getTopDrawable(R.drawable.tab_person_selected), null, null);
				tab_personal.setTextColor(getResources().getColor(R.color.text_red));
				tabHost.setCurrentTabByTag("3");
				break;
			default:
				break;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (null != myBroadcastReceiver){
			registerReceiver(myBroadcastReceiver,intentfilter);
			registerReceiver(myBroadcastReceiver,changeToHomeFilter);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != myBroadcastReceiver){
			unregisterReceiver(myBroadcastReceiver);
		}
		SharedPreferencesUtils.setConfigBoolean(MyApplication.getContext(),Constants.TEMP,"isFromMyOrder",false);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(false);
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	//记录用户首次点击返回键的时间
	private long firstTime = 0;
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//双击退出程序
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
			if (System.currentTimeMillis() - firstTime > 2000){
				Toast.makeText(TabHostMainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
				firstTime = System.currentTimeMillis();
			}else{
				MyApplication.getAppContext().clearStatck();
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	//切换到首页
	private void changeToHome(){
		clearStyle();
		tab_home.setCompoundDrawables(null, getTopDrawable(R.drawable.tab_home_selected), null, null);
		tabHost.setCurrentTabByTag("1");
		tab_home.setTextColor(getResources().getColor(R.color.text_red));
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tab_home:
			changeToHome();
			break;
		case R.id.tab_find:
//			ToastUtil.show(getApplicationContext(),"暂未开放");


			clearStyle();
			tab_find.setCompoundDrawables(null, getTopDrawable(R.drawable.tab_find_selected), null, null);
			tab_find.setTextColor(getResources().getColor(R.color.text_red));
			tabHost.setCurrentTabByTag("2");
			break;
		case R.id.tab_personal:
			if (!SystemUtil.getInstance().getIsLogin()){
				SystemUtil.getInstance().intentToLoginActivity(TabHostMainActivity.this,Constants.TO_PERSON);
			}else{
				intentTo(Constants.TO_PERSON);
			}
			break;
		case R.id.tab_about:
			clearStyle();
			tab_about.setCompoundDrawables(null, getTopDrawable(R.drawable.tab_about_selected), null, null);
			tab_about.setTextColor(getResources().getColor(R.color.text_red));
			tabHost.setCurrentTabByTag("4");
			break;
		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("TabHost----------------------------------------------------"+getTaskId());
	}
}
