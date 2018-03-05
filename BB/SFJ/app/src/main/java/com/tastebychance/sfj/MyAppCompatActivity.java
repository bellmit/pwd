package com.tastebychance.sfj;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.tastebychance.sfj.common.Constants;
import com.tastebychance.sfj.util.AppManager;
import com.tastebychance.sfj.util.SystemBarTintManager;
import com.tastebychance.sfj.util.PackageManagerUtils;
import com.tastebychance.sfj.util.SystemUtil;
import com.tastebychance.sfj.util.permissonchecker.PermissionsActivity;
import com.tastebychance.sfj.util.permissonchecker.PermissionsChecker;
/**
 * 
 * 
 * @author shenbinghong
 * @time 2018/1/30 20:16
 * @path com.tastebychance.sfj.MyAppCompatActivity.java
 */
public class MyAppCompatActivity extends AppCompatActivity {
    InputMethodManager manager;
    public int statusHeight;

    public PermissionsChecker permissionsChecker; // 权限检测器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);

        // if (MyApplication.NEEDUNLOCK){
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // //应用运行时，保持屏幕高亮，不锁屏
        // }

        statusHeight = SystemUtil.getInstance().getStatusBarHeight();
        setStatusBar();
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        permissionsChecker = new PermissionsChecker(this);

//        initWindow();
    }

    @Override
    protected void onStart() {
        if (Constants.IS_DEVELOPING) {
            System.out.println(PackageManagerUtils.getRunningActivityName(this) + "----------------------------------------------------" + getTaskId());
        }
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (Build.VERSION.SDK_INT >= 23 && permissionsChecker.lacksPermissions(Constants.PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    @Override
    protected void onDestroy() {
        AppManager.getInstance().finishActivity(this);
        super.onDestroy();
    }

    public void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, Constants.PERMISSION_REQUEST_CODE, Constants.PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == Constants.PERMISSION_REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }

    }

    //设置布局距离状态栏高度
    public void setLayoutPadding(Activity activity, DrawerLayout drawerLayout) {
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        contentLayout.getChildAt(1)
                .setPadding(contentLayout.getPaddingLeft(), statusHeight + contentLayout.getPaddingTop(),
                        contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
    }

    protected void setStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.TRANSPARENT);
        tintManager.setNavigationBarTintColor(Color.BLACK);//通知栏所需颜色
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on){
            winParams.flags |= bits;
        }else {
            winParams.flags &= ~bits;
        }
        window.setAttributes(winParams);
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
}
