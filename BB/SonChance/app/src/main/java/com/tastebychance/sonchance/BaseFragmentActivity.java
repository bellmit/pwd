package com.tastebychance.sonchance;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;

import com.tastebychance.sonchance.util.CrashHandler;
import com.tastebychance.sonchance.util.SystemUtil;

/**
 * 项目名称：SonChance
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/18 15:37
 * 修改人：Administrator
 * 修改时间：2017/9/18 15:37
 * 修改备注：
 */

public abstract class BaseFragmentActivity extends FragmentActivity {


    public int statusHeight;

    protected abstract void processMessage(Message msg);
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            processMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication app = (MyApplication) getApplication();// 获取应用程序全局的实例引用
        app.activities.add(this); // 把当前Activity放入集合中

        statusHeight = SystemUtil.getInstance().getStatusBarHeight();
        setStatusBar();
    }

    /**
     * 设置状态栏颜色为透明色，这样能保证状态栏为沉浸式状态
     * 根据SDK >= 21时，标题栏高度设为statusBarHeight(25dp)+titlBarHeight(48dp)
     * 若SDK < 21,标题栏高度直接为titlBarHeight,关于不同的高度设置可以用两个values，values-19两个分别设置不同的dimens
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    protected  void handlerException(Exception e){
        CrashHandler.getInstance().handleException(e);
    }

    public void sendEmptyMessage(int what){
        handler.sendEmptyMessage(what);
    }

    public void sendEmptyMessageAtTime(int what,long uptimeMillis){
        handler.sendEmptyMessageAtTime(what,uptimeMillis);
    }

    public void  sendEmptyMessageDelayed(int what,long delayMillis){
        handler.sendEmptyMessageDelayed(what,delayMillis);
    }

    public void sendMessage(Message msg){
        handler.sendMessage(msg);
    }

    public void sendMessageAtFrontOfQueue(Message msg){
        handler.sendMessageAtFrontOfQueue(msg);
    }

    public void sendMessageAtTime(Message msg,long uptimeMillis){
        handler.sendMessageAtTime(msg,uptimeMillis);
    }

    public void sendMessageDelayed(Message msg,long delayMillis){
        handler.sendMessageDelayed(msg,delayMillis);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication app = (MyApplication) getApplication();// 获取应用程序全局的实例引用
        app.activities.remove(this);
    }
}
