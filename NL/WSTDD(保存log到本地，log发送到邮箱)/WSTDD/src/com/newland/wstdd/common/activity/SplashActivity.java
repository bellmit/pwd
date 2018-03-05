package com.newland.wstdd.common.activity;


import com.newland.wstdd.R;
import com.newland.wstdd.common.common.AppManager;
import com.newland.wstdd.common.log.transaction.log.manager.LogManager;
import com.newland.wstdd.common.log.transaction.utils.LogUtils;
import com.newland.wstdd.common.saveLog.LogcatHelper;
import com.newland.wstdd.common.savesendlog.logcollector.LogCollector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
 
/**
 * 功能：使用ViewPager实现初次进入应用时的引导页
 * 
 * (1)判断是否是首次加载应用--采取读取SharedPreferences的方法
 * (2)是，则进入引导activity；否，则进入MainActivity
 * (3)5s后执行(2)操作
 * 
 * @author sz082093
 *
 */
public class SplashActivity extends Activity {
	private static final String TAG = "SplashActivity";//收集异常日志tag
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
		AppManager.getAppManager().addActivity(this);//添加这个Activity到相应的栈中
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 保持屏幕常亮
        setContentView(R.layout.activity_splash);
        
        
        /**收集异常日志*/
        LogManager.getManager(getApplicationContext()).registerActivity(this);
        //如果你要收集普通的日志到文件或者服务器，那么调用下面的方法即可。
        LogManager.getManager(getApplicationContext()).log(TAG, "onCreate",LogUtils.LOG_TYPE_2_FILE_AND_LOGCAT);
        /**收集异常日志*/
//        LogcatHelper.getInstance(this).start();
        
        System.out.println(1/0);
        boolean mFirst = isFirstEnter(SplashActivity.this,SplashActivity.this.getClass().getName());
        if(mFirst)
            mHandler.sendEmptyMessageDelayed(SWITCH_GUIDACTIVITY,2000);
        else
            mHandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY,2000);
    }   
    @Override
    protected void onDestroy() {
    	 /**收集异常日志*/
    	LogManager.getManager(getApplicationContext()).unregisterActivity(this);
    	 /**收集异常日志*/
    	
//    	LogcatHelper.getInstance(this).stop();
    	super.onDestroy();
    }
     
	
    //****************************************************************
    // 判断应用是否初次加载，读取SharedPreferences中的guide_activity字段
    //****************************************************************
    private static final String SHAREDPREFERENCES_NAME = "my_pref";
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";
    private boolean isFirstEnter(Context context,String className){
        if(context==null || className==null||"".equalsIgnoreCase(className))return false;
        String mResultStr = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_WORLD_READABLE)
                 .getString(KEY_GUIDE_ACTIVITY, "");//取得所有类名 如 com.my.MainActivity
        if(mResultStr.equalsIgnoreCase("false"))
            return false;
        else
            return true;
    }
     
     
    //*************************************************
    // Handler:跳转至不同页面
    //*************************************************
    private final static int SWITCH_MAINACTIVITY = 1000;
    private final static int SWITCH_GUIDACTIVITY = 1001;
    public Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch(msg.what){
            case SWITCH_MAINACTIVITY:
            	//在跳转前面进行请求     优惠信息数据  调用
            	
            	Intent nIntent = new Intent();
                nIntent = new Intent();
                nIntent.setClass(SplashActivity.this, HomeActivity.class);
                SplashActivity.this.startActivity(nIntent);
                SplashActivity.this.finish();
                break;
            case SWITCH_GUIDACTIVITY:
            	Intent mIntent = new Intent();
                mIntent = new Intent();
                mIntent.setClass(SplashActivity.this, HomeActivity.class);
                SplashActivity.this.startActivity(mIntent);
                SplashActivity.this.finish();
                break;
            }
            super.handleMessage(msg);
        }
    };
    
    
}
