package company.webdemo.agile.com.technologycompany;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import java.lang.reflect.Method;

import company.webdemo.agile.com.technologycompany.util.Constants;
import company.webdemo.agile.com.technologycompany.util.SystemUtil;
import company.webdemo.agile.com.technologycompany.util.UiHelper;

public class MyBaseActivity extends Activity {
    InputMethodManager manager;

    public int statusHeight;

    public final int ERROR_WHAT = 0;//错误提示
    public final int INFO_WHAT = 1;//消息提示
    public String UPDATE_SUCCESS = "更新成功";
    public String GETDATA_SUCCESS = "请求数据成功";
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR_WHAT:
                    UiHelper.ShowOneToast(getApplicationContext(), (String) msg.obj);
                    break;
                case INFO_WHAT:
                    if (Constants.IS_DEVELOPING) {
                        UiHelper.ShowOneToast(getApplicationContext(), (String) msg.obj);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication app = (MyApplication) getApplication();// 获取应用程序全局的实例引用
        app.activities.add(this); // 把当前Activity放入集合中

        // if (MyApplication.NEEDUNLOCK){
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // //应用运行时，保持屏幕高亮，不锁屏
        // }
        statusHeight = SystemUtil.getInstance().getStatusBarHeight();
        setStatusBar();

        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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

    protected void hideNavigation() {//隐藏导航栏
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        //判断当前版本在4.0以上并且存在虚拟按键，否则不做操作
        if (Build.VERSION.SDK_INT < 19 || !checkDeviceHasNavigationBar()) {
            /*View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);*/

            //一定要判断是否存在按键，否则在没有按键的手机调用会影响别的功能。如之前没有考虑到，导致图传全屏变成小屏显示。
            return;
        } else {
            // 获取属性
            getWindow().getDecorView().setSystemUiVisibility(flag);
        }
    }

    /**
     * 判断是否存在虚拟按键
     *
     * @return
     */
    private boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication app = (MyApplication) getApplication();// 获取应用程序全局的实例引用
        app.activities.remove(this);
    }

    public void networkRequest() {

    }
}
