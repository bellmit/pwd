package rongshanghui.tastebychance.com.rongshanghui.startup;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.Method;

import rongshanghui.tastebychance.com.rongshanghui.HostActivity;
import rongshanghui.tastebychance.com.rongshanghui.MyApplication;
import rongshanghui.tastebychance.com.rongshanghui.MyFragmentTabs;
import rongshanghui.tastebychance.com.rongshanghui.R;
import rongshanghui.tastebychance.com.rongshanghui.login.LoginActivity;
import rongshanghui.tastebychance.com.rongshanghui.util.Constants;
import rongshanghui.tastebychance.com.rongshanghui.util.SharedPreferencesUtils;
import rongshanghui.tastebychance.com.rongshanghui.util.SystemUtil;


/**
 * 启动页
 *
 * @author shenbh
 *         <p>
 *         2017年8月9日
 */
public class StartUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.startup);
    }

    /**
     * 跳转到tabhost
     */
    public void intentToTabHost() {
        Intent intent;
        // 当前版本号
        /*int versionCode = SystemUtil.getInstance().getVersionCode(MyApplication.getContext());
        int oldVersonCode = SharedPreferencesUtils.getConfigInt(MyApplication.getContext(), "versionCode");//已存储版本号
        if (oldVersonCode == 0 || versionCode > oldVersonCode) {//版本号为空代表未使用过，版本更新判断版本号比当前的高需要进入引导页
            intent = new Intent(this, Guide2Activity.class);
        } else {*/
        intent = new Intent(this, HostActivity.class);
        /*}
        SharedPreferencesUtils.setConfigInt(MyApplication.getContext(), "versionCode", versionCode);*/
        startActivity(intent);
        this.finish();
    }

    /**
     * 跳转到注册界面
     */
    public void intentToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * 跳转到引导页
     */
    public void intentToGuide() {

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
    protected void onStart() {
        super.onStart();
        hideNavigation();

        //几秒后跳转
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;
                while (flag) {
                    try {
                        Thread.sleep(Constants.LOGO_SHOWTIME);
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
        System.out.println("StartUp----------------------------------------------------" + getTaskId());
    }


}
