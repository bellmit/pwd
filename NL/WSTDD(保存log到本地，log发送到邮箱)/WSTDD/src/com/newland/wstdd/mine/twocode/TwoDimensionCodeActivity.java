package com.newland.wstdd.mine.twocode;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.newland.wstdd.R;
import com.newland.wstdd.common.activity.HomeActivity;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
import com.newland.wstdd.common.tools.UiHelper;
import com.newland.wstdd.common.widget.PengRadioButton;

/**
 * 我的-支付二维码界面
 * 
 * @author H81 2015-11-9
 * 
 */
public class TwoDimensionCodeActivity extends FragmentActivity {
	private static final String TAG = "TwoDimensionCodeActivity";//收集异常日志tag
	  private ArrayList<Fragment> fragmentList;  
	 private ZhiFuBaoFragment zhiFuBaoFragment;
	 private WeiXinFragment weiXinFragment;
	private RadioGroup radiogroup;// 组中放两个单选按钮
	private FragmentManager fragmentmanager;// 管理显示的Fragment											// 通过这个管理器进行Fragement的切换
	private Bitmap weixinBitmap;//生成的时候暂时保存的
	private Bitmap zhifubaoBitmap;//生成的时候暂时保存的
	private ViewPager mPager; 
	private FrameLayout bgFrameLayout;
	PengRadioButton weixinRadio;
	PengRadioButton zhifubaoRadio;
	private String weixinUrl;
	private String zhifubaoUrl;
	
	//布局文件头 
	private ImageView leftImageView;
	private TextView centerTitle;
	private ImageButton rightButton;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
		AppManager.getAppManager().addActivity(this);// 添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮
		setContentView(R.layout.activity_two_dimension_code);
		
		  /**收集异常日志*/
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        //如果你要收集普通的日志到文件或者服务器，那么调用下面的方法即可。
        LogManager.getManager(getApplicationContext()).log(TAG, "onCreate",LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**收集异常日志*/
        
		bgFrameLayout = (FrameLayout) findViewById(R.id.bg);
//		fragmentmanager = getSupportFragmentManager();// 通过FragementActivity的方式获取FragementManager管理器对象
		initTitle();//初始化文件头布局
		initView();// 界面布局的初始化
		// 更改当前的Fragement受理业务的fragement 第二个参数是容器 第三个参数是内容
//		appContext.replaceFragment(fragmentmanager, R.id.login_regist_details, new ZhiFuBaoFragment(TwoDimensionCodeActivity.this,getZhifubaoBitmap()));//支付宝
		bgFrameLayout.setBackground(getResources().getDrawable(R.drawable.weixin));
		InitViewPager(); 
		
		
		
	}

	@Override
	protected void onDestroy() {
		/** 收集异常日志 */
		LogManager.getManager(getApplicationContext()).unregisterActivity(this);
		/** 收集异常日志 */
		super.onDestroy();
	}
	@SuppressLint("NewApi")
	private void initTitle() {
		// TODO Auto-generated method stub
		leftImageView = (ImageView) findViewById(R.id.head_left_iv);
		leftImageView.setImageDrawable(getResources().getDrawable(R.drawable.left_expandsion));
		leftImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerTitle = (TextView) findViewById(R.id.head_center_title);
		centerTitle.setText("支付二维码");
		rightButton = (ImageButton) findViewById(R.id.head_right_btn);
		rightButton.setBackground(getResources().getDrawable(R.drawable.twocode_payquestion_icon));
		rightButton.setVisibility(View.VISIBLE);
		rightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UiHelper.ShowOneToast(TwoDimensionCodeActivity.this, "作为收款的工具！");
			}
		});
	}

	private void initView() {
		radiogroup = (RadioGroup) findViewById(R.id.login_regist_radiogroup);
		weixinRadio=(PengRadioButton) radiogroup.findViewById(R.id.two_dimention_code_weixin_bt);
		zhifubaoRadio=(PengRadioButton) radiogroup.findViewById(R.id.two_dimention_code_zhifubao_bt);
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@SuppressLint("NewApi")
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				/*
				 * 
				 * 对于replaceFragment这个方法来说，需要记住的是三个参数 1.Fragmentmanager对象
				 * 2.容器FrameLayout(xml中，一般使用的是FrameLayout) 3.Fragment对象
				 * 
				 * 也就是将Fragment 放入xml布局中的一个FrameLayout容器中
				 */
				if (checkedId == R.id.two_dimention_code_zhifubao_bt) {//支付宝
					mPager.setCurrentItem(1);
//					appContext.replaceFragment(fragmentmanager, R.id.login_regist_details, new ZhiFuBaoFragment(TwoDimensionCodeActivity.this,getZhifubaoBitmap()));
					bgFrameLayout.setBackground(getResources().getDrawable(R.drawable.zhifubao));
				} else if (checkedId == R.id.two_dimention_code_weixin_bt) {//微信
					mPager.setCurrentItem(0);
//					appContext.replaceFragment(fragmentmanager, R.id.login_regist_details, new WeiXinFragment(TwoDimensionCodeActivity.this,getWeixinBitmap()));
					bgFrameLayout.setBackground(getResources().getDrawable(R.drawable.weixin));
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

	public Bitmap getWeixinBitmap() {
		return weixinBitmap;
	}

	public void setWeixinBitmap(Bitmap weixinBitmap) {
		this.weixinBitmap = weixinBitmap;
	}

	public Bitmap getZhifubaoBitmap() {
		return zhifubaoBitmap;
	}

	public void setZhifubaoBitmap(Bitmap zhifubaoBitmap) {
		this.zhifubaoBitmap = zhifubaoBitmap;
	}

	

	 /* 
     * 初始化ViewPager 
     */  
    public void InitViewPager(){  
        mPager = (ViewPager)findViewById(R.id.viewpager);  
        fragmentList = new ArrayList<Fragment>();  
        weiXinFragment=new WeiXinFragment(TwoDimensionCodeActivity.this,getWeixinBitmap());
        zhiFuBaoFragment=new ZhiFuBaoFragment(TwoDimensionCodeActivity.this,getZhifubaoBitmap());
        fragmentList.add(weiXinFragment);  
        fragmentList.add(zhiFuBaoFragment);    
        //给ViewPager设置适配器  
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));  
        mPager.setCurrentItem(0);//设置当前显示标签页为第一页  
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器  
    }  
  
      
    public class MyOnPageChangeListener implements OnPageChangeListener{  
          
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
        	
        }  
          
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
              
        }  
          
        @SuppressLint("NewApi")
		@Override  
        public void onPageSelected(int arg0) {  
            // TODO Auto-generated method stub  
        	if(arg0==0){
        		bgFrameLayout.setBackground(getResources().getDrawable(R.drawable.weixin));
        		weixinRadio.setChecked(true);
        		zhifubaoRadio.setChecked(false);
        	}else if(arg0==1){
        		bgFrameLayout.setBackground(getResources().getDrawable(R.drawable.zhifubao));
        		zhifubaoRadio.setChecked(true);
        		weixinRadio.setChecked(false);
        	}
            int i = arg0 + 1;  
//            Toast.makeText(TwoDimensionCodeActivity.this, "您选择了第"+i+"个页卡", Toast.LENGTH_SHORT).show();  
        }  
    }


	public String getWeixinUrl() {
		return weixinUrl;
	}

	public void setWeixinUrl(String weixinUrl) {
		this.weixinUrl = weixinUrl;
	}

	public String getZhifubaoUrl() {
		return zhifubaoUrl;
	}

	public void setZhifubaoUrl(String zhifubaoUrl) {
		this.zhifubaoUrl = zhifubaoUrl;
	}  
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			if(zhiFuBaoFragment.post!=null&&zhiFuBaoFragment.post.pd!=null&&zhiFuBaoFragment.post.pd.isShowing()){
//				
//				zhiFuBaoFragment.post.cancel(true);
//				zhiFuBaoFragment.post.pd.dismiss();
//				zhiFuBaoFragment.post.pd = null;
//			}
//			if(weiXinFragment.post!=null&&weiXinFragment.post.pd!=null&&weiXinFragment.post.pd.isShowing()){
//				
//				weiXinFragment.post.cancel(true);
//				weiXinFragment.post.pd.dismiss();
//				weiXinFragment.post.pd = null;
//			}
			finish();
			return false;
		}
		return false;
	}
}
